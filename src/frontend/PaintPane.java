package frontend;

import backend.CanvasState;
import backend.model.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class PaintPane extends BorderPane {

	LayersPane layersPane;
	// BackEnd
	SortedMap<Layer, Pair<Boolean, CanvasState>> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor1 = Color.web("#fab900");
	Color defaultFillColor2 = Color.web("#033f87");

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");

	ObservableList<ShadowEnum> shadowsOptions =
			FXCollections.observableArrayList(
					ShadowEnum.SIMPLE,
					ShadowEnum.COLOREADA,
					ShadowEnum.SIMPLE_INVERSA,
					ShadowEnum.COLOREADA_INVERSA,
					ShadowEnum.NINGUNA
			);
	// Selector de sombras
	ChoiceBox<ShadowEnum> shadows = new ChoiceBox<>(shadowsOptions);

	// Selector de color de relleno
	ColorPicker fillColorPicker1 = new ColorPicker(defaultFillColor1);

	// Selector de color de relleno
	ColorPicker fillColorPicker2 = new ColorPicker(defaultFillColor2);

	// Barra Selectora para grosor de borde
	Slider edgeSlider = new Slider();

	ObservableList<BorderEnum> borderOptions =
			FXCollections.observableArrayList(
					BorderEnum.NORMAL,
					BorderEnum.PUNTEADO_SIMPLE,
					BorderEnum.PUNTEADO_COMPLEJO
			);
	// Selector de sombras
	ChoiceBox<BorderEnum> borders = new ChoiceBox<>(borderOptions);

	// Botones Acciones
	Button duplicarButton = new Button("Duplicar");
	Button dividirButton = new Button("Dividir");
	Button movCentroButton = new Button("Mov. Centro");

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	// Colores de relleno de cada figura
	Map<Figure, Pair<Color, Color>> figureColorMap = new HashMap<>();

	Map<Figure, ShadowEnum> figureShadowMap = new HashMap<>();

	Map<Figure, Pair<BorderEnum,Double>> figureBorderMap = new HashMap<>();

	Map<Figure, Layer> figureLayerMap = new HashMap<>();

	public PaintPane(SortedMap<Layer, Pair<Boolean, CanvasState>> canvasState, StatusPane statusPane, LayersPane layersPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.layersPane=layersPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr); // agrega los botones de figuras y select

		//Sombras
		buttonsBox.getChildren().add(new Label("Sombras"));
		buttonsBox.getChildren().add(shadows); // agrega las opciones de sombras
		shadows.setValue(ShadowEnum.NINGUNA); //setea ninguna

		//Relleno
		buttonsBox.getChildren().add(new Label("Relleno"));
		buttonsBox.getChildren().add(fillColorPicker1); // agrega las opciones de relleno
		buttonsBox.getChildren().add(fillColorPicker2); // seleccionador de colores (arranca en amarillo)

		//Borde
		buttonsBox.getChildren().add(new Label("Borde"));
		buttonsBox.getChildren().add(edgeSlider);
		edgeSlider.setMax(10);
		edgeSlider.setMin(0);// creemos que setMin es un menor igual y el grosor no puede ser cero
		edgeSlider.setValue(5);
		edgeSlider.setShowTickLabels(true);
		edgeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				figureBorderMap.put(selectedFigure,new Pair<>(borders.getValue(),t1.doubleValue()));
				edgeSlider.setValue(t1.doubleValue());
				redrawCanvas();
			}
		});
		buttonsBox.getChildren().add(borders);
		borders.setValue(BorderEnum.NORMAL);

		//Acciones
		buttonsBox.getChildren().add(new Label("Acciones"));
		Button[] actionArr = {duplicarButton, dividirButton, movCentroButton};
		for (Button action : actionArr) {
			action.setMinWidth(90);
			action.setCursor(Cursor.HAND);
		}
		buttonsBox.getChildren().addAll(actionArr);

		//Parametros
		buttonsBox.setPadding(new Insets(5)); // alto de la barra lateral
		buttonsBox.setStyle("-fx-background-color: #999"); // color de fondo
		buttonsBox.setPrefWidth(100); // ancho de la barra lateral
		gc.setLineWidth(1); // borde de las figuras

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			Figure newFigure;
			if(rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint);
			}
			else if(circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, circleRadius);
			} else if(squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(startPoint, size);
			} else if(ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
			} else {
				return ;
			}
			figureColorMap.put(newFigure, new Pair<>(fillColorPicker1.getValue(), fillColorPicker2.getValue()));
			figureShadowMap.put(newFigure, shadows.getValue());
			figureBorderMap.put(newFigure, new Pair<>(borders.getValue(),edgeSlider.getValue()));
			figureLayerMap.put(newFigure, layersPane.getChoiceLayer().getValue());
			canvasState.get(layersPane.getChoiceLayer().getValue()).getValue().add(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (Pair<Boolean, CanvasState> canvas : canvasState.values()) {
				if (canvas.getKey()) {
					for (Figure figure : canvas.getValue()) { // cambiar esto por metodo "encontrar figura"
						if (figureBelongs(figure, eventPoint)) {
							found = true;
							label.append(figure);
						}
					}
				}
			}
			if(!found) { // cambiado
				label.append(eventPoint);
			}
			statusPane.updateStatus(label.toString());
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Pair<Boolean, CanvasState> canvas : canvasState.values()) {
					if (canvas.getKey()) {
						for (Figure figure : canvas.getValue()) {   // se repite codigo
							if (figureBelongs(figure, eventPoint)) {     // cambiar esto por metodo "encontrar figura"
								found = true;
								selectedFigure = figure;
								label.append(figure);
							}
						}
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
					shadows.setValue(figureShadowMap.get(selectedFigure));
					fillColorPicker1.setValue(figureColorMap.get(selectedFigure).getKey());
					fillColorPicker2.setValue(figureColorMap.get(selectedFigure).getValue());
					borders.setValue(figureBorderMap.get(selectedFigure).getKey());
					//edgeSlider.setValue(figureBorderMap.get(selectedFigure).getValue());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectedFigure == null) {
				return;
			}
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getDistanceX(startPoint)) / 100;
				double diffY = (eventPoint.getDistanceY(startPoint)) / 100;
				selectedFigure.move(diffX,diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});
		
		duplicarButton.setOnAction(event -> {
			if (selectedFigure != null) {
				Figure dupFigure = selectedFigure.duplicate();
				canvasState.get(figureLayerMap.get(selectedFigure)).getValue().add(dupFigure);
				propertiesCopy(selectedFigure, dupFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		dividirButton.setOnAction(event -> {
			if (selectedFigure != null) {
				Pair<Figure, Figure> pairFigure = selectedFigure.split();
				canvasState.get(figureLayerMap.get(selectedFigure)).getValue().add(pairFigure.getValue());
				canvasState.get(figureLayerMap.get(selectedFigure)).getValue().add(pairFigure.getKey());
				propertiesCopy(selectedFigure, pairFigure.getValue());
				propertiesCopy(pairFigure.getValue(), pairFigure.getKey());
				deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		movCentroButton.setOnAction(event -> {
			if (selectedFigure != null) {
				Point centre = new Point(canvas.getWidth() / 2, canvas.getHeight() / 2);
				selectedFigure.move(centre.getDistanceX(selectedFigure.getCenter()), centre.getDistanceY(selectedFigure.getCenter()));
				redrawCanvas();
			}
		});

		shadows.setOnAction(event -> {
			if (selectedFigure != null) {
				figureShadowMap.put(selectedFigure,shadows.getValue());
				redrawCanvas();
			}
		});

		fillColorPicker1.setOnAction(event -> {
			if (selectedFigure != null) {
				figureColorMap.put(selectedFigure, new Pair<>(fillColorPicker1.getValue(), fillColorPicker2.getValue()));
				redrawCanvas();
			}
		});

		fillColorPicker2.setOnAction(event -> {
			if (selectedFigure != null) {
				figureColorMap.put(selectedFigure, new Pair<>(fillColorPicker1.getValue(), fillColorPicker2.getValue()));
				redrawCanvas();
			}
		});

		borders.setOnAction(event -> {
			if (selectedFigure != null) {
				figureBorderMap.put(selectedFigure, new Pair<>(borders.getValue(),edgeSlider.getValue()));
				redrawCanvas();
			}
		});

		layersPane.getMostrarButton().setOnAction(event -> {
			if (canvasState.containsKey(layersPane.getChoiceLayer().getValue())) {
				canvasState.put(layersPane.getChoiceLayer().getValue(), new Pair<>(true, canvasState.get(layersPane.getChoiceLayer().getValue()).getValue()));
				redrawCanvas();
			}
		});

		layersPane.getOcultarButton().setOnAction(event -> {
			if (canvasState.containsKey(layersPane.getChoiceLayer().getValue())) {
				canvasState.put(layersPane.getChoiceLayer().getValue(), new Pair<>(false, canvasState.get(layersPane.getChoiceLayer().getValue()).getValue()));
				redrawCanvas();
			}
		});

		layersPane.getChoiceLayer().setOnAction(event -> {
			boolean show = canvasState.getOrDefault(layersPane.getChoiceLayer().getValue(), new Pair<>(true, null)).getKey();
			layersPane.getMostrarButton().setSelected(show);
			layersPane.getOcultarButton().setSelected(!show);
		});

		layersPane.getAddLayerButton().setOnAction(event -> {
			Layer newLayer=new Layer(layersPane.nextLayer());
			layersPane.getChoiceLayer().getItems().add(newLayer);
			canvasState.put(newLayer,new Pair<>(true, new CanvasState()));
		});

		layersPane.getRemoveLayerButton().setOnAction(event -> {
			Layer layer=layersPane.getChoiceLayer().getValue();
			if (layer.getLayer()>3) {
				layersPane.getChoiceLayer().setValue(new Layer(1));
				layersPane.getChoiceLayer().getItems().remove(layer);
				canvasState.remove(layer);
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Pair<Boolean, CanvasState> canvas : canvasState.values()) {
			if (canvas.getKey()) {
				for (Figure figure : canvas.getValue()) {
					if (figure == selectedFigure) {
						gc.setStroke(Color.RED);
					} else {
						gc.setStroke(lineColor);
					}
					gc.setLineWidth(figureBorderMap.get(figure).getValue());
					figureBorderMap.get(figure).getKey().setPattern(gc);
					if (figure.isRect()) {
						figureShadowMap.get(figure).shadowRec(gc, figure, figureColorMap.get(figure).getKey().darker());
						LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
								CycleMethod.NO_CYCLE,
								new Stop(0, figureColorMap.get(figure).getKey()),
								new Stop(1, figureColorMap.get(figure).getValue()));
						gc.setFill(linearGradient);
						gc.fillRect(figure.getLeft(), figure.getTop(),
								figure.getWidth(), figure.getHeight());
						gc.strokeRect(figure.getLeft(), figure.getTop(),
								figure.getWidth(), figure.getHeight());

					} else if (figure.isRound()) {
						figureShadowMap.get(figure).shadowRound(gc, figure, figureColorMap.get(figure).getKey().darker());
						RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
								CycleMethod.NO_CYCLE,
								new Stop(0, figureColorMap.get(figure).getKey()),
								new Stop(1, figureColorMap.get(figure).getValue()));
						gc.setFill(radialGradient);
						gc.fillOval(figure.getLeft(), figure.getTop(),
								figure.getWidth(), figure.getHeight());
						gc.strokeOval(figure.getLeft(), figure.getTop(),
								figure.getWidth(), figure.getHeight());

					}
				}
			}
		}
	}

	private void propertiesCopy(Figure source, Figure destiny) {
		if (!canvasState.get(figureLayerMap.get(source)).getValue().contains(source)) {
			return;
		}
		figureColorMap.put(destiny, figureColorMap.get(source));
		figureShadowMap.put(destiny, figureShadowMap.get(source));
		figureBorderMap.put(destiny, figureBorderMap.get(source));
		figureLayerMap.put(destiny, figureLayerMap.get(source));
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.belongs(eventPoint);
	}

	private void deleteFigure(Figure figure) {
		canvasState.get(figureLayerMap.get(figure)).getValue().remove(figure);
		figureColorMap.remove(figure);
		figureShadowMap.remove(figure);
		figureBorderMap.remove(figure);
		figureLayerMap.remove(figure);
	}

}

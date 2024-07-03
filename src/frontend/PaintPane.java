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
	SortedMap<Layer, Pair<Boolean, CanvasState>> layerPairSortedMap;

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

	Map<Figure, Properties> figureProperties= new HashMap<>();

	public PaintPane(SortedMap<Layer, Pair<Boolean, CanvasState>> canvasState, StatusPane statusPane, LayersPane layersPane) {
		this.layerPairSortedMap = canvasState;
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
		edgeSlider.setMin(0.00000001);// creemos que setMin es un menor igual y el grosor no puede ser cero
		edgeSlider.setValue(5);
		edgeSlider.setShowTickLabels(true);
		edgeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				if(selectedFigure!=null){
					Properties props = figureProperties.get(selectedFigure).setFigureBorderWidth(t1.doubleValue());
					figureProperties.put(selectedFigure, props);
					edgeSlider.setValue(t1.doubleValue());
					redrawCanvas();
				}
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

			figureProperties.put(newFigure,
					new Properties(fillColorPicker1.getValue(),
							fillColorPicker2.getValue(),
							shadows.getValue(),
							borders.getValue(),
							edgeSlider.getValue(),
							layersPane.getChoiceLayer().getValue()
					)
			);
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
					for (Figure figure : canvas.getValue()) {
						if (figure.belongs(eventPoint)) {
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
						for (Figure figure : canvas.getValue()) {
							if (figure.belongs(eventPoint)){
								found = true;
								selectedFigure = figure;
								label.append(figure);
							}
						}
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
					Properties props=figureProperties.get(selectedFigure);
					edgeSlider.setValue(props.getFigureBorderWidth());
					borders.setValue(props.getFigureBorderStyle());
					shadows.setValue(props.getFigureShadow());
					fillColorPicker1.setValue(props.getColors().getKey());
					fillColorPicker2.setValue(props.getColors().getValue());
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
				propertiesCopy(selectedFigure, dupFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		dividirButton.setOnAction(event -> {
			if (selectedFigure != null) {
				Pair<Figure, Figure> pairFigure = selectedFigure.split();
				CanvasState cState = canvasState.get(figureProperties.get(selectedFigure).getFigureLayer()).getValue();
				cState.add(pairFigure.getValue());
				cState.add(pairFigure.getKey());
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
				figureProperties.get(selectedFigure).setFigureShadow(shadows.getValue());
				redrawCanvas();
			}
		});

		fillColorPicker1.setOnAction(event -> {
			if (selectedFigure != null) {
				figureProperties.get(selectedFigure).setColors(fillColorPicker1.getValue(), fillColorPicker2.getValue());
				redrawCanvas();
			}
		});

		fillColorPicker2.setOnAction(event -> {
			if (selectedFigure != null) {
				figureProperties.get(selectedFigure).setColors(fillColorPicker1.getValue(), fillColorPicker2.getValue());
				redrawCanvas();
			}
		});

		borders.setOnAction(event -> {
			if (selectedFigure != null) {
				figureProperties.get(selectedFigure).setFigureBorderStyle(borders.getValue());
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
		//clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Pair<Boolean, CanvasState> layer : layerPairSortedMap.values()) {
			if (layer.getKey()) {
				for (Figure figure : layer.getValue()) {
					if (figure == selectedFigure) {
						gc.setStroke(Color.RED);
					} else {
						gc.setStroke(lineColor);
					}
					if (figure.isRect()) {
						drawRectangularFigure(figure);
					} else {
						drawOvalFigure(figure);
					}
				}
			}
		}
	}

	private void propertiesCopy(Figure source, Figure destiny) {
		Properties props= figureProperties.get(source);
		if (!layerPairSortedMap.get(props.getFigureLayer()).getValue().contains(source)) {
			return;
		}
		layerPairSortedMap.get(props.getFigureLayer()).getValue().add(destiny);
		figureProperties.put(destiny,new Properties(
				props.getColors().getKey(),
				props.getColors().getValue(),
				props.getFigureShadow(),
				props.getFigureBorderStyle(),
				props.getFigureBorderWidth(),
				props.getFigureLayer()
		));
	}

	//private boolean figureBelongs(Figure figure, Point eventPoint) {
	//	return figure.belongs(eventPoint);
	//}

	private void deleteFigure(Figure figure) {
		layerPairSortedMap.get(figureProperties.get(figure).getFigureLayer()).getValue().remove(figure);
		figureProperties.remove(figure);
	}

	private void drawRectangularFigure(Figure figure){
		drawEdgeFigure(figure);
		Pair<Color, Color>  colors = figureProperties.get(figure).getColors();
		figureProperties.get(figure).getFigureShadow().shadowRec(gc, figure,colors.getKey().darker());
		LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
				CycleMethod.NO_CYCLE,
				new Stop(0, colors.getKey()),
				new Stop(1, colors.getValue())
		);
		gc.setFill(linearGradient);
		gc.fillRect(figure.getLeft(), figure.getTop(),
				figure.getWidth(), figure.getHeight());
		gc.strokeRect(figure.getLeft(), figure.getTop(),
				figure.getWidth(), figure.getHeight());
	}
	private void drawOvalFigure(Figure figure) {
		drawEdgeFigure(figure);
		Pair<Color, Color>  colors = figureProperties.get(figure).getColors();
		figureProperties.get(figure).getFigureShadow().shadowRound(gc,figure,colors.getKey().darker());
		RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
				CycleMethod.NO_CYCLE,
				new Stop(0, colors.getKey()),
				new Stop(1, colors.getValue())
		);
		gc.setFill(radialGradient);
		gc.fillOval(figure.getLeft(), figure.getTop(),
				figure.getWidth(), figure.getHeight());
		gc.strokeOval(figure.getLeft(), figure.getTop(),
				figure.getWidth(), figure.getHeight());
	}
	private void drawEdgeFigure(Figure figure) {
		gc.setLineWidth(figureProperties.get(figure).getFigureBorderWidth());
		figureProperties.get(figure).getFigureBorderStyle().setPattern(gc);
	}
}

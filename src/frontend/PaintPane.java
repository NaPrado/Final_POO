package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

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

	ChoiceBox<ShadowEnum> shadows = new ChoiceBox<>(shadowsOptions);

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	// Colores de relleno de cada figura
	Map<Figure, Color> figureColorMap = new HashMap<>();

	Map<Figure, ShadowEnum> figureShadowMap = new HashMap<>();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr); // agrega los botones de figuras y select
		buttonsBox.getChildren().add(new Label("Sombras"));
		buttonsBox.getChildren().add(shadows); // agrega las opciones de sombras
		shadows.setValue(ShadowEnum.NINGUNA);
		buttonsBox.getChildren().add(fillColorPicker); // seleccionador de colores (arranca en amarillo)
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
			figureColorMap.put(newFigure, fillColorPicker.getValue());
			figureShadowMap.put(newFigure, shadows.getValue());
			canvasState.add(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState) { // cambiar esto por metodo "encontrar figura"
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure);
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
				for (Figure figure : canvasState) {   // se repite codigo
					if(figureBelongs(figure, eventPoint)) {     // cambiar esto por metodo "encontrar figura"
						found = true;
						selectedFigure = figure;
						label.append(figure);
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
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
				canvasState.remove(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(figureColorMap.get(figure));
			if (figure.isRect()) {
				gc.fillRect(figure.getLeft(), figure.getTop(),
						figure.getWidth(), figure.getHeigth());
				gc.strokeRect(figure.getLeft(), figure.getTop(),
						figure.getWidth(), figure.getHeigth());
			} else if (figure.isRound()) {
				gc.fillOval(figure.getLeft(), figure.getTop(),
						figure.getWidth(), figure.getHeigth());
				gc.strokeOval(figure.getLeft(), figure.getTop(),
						figure.getWidth(), figure.getHeigth());
			}
		}
	}

	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.belongs(eventPoint);
	}

}

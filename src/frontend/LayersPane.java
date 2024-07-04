package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LayersPane extends BorderPane {

    private ChoiceBox<Layer> capas;
    private int layerCount = 3;

    private final RadioButton mostrarButton = new RadioButton("Mostrar");
    private final RadioButton ocultarButton = new RadioButton("Ocultar");
    private final RadioButton todasButton = new RadioButton("Todas");
    private final RadioButton soloButton = new RadioButton("Solo");
    private final TextField filterByLabel =new TextField();
    private final Button addLayer = new Button("Agregar Capa");
    private final Button removeLayer = new Button("Eliminar Capa");

    public LayersPane() {
        VBox layersPane = new VBox(10);
        setStyle("-fx-background-color: #999");
        layersPane.setAlignment(Pos.CENTER);
        layersPane.setPadding(new Insets(5));
        Label capasText=new Label("Capas");
        layersPane.getChildren().add(capasText);
        HBox firstRow = new HBox(10);
        HBox secondRow = new HBox(10);
        ObservableList<Layer> layersOptions =
                FXCollections.observableArrayList(
                        new Layer(1),
                        new Layer(2),
                        new Layer(3)
                );
        capas = new ChoiceBox<>(layersOptions);
        capas.setValue(new Layer(1));
        layersPane.getChildren().add(capas);
        mostrarButton.setSelected(true);
        layersPane.getChildren().add(mostrarButton);
        RadioButton[] layersArr = {mostrarButton, ocultarButton};
        ToggleGroup tools = new ToggleGroup();
        for (RadioButton tool : layersArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        firstRow.getChildren().addAll(capasText,capas,layersArr[0],layersArr[1], addLayer, removeLayer);
        secondRow.getChildren().addAll(todasButton,soloButton,filterByLabel);
        layersPane.getChildren().addAll(firstRow,secondRow);
        setCenter(layersPane);
    }

    public ChoiceBox<Layer> getChoiceLayer() {
        return capas;
    }

    public RadioButton getMostrarButton() {
        return mostrarButton;
    }

    public RadioButton getSoloButton() {
        return soloButton;
    }

    public RadioButton getTodasButton() {
        return todasButton;
    }

    public RadioButton getOcultarButton() {
        return ocultarButton;
    }

    public Button getAddLayerButton() {
        return addLayer;
    }

    public Button getRemoveLayerButton() {
        return removeLayer;
    }

    public int nextLayer() {
        return ++layerCount;
    }
    public TextField getfilterByLabel(){return filterByLabel;}

}

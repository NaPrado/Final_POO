package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class LayersPane extends BorderPane {

    ChoiceBox<Layer> capas;
    int layerCount = 3;

    RadioButton mostrarButton = new RadioButton("Mostrar");
    RadioButton ocultarButton = new RadioButton("Ocultar");

    Button addLayer = new Button("Agregar Capa");
    Button removeLayer = new Button("Eliminar Capa");

    public LayersPane() {
        HBox layersPane = new HBox(10);
        setStyle("-fx-background-color: #999");
        layersPane.setAlignment(Pos.CENTER);
        layersPane.setPadding(new Insets(5));
        Label capasText=new Label("Capas");
        layersPane.getChildren().add(capasText);

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
        layersPane.getChildren().add(ocultarButton);
        layersPane.getChildren().add(addLayer);
        layersPane.getChildren().add(removeLayer);
        setCenter(layersPane);
    }

    public ChoiceBox<Layer> getChoiceLayer() {
        return capas;
    }

    public RadioButton getMostrarButton() {
        return mostrarButton;
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

}

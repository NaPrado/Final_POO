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
    public LayersPane() {
        HBox layersPane = new HBox(10);
        setStyle("-fx-background-color: #999");
        layersPane.setAlignment(Pos.CENTER);
        layersPane.setPadding(new Insets(5));
        Label capasText=new Label("Capas");
        layersPane.getChildren().add(capasText);

        ObservableList<String> layersOptions =
                FXCollections.observableArrayList(
                        "Capa 1",
                        "Capa 2",
                        "Capa 3"
                );
        ChoiceBox<String> capas = new ChoiceBox<>(layersOptions);
        capas.setValue("Capa 1");
        layersPane.getChildren().add(capas);
        RadioButton mostrarButton = new RadioButton("Mostrar");
        mostrarButton.setSelected(true);
        layersPane.getChildren().add(mostrarButton);
        RadioButton ocultarButton = new RadioButton("Ocultar");
        RadioButton[] layersArr = {mostrarButton, ocultarButton};
        ToggleGroup tools = new ToggleGroup();
        for (RadioButton tool : layersArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        layersPane.getChildren().add(ocultarButton);
        Button addLayer = new Button("Agregar Capa");
        layersPane.getChildren().add(addLayer);
        Button removeLayer = new Button("Eliminar Capa");
        layersPane.getChildren().add(removeLayer);
        setCenter(layersPane);
    }
}

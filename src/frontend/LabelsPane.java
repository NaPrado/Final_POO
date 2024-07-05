package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LabelsPane extends BorderPane {

    private final RadioButton todasButton = new RadioButton("Todas");
    private final RadioButton soloButton = new RadioButton("Solo");
    private final TextField filterByLabel =new TextField();

    public LabelsPane() {
        HBox layersPane = new HBox(10);
        setStyle("-fx-background-color: #999");
        layersPane.setAlignment(Pos.CENTER);
        layersPane.setPadding(new Insets(5));
        Label etiquetasText=new Label("Mostrar Etiquetas");
        layersPane.getChildren().add(etiquetasText);
        layersPane.getChildren().add(soloButton);
        layersPane.getChildren().add(todasButton);
        layersPane.getChildren().add(filterByLabel);
        setCenter(layersPane);
    }
    public RadioButton getSoloButton() {
        return soloButton;
    }
    public RadioButton getTodasButton() {
        return todasButton;
    }
    public TextField getfilterByLabel(){return filterByLabel;}
}

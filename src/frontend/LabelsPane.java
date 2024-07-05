package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LabelsPane extends BorderPane {

    private final RadioButton todasButton = new RadioButton("Todas");
    private final RadioButton soloButton = new RadioButton("Solo");
    private final TextField filterByLabel =new TextField();
    private String inputFilter;

    public LabelsPane() {
        HBox layersPane = new HBox(10);
        setStyle("-fx-background-color: #999");
        layersPane.setAlignment(Pos.CENTER);
        layersPane.setPadding(new Insets(5));
        Label etiquetasText=new Label("Mostrar Etiquetas");
        layersPane.getChildren().add(etiquetasText);
        ToggleButton[] toolsArr = {todasButton,soloButton};
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        layersPane.getChildren().addAll(toolsArr);
        layersPane.getChildren().add(filterByLabel);
        todasButton.setSelected(true);
        setCenter(layersPane);
    }
    public RadioButton getSoloButton() {
        return soloButton;
    }
    public RadioButton getTodasButton() {
        return todasButton;
    }
    public TextField getFilterByLabel() {return filterByLabel;}
    public String getInputFilter() {return inputFilter;}
    public void setInputFilter(String inputFilter) {this.inputFilter = inputFilter;}
}

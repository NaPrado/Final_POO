package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.SortedMap;

public class MainFrame extends VBox {

    public MainFrame(SortedMap<Layer, Pair<Boolean, CanvasState>> layersMap) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        LayersPane layersPane = new LayersPane();
        LabelsPane labelPane = new LabelsPane();
        getChildren().add(layersPane);
        getChildren().add(labelPane);
        getChildren().add(new PaintPane(layersMap, statusPane, labelPane, layersPane));
        getChildren().add(statusPane);
    }

}

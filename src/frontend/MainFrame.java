package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.SortedMap;

public class MainFrame extends VBox {

    public MainFrame(SortedMap<String, Pair<Boolean, CanvasState>> layersMap) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        LayersPane layersPane = new LayersPane();
        getChildren().add(layersPane);
        getChildren().add(new PaintPane(layersMap, statusPane,layersPane));
        getChildren().add(statusPane);
    }

}

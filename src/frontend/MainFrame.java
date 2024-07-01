package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        LayersPane layersPane = new LayersPane();
        getChildren().add(layersPane);
        getChildren().add(new PaintPane(canvasState, statusPane,layersPane));
        getChildren().add(statusPane);
    }

}

package frontend.main;

import frontend.front_figures.FrontFigure;
import frontend.panes.LabelsPane;
import frontend.panes.LayersPane;
import frontend.panes.PaintPane;
import frontend.panes.StatusPane;
import frontend.properties.Layer;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.SortedMap;

public class MainFrame extends VBox {

    public MainFrame(SortedMap<Layer, Pair<Boolean, ArrayList<FrontFigure>>> layersMap) {
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

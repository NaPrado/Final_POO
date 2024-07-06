package frontend.panes.buttonTypes;

import backend.model.Point;
import frontend.front_figures.FrontFigure;
import frontend.properties.Properties;
import javafx.scene.control.ToggleButton;

public class FigureButton extends ToggleButton {
    private FigureButtonFunctionality fbf;
    public FigureButton(String name, FigureButtonFunctionality fbf){
        super(name);
        this.fbf = fbf;
    }
    public FrontFigure makeFrontFigure( Point endPoint, Point startPoint, Properties properties){
        return fbf.makeNewFigure( endPoint, startPoint, properties);
    }
}

package frontend.front_figures;

import backend.model.Ellipse;
import backend.model.Point;
import frontend.properties.Properties;

public class FrontElipse extends FrontOvalFigure {

    public FrontElipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Properties properties) {
        this.figure = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
        this.properties = properties;
    }

}

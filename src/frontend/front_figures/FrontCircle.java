package frontend.front_figures;

import backend.model.Circle;
import backend.model.Point;
import frontend.properties.Properties;

public class FrontCircle extends FrontOvalFigure {

    public FrontCircle(Point centerPoint, double radius, Properties properties) {
        this.figure = new Circle(centerPoint, radius);
        this.properties = properties;
    }

}

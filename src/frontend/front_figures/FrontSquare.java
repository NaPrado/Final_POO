package frontend.front_figures;

import backend.model.Point;
import backend.model.Square;
import frontend.properties.Properties;

public class FrontSquare extends FrontRectangularFigure {

    public FrontSquare(Point topLeft, double size, Properties properties) {
        this.figure = new Square(topLeft, size);
        this.properties = properties;
    }

}

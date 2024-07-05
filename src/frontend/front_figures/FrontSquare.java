package frontend.front_figures;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.properties.Properties;

public class FrontSquare extends FrontRectangularFigure {

    public FrontSquare(Figure figure, Properties properties) {
        super(figure, properties);
    }

    public FrontSquare(Point topLeft, double size, Properties properties) {
        super(new Square(topLeft, size), properties);
    }

    @Override
    public FrontFigure duplicate() {
        return new FrontSquare(figure.duplicate(), copyProperties());
    }
}

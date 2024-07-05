package frontend.front_figures;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.properties.Properties;

public class FrontElipse extends FrontOvalFigure {

    public FrontElipse(Figure figure, Properties properties) {
        super(figure, properties);
    }

    public FrontElipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Properties properties) {
        super(new Ellipse(centerPoint, sMayorAxis, sMayorAxis), properties);
    }

    @Override
    public FrontFigure duplicate() {
        return new FrontElipse(figure.duplicate(), copyProperties());
    }

}

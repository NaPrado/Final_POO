package frontend.front_figures;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.properties.Properties;
import javafx.util.Pair;

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

    @Override
    public Pair<FrontFigure, FrontFigure> split() {
        Pair<Figure, Figure> figureSplit = figure.split();
        return new Pair<>(new FrontElipse(figureSplit.getKey(), copyProperties()), new FrontElipse(figureSplit.getValue(), copyProperties()));
    }

}

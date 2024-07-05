package frontend.front_figures;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.properties.Properties;
import javafx.util.Pair;

public class FrontCircle extends FrontOvalFigure {

    public FrontCircle(Figure figure, Properties properties) {
        super(figure, properties);
    }

    public FrontCircle(Point centerPoint, double radius, Properties properties) {
        super(new Circle(centerPoint, radius), properties);
    }

    @Override
    public FrontFigure duplicate() {
        return new FrontCircle(figure.duplicate(), copyProperties());
    }

    @Override
    public Pair<FrontFigure, FrontFigure> split() {
        Pair<Figure,Figure>figurePair=figure.split();
        return new Pair<>(new FrontCircle(figurePair.getKey(),copyProperties()),new FrontCircle(figurePair.getValue(),copyProperties()));
    }

}

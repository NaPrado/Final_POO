package frontend.front_figures;

import backend.model.*;
import frontend.properties.Properties;
import javafx.util.Pair;

public class FrontRectangle extends FrontRectangularFigure{

    @Override
    public FrontRectangle(Figure figure, Properties properties) {
        super(figure, properties);
    }

    public FrontRectangle(Point topLeft, Point bottomRight, Properties properties) {
        super(new Rectangle(topLeft, bottomRight),properties);
    }

    @Override
    public FrontFigure duplicate() {
        Figure copyFigure = figure.duplicate();
        Properties properties = copyProperties();
        FrontFigure toReturn = new FrontRectangle(copyFigure, properties);
        return toReturn;

    }

    @Override
    public Pair<FrontFigure, FrontFigure> split() {
        return null;
    }

}

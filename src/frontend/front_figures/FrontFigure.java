package frontend.front_figures;

import backend.model.Figure;
import backend.model.Point;
import frontend.panes.PaintPane;
import frontend.properties.Properties;
import javafx.util.Pair;

public abstract class FrontFigure implements Drawable {

    Figure figure;
    Properties properties;

    public FrontFigure(Figure figure, Properties properties) {
        this.figure = figure;
        this.properties = properties;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean belongs(Point point) {
        return figure.belongs(point);
    }

    public void move(double diffX, double diffY) {
        figure.move(diffX, diffY);
    }

    public Properties copyProperties() {
        Properties toReturn = new Properties(
                properties.getColors().getKey(),
                properties.getColors().getValue(),
                properties.getFigureShadow(),
                properties.getFigureBorderStyle(),
                properties.getFigureBorderWidth(),
                properties.getFigureLayer()
        );
        toReturn.setTags(properties.getTags());
        return toReturn;
    }

    abstract public FrontFigure duplicate();

    abstract public Pair<FrontFigure, FrontFigure> split();
}

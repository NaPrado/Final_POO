package frontend.front_figures;

import backend.model.Point;
import backend.model.Square;
import frontend.Properties;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Pair;

public class FrontSquare extends FrontFigure {

    FrontSquare(Point topLeft, int size, Properties properties) {
        super();
        this.figure = new Square(topLeft, size);
        this.properties = properties;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawEdge(gc);
        Pair<Color, Color> colors = properties.getColors();
        properties.getFigureShadow().shadowRec(gc, figure, colors.getKey().darker());
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, colors.getKey()),
                new Stop(1, colors.getValue())
        );
        gc.setFill(linearGradient);
        gc.fillRect(figure.getLeft(), figure.getTop(), figure.getWidth(), figure.getHeight());
        gc.strokeRect(figure.getLeft(), figure.getTop(), figure.getWidth(), figure.getHeight());
    }
}

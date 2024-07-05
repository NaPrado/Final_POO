package frontend.front_figures;

import backend.CanvasState;
import backend.model.*;
import frontend.Properties;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Pair;

public class FrontRectangle extends FrontFigure{

    FrontRectangle(Point topLeft, Point bottomRight, Properties properties) {
        this.figure = new Rectangle(topLeft, bottomRight);
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

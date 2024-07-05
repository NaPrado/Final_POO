package frontend.front_figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.util.Pair;

public abstract class FrontOvalFigure extends FrontFigure {
    @Override
    public void draw(GraphicsContext gc) {
        drawEdge(gc);
        Pair<Color, Color> colors = properties.getColors();
        properties.getFigureShadow().shadowRound(gc,figure,colors.getKey().darker());
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, colors.getKey()),
                new Stop(1, colors.getValue())
        );
        gc.setFill(radialGradient);
        gc.fillOval(figure.getLeft(), figure.getTop(),
                figure.getWidth(), figure.getHeight());
        gc.strokeOval(figure.getLeft(), figure.getTop(),
                figure.getWidth(), figure.getHeight());
    }
}

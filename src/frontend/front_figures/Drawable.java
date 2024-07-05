package frontend.front_figures;

import frontend.Properties;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    default void drawEdge(GraphicsContext gc) {
        gc.setLineWidth(getProperties().getFigureBorderWidth());
        getProperties().getFigureBorderStyle().setPattern(gc);
    }

    Properties getProperties();

    default void draw(GraphicsContext gc) {
        drawEdge(gc);

    }

}

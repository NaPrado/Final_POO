package frontend.front_figures;

import frontend.properties.Properties;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    default void drawEdge(GraphicsContext gc) {
        gc.setLineWidth(getProperties().getFigureBorderWidth());
        getProperties().getFigureBorderStyle().setPattern(gc);
    }

    void draw(GraphicsContext gc);

    Properties getProperties();

}

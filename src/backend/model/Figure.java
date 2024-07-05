package backend.model;

import javafx.util.Pair;

public interface Figure {
    public static int OFFSET = 10;
    public void move(double diffX, double diffY);
    public boolean isRect();
    public double getLeft();
    public double getTop();
    public double getHeight();
    public double getWidth();
    public boolean belongs(Point eventPoint);
    public Point getCenter();
    public Figure duplicate();
    public Pair<Figure,Figure> split();
}

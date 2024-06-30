package backend.model;

public interface Figure {
    public void move(double diffX, double diffY);
    public boolean isRect();
    public boolean isRound();
    public double getLeft();
    public double getTop();
    public double getHeight();
    public double getWidth();
    public boolean belongs(Point eventPoint);
    public Point getCenter();
}

package backend.model;

public class Rectangle implements Figure{

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public void move(double diffX, double diffY) {
        topLeft.setX(topLeft.getX()+diffX);
        bottomRight.setX(bottomRight.getX()+diffX);
        topLeft.setY(topLeft.getY()+diffY);
        bottomRight.setY(bottomRight.getY()+diffY);
    }

    @Override
    public boolean isRect() {
        return true;
    }

    @Override
    public boolean isRound() {
        return false;
    }

    @Override
    public double getLeft() {
        return getTopLeft().getX();
    }

    @Override
    public double getTop() {
        return getTopLeft().getY();
    }

    @Override
    public double getHeigth() {
        return Math.abs(getTopLeft().getY() - getBottomRight().getY());
    }

    @Override
    public double getWidth() {
        return Math.abs(getTopLeft().getDistanceX(getBottomRight()));
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }
}

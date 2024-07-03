package backend.model;

import javafx.util.Pair;

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
    public double getLeft() {
        return getTopLeft().getX();
    }

    @Override
    public double getTop() {
        return getTopLeft().getY();
    }

    @Override
    public double getHeight() {
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

    @Override
    public Point getCenter() {
        return new Point(topLeft.getX()+getWidth()/2, topLeft.getY()+getHeight()/2);
    }

    @Override
    public Figure duplicate() {
        return new Rectangle(new Point(topLeft.getX()+OFFSET,topLeft.getY()+OFFSET),
                new Point(bottomRight.getX()+OFFSET,bottomRight.getY()+OFFSET));
    }

    @Override
    public Pair<Figure, Figure> split() {
        Figure fig1,fig2;
        fig1 = new Rectangle(new Point(getCenter().getX()-getWidth()/2,getCenter().getY()-getHeight()/4),
                new Point(getCenter().getX(),getCenter().getY()+getHeight()/4));
        fig2 = new Rectangle(new Point(getCenter().getX(),getCenter().getY()-getHeight()/4),
                new Point(getCenter().getX()+getWidth()/2,getCenter().getY()+getHeight()/4));
        return new Pair<>(fig1, fig2);
    }

}

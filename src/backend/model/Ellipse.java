package backend.model;

import javafx.util.Pair;

public class Ellipse implements Figure {

    protected final Point centerPoint;
    private final double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public void move(double diffX, double diffY) {
        getCenterPoint().setX( getCenterPoint().getX()+ diffX);
        getCenterPoint().setY( getCenterPoint().getY()+ diffY);
    }
    @Override
    public boolean isRect() {
        return false;
    }

    @Override
    public boolean isRound() {
        return true;
    }

    @Override
    public double getLeft() {
        return getCenterPoint().getX() - (getsMayorAxis() / 2);
    }

    @Override
    public double getTop() {
        return getCenterPoint().getY() - (getsMinorAxis() / 2);
    }

    @Override
    public double getHeight() {
        return getsMinorAxis();
    }

    @Override
    public double getWidth() {
        return getsMayorAxis();
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - getCenterPoint().getX(), 2) / Math.pow(getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - getCenterPoint().getY(), 2) / Math.pow(getsMinorAxis(), 2))) <= 0.30;
    }

    @Override
    public Point getCenter() {
        return centerPoint;
    }

    @Override
    public Figure duplicate() {
        return new Ellipse(new Point(centerPoint.getX()+OFFSET,centerPoint.getY()+OFFSET),
                sMayorAxis, sMinorAxis);
    }

    @Override
    public Pair<Figure, Figure> split() {
        Figure fig1,fig2;
        fig1 = new Ellipse(new Point(getCenterPoint().getX()-getWidth()/4,getCenterPoint().getY()),
                (getsMayorAxis())/2,getsMinorAxis()/2);
        fig2 = new Ellipse(new Point(getCenterPoint().getX()+getWidth()/4,getCenterPoint().getY()),
                (getsMayorAxis())/2,getsMinorAxis()/2);
        return new Pair<>(fig1,fig2);
    }
}

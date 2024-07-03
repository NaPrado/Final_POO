package backend.model;

import javafx.util.Pair;

public class Circle extends Ellipse {
    public Circle(Point centerPoint, double radius) {
        super(centerPoint,radius*2,radius*2);
    }

    public double getRadius() {
        return getsMayorAxis()/2;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, getRadius());
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }

    @Override
    public Figure duplicate() {
        return new Circle(new Point(getCenterPoint().getX()+OFFSET,getCenterPoint().getY()+OFFSET),getRadius());
    }

    @Override
    public Pair<Figure, Figure> split() {
        Figure fig1,fig2;
        fig1 = new Circle(new Point(getCenterPoint().getX()-getWidth()/4,getCenterPoint().getY()),
                getRadius()/2);
        fig2 = new Circle(new Point(getCenterPoint().getX()+getWidth()/4,getCenterPoint().getY()),
                getRadius()/2);;
        return new Pair<>(fig1,fig2);
    }
}

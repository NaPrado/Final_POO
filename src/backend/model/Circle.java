package backend.model;

public class Circle extends Ellipse implements Figure {
    public Circle(Point centerPoint, double radius) {
        super(centerPoint,radius,radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, getRadius());
    }

    public double getRadius() {
        return getsMayorAxis();
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }
}

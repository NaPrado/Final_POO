package backend.model;

import javafx.util.Pair;

public class Square extends Rectangle {
    private final double size;
    public Square(Point topLeft, double size) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.size=size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public Figure duplicate() {
        return new Square(new Point(getTopLeft().getX()+OFFSET,getTopLeft().getY()+OFFSET),size);
    }

    @Override
    public Pair<Figure, Figure> split() {
        Figure fig1,fig2;
        fig1 = new Square(new Point(getCenter().getX()-getWidth()/2,getCenter().getY()-getHeight()/4),size/2);
        fig2 = new Square(new Point(getCenter().getX(),getCenter().getY()-getHeight()/4),size/2);
        return new Pair(fig1, fig2);
    }
}

package frontend;

import backend.model.Figure;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Properties {
    private Pair<Color, Color> colors;
    private ShadowEnum figureShadow;
    private Pair<BorderEnum,Double> figureBorder;
    private Layer figureLayer;

    Properties(Color c1,Color c2,ShadowEnum figureShadow,BorderEnum figureBorder,double width,Layer figureLayer){
        colors = new Pair<>(c1,c2);
        this.figureShadow = figureShadow;
        this.figureBorder = new Pair<>(figureBorder,width);
        this.figureLayer = figureLayer;
    }



    public Properties setColors(Color c1, Color c2) {
        this.colors = new Pair<>(c1,c2);
        return this;
    }

    public Properties setFigureBorder(BorderEnum figureBorder,double width) {
        this.figureBorder = new Pair<>(figureBorder,width);
        return this;
    }

    public Properties setFigureShadow(ShadowEnum figureShadow) {
        this.figureShadow = figureShadow;
        return this;
    }

    public Pair<Color, Color> getColors() {
        return colors;
    }

    public ShadowEnum getFigureShadow() {
        return figureShadow;
    }

    public Pair<BorderEnum, Double> getFigureBorder() {
        return figureBorder;
    }

    public Layer getFigureLayer() {
        return figureLayer;
    }
}

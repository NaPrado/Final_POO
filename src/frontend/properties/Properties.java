package frontend.properties;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class Properties {
    private Pair<Color, Color> colors;
    private ShadowEnum figureShadow;
    private BorderEnum figureBorderStyle;
    private double figureBorderWidth;
    private Layer figureLayer;
    private Set<String> tags;

    public Properties(Color c1, Color c2, ShadowEnum figureShadow, BorderEnum figureBorderStyle, double figureBorderWidth, Layer figureLayer){
        colors = new Pair<>(c1,c2);
        this.figureShadow = figureShadow;
        this.figureBorderStyle = figureBorderStyle;
        this.figureBorderWidth = figureBorderWidth;
        this.figureLayer = figureLayer;
        tags=new HashSet<>();
    }

    public void setTags(String tagsToAdd) {
        Set<String> newtags = new HashSet<>();
        newtags.addAll(Arrays.asList(tagsToAdd.split("[\\s|\\n]+")));
        tags=newtags;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tagSet) {
        tags = new HashSet<>(tags);
    }

    public void setColors(Color c1, Color c2) {
        this.colors = new Pair<>(c1,c2);
    }

    public void setFigureBorderStyle(BorderEnum figureBorderStyle) {
        this.figureBorderStyle = figureBorderStyle;
    }

    public void setFigureBorderWidth(double figureBorderWidth) {
        this.figureBorderWidth = figureBorderWidth;
    }

    public void setFigureShadow(ShadowEnum figureShadow) {
        this.figureShadow = figureShadow;
    }

    public Pair<Color, Color> getColors() {
        return colors;
    }

    public ShadowEnum getFigureShadow() {
        return figureShadow;
    }

    public BorderEnum getFigureBorderStyle() {
        return figureBorderStyle;
    }

    public double getFigureBorderWidth() {return figureBorderWidth;}

    public Layer getFigureLayer() {
        return figureLayer;
    }
}

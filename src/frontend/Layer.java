package frontend;

public class Layer implements Comparable<Layer>{
    int layer;
    public Layer(int layer) {
        this.layer = layer;
    }
    @Override
    public String toString() {
        return "Capa %d".formatted(layer);
    }
    @Override
    public int compareTo(Layer o) {
        return Integer.compare(layer, o.layer);
    }
    public int getLayer() {
        return layer;
    }
}

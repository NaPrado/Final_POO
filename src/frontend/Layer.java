package frontend;

public class Layer implements Comparable<Layer>{
    private final int layer;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer layer1 = (Layer) o;
        return layer == layer1.layer;
    }
}

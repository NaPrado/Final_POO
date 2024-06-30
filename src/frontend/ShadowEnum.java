package frontend;
public enum ShadowEnum {
    SIMPLE("Simple"),
    COLOREADA("Coloreada"),
    SIMPLE_INVERSA("Simple Inversa"),
    COLOREADA_INVERSA("Coloreada Inversa"),
    NINGUNA("Ninguna");


    private String choiceName;
    ShadowEnum(String choiceName) {
        this.choiceName = choiceName;
    }

    // Hay que sobreescribirlo
    @Override
    public String toString() {
        return choiceName;
    }
}

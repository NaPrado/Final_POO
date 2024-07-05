package frontend.properties;

import javafx.scene.canvas.GraphicsContext;

public enum BorderEnum {
        NORMAL("Normal"){
            @Override
            public void setPattern(GraphicsContext gc){
                gc.setLineDashes(0);
            }
        },
        PUNTEADO_SIMPLE("P.Simple"){
            @Override
            public void setPattern(GraphicsContext gc){
                gc.setLineDashes(10);
            }
        },
        PUNTEADO_COMPLEJO("P.Completo"){
            @Override
            public void setPattern(GraphicsContext gc){
                gc.setLineDashes(30,10,15,10);
            }
        };
        private String choiceName;
        BorderEnum(String choiceName){
            this.choiceName=choiceName;
        }

    @Override
    public String toString() {
        return choiceName;
    }
    public abstract void setPattern(GraphicsContext gc);
}

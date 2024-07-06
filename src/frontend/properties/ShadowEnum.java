package frontend.properties;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum ShadowEnum {
    SIMPLE("Simple",10){
            public void shadowRec(GraphicsContext gc,Figure figure,Color c){
                shadowRecGen(gc,figure,Color.GRAY);
            }
            public void shadowRound(GraphicsContext gc,Figure figure,Color c){
                shadowRoundGen(gc,figure,Color.GRAY);
            }
        },
    COLOREADA("Coloreada",10){
        public void shadowRec(GraphicsContext gc,Figure figure,Color c){
            shadowRecGen(gc,figure,c);
        }
        public void shadowRound(GraphicsContext gc,Figure figure,Color c){
            shadowRoundGen(gc,figure,c);
        }
    },
    SIMPLE_INVERSA("Simple Inversa",-10){
        public void shadowRec(GraphicsContext gc,Figure figure,Color c){
            shadowRecGen(gc,figure,Color.GRAY);
        }
        public void shadowRound(GraphicsContext gc,Figure figure,Color c){
            shadowRoundGen(gc,figure,Color.GRAY);
        }
    },
    COLOREADA_INVERSA("Coloreada Inversa",-10){
        public void shadowRec(GraphicsContext gc,Figure figure,Color c){
            shadowRecGen(gc,figure,c);
        }
        public void shadowRound(GraphicsContext gc,Figure figure,Color c){
            shadowRoundGen(gc,figure,c);
        }
    },
    NINGUNA("Ninguna",0){
        public void shadowRec(GraphicsContext gc, Figure figure, Color color){
            return;
        }
        public void shadowRound(GraphicsContext gc, Figure figure, Color color){
            return;
        }
    };


    private final String choiceName;
    private final int offset;
    ShadowEnum(String choiceName, int offset) {
        this.choiceName = choiceName;
        this.offset=offset;
    }



    protected void shadowRecGen(GraphicsContext gc, Figure figure, Color color){
        gc.setFill(color);
        gc.fillRect(figure.getLeft() + offset,figure.getTop() + offset,figure.getWidth(),figure.getHeight());
    }

    protected void shadowRoundGen(GraphicsContext gc, Figure figure, Color color){
        gc.setFill(color);
        gc.fillOval(figure.getLeft() + offset,figure.getTop() +  offset ,figure.getWidth(),figure.getHeight());
    }

    @Override
    public String toString() {
        return choiceName;
    }

    public abstract void shadowRec(GraphicsContext gc, Figure figure,Color c);
    public abstract void shadowRound(GraphicsContext gc, Figure figure,Color c);
}

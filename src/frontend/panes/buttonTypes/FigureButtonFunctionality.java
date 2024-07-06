package frontend.panes.buttonTypes;

import backend.model.Point;
import frontend.front_figures.*;
import frontend.properties.Properties;

public enum FigureButtonFunctionality {
    CICLE_BUTTON{
        FrontFigure makeNewFigure( Point endPoint, Point startPoint, Properties properties) {
            double circleRadius = Math.abs(endPoint.getDistanceX(startPoint));
            return new FrontCircle(startPoint, circleRadius, properties);
        }
    },
    ELLIPSE_BUTTON{
        FrontFigure makeNewFigure( Point endPoint, Point startPoint, Properties properties){
            Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
            double sMayorAxis = Math.abs(endPoint.getDistanceX(startPoint));
            double sMinorAxis = Math.abs(endPoint.getDistanceY(startPoint));
            return new FrontElipse(centerPoint, sMayorAxis, sMinorAxis, properties);
        }
    },
    RECTANGLE_BUTTON{
        FrontFigure makeNewFigure( Point endPoint, Point startPoint, Properties properties){
            return  new FrontRectangle(startPoint, endPoint, properties);
        }
    },
    SQUARE_BUTTON{
        FrontFigure makeNewFigure( Point endPoint, Point startPoint, Properties properties){
            double size = Math.abs(endPoint.getDistanceX(startPoint));
            return new FrontSquare(startPoint, size, properties);
        }
    };
    abstract FrontFigure makeNewFigure( Point endPoint, Point startPoint, Properties properties);
}

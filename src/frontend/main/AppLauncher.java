package frontend.main;

import frontend.front_figures.FrontFigure;
import frontend.properties.Layer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class AppLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		SortedMap<Layer, Pair<Boolean, ArrayList<FrontFigure>>> layersMap = new TreeMap<>(); // BackEnd
		for (int i=1; i<=3 ;i++){
			layersMap.put( new Layer(i),new Pair<>(true,new ArrayList<>()));
		}
		MainFrame frame = new MainFrame(layersMap);
		Scene scene = new Scene(frame);
		primaryStage.setTitle("TPF POO - Paint");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

}
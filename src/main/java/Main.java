import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main extends Application {
    static Factory best;

    public static void main (String [] args)  {

//        BlockingQueue bestFitness = new ArrayBlockingQueue(1);
//        BlockingQueue currentGeneration = new ArrayBlockingQueue(1);
//
//        //Thread algo = new Thread(new GeneticAlgorithm());
//        //algo.start();
//

        launch(args);
        System.out.println("Done");

    }

    @Override
    public void start(Stage primaryStage) throws IOException{


        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GeneticAlgorithm.fxml"));
        primaryStage.setTitle("CSC375hw01");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

//        primaryStage.setTitle("GridPane Experiment");
//
//        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);
//
//        for (int i = 0; i<10; i++){
//            for (int j = 0; j<10; j++){
//                Label l = new Label(" [" + i + "] " + "[" + j + "] ");
//                l.prefHeight(50);
//                l.setFont(new Font("Arial", 20));
//                l.setTextFill(Color.GRAY);
//                l.backgroundProperty().setValue(new Background(new BackgroundFill(Color.ORANGERED, new CornerRadii(2), new Insets(2))));
//                gridPane.add(l, i, j);
//            }
//        }
//        gridPane.setAlignment(Pos.CENTER);
//
//        Scene scene = new Scene(gridPane, 800, 800);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }



}

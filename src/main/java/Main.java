import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main (String [] args)  {


        launch(args);
        runAlgorithm();
        System.out.println("Done");

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GeneticAlgorithm.fxml"));
//        primaryStage.setTitle("CSC375hw01");
//        primaryStage.setScene(new Scene(root, 1200, 771));
//        primaryStage.show();

        primaryStage.setTitle("GridPane Experiment");

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i<10; i++){
            for (int j = 0; j<10; j++){
                Label l = new Label("[" + i + "]" + " [" + j + "]");
                gridPane.add(l, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void runAlgorithm(){
        Generation a = new Generation();
        Factory best = new Factory(10,10);
        best.setFitness(0.0f);
        //a.getBestFactories()[0].printLocalFitnesses();

        for (int i = 0; i<10000; i++) {
                if (i==0) {
                    best = a.getBestFactories()[0];
                }
                System.out.println("\nGeneration " + i + "\nBestFitnesses:");
                for (int j = 0; j < a.getBestFactories().length; j++) {
                    System.out.println(j + ": " + a.getBestFactories()[j].getFactoryFitness());
                }
                if (a.getBestFactories()[0].getFactoryFitness() > best.getFactoryFitness()) {
                    best = a.getBestFactories()[0];
                    //paint new best factory here
                }

                System.out.println("\nAverage Fitness: " + a.averageFitness() + "\n");
                System.out.println("\n\nGeneration View\n\n");
                a.printGeneration();
                System.out.println("\n\n\n--------------------------");

                if (a.getBestFactories()[0].getFactoryFitness() > 185) {
                    a.getBestFactories()[0].printFactoryHeight();
                    break;
                }


            a= a.generateOffSpring();
        }

        //a.getBestFactories()[0].printLocalFitnesses();

    }

}

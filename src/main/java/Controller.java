
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    Label bestFactoryFitnessLabel;

    @FXML
    Label currentGenerationLabel;

    @FXML
    GridPane FactoryGrid;

    @FXML
    Label bestFactoryLabel;

    @FXML
    Label secondBestFactoryLabel;

    @FXML
    Label thirdBestFactoryLabel;

    @FXML
    Label fourthBestFactoryLabel;



    BlockingQueue<Integer> currentGen = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>bestFactory = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>secondBestFactory = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>thirdBestFactory = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>fourthBestFactory = new ArrayBlockingQueue(10);


    public void startAlgorithm(ActionEvent e) throws IOException, InterruptedException {
         ExecutorService executor = Executors.newFixedThreadPool(2);

         Runnable geneticAlgorithm = new Runnable() {
             @Override
             public void run() {
                 Generation a = new Generation();
                 Factory best = new Factory(10, 10);
                 best.setFitness(0.0f);

                 for (int i = 0; i < 10000; i++) {
                     try {
                         Thread.sleep(300);
                     } catch (InterruptedException e){
                         e.printStackTrace();
                     }


                     if (i == 0) {
                         best = a.getBestFactories()[0];
                     } else if (a.getBestFactories()[0].getFactoryFitness() > best.getFactoryFitness()) {
                         best = a.getBestFactories()[0];
                     }

                     Factory f2 = a.getBestFactories()[1];
                     Factory f3 = a.getBestFactories()[2];
                     Factory f4 = a.getBestFactories()[3];

                     System.out.println("\nAverage Fitness: " + a.averageFitness() + "\n");
                     System.out.println("\n\nGeneration View\n\n");
                     a.printGeneration();
                     System.out.println("\n\n\n--------------------------");

                     try {
                         currentGen.put(i);
                         bestFactory.put(best);
                         secondBestFactory.put(f2);
                         thirdBestFactory.put(f3);
                         fourthBestFactory.put(f4);
                     } catch (InterruptedException e) {
                         System.out.println("interrupted");
                     } catch (Exception e){
                         System.out.println("Queue full...");
                     }

                     if (a.getBestFactories()[0].getFactoryFitness() > 190) {
                         a.getBestFactories()[0].printFactoryHeight();
                         break;
                     }

                     a = a.generateOffSpring();
                 }
             }
         };

        Runnable gui = new Runnable() {
            public void run() {
                do {
                    try {
                        final int gen = currentGen.take();
                        final Factory f = bestFactory.take();
                        final Factory f2 = secondBestFactory.take();
                        final Factory f3 = thirdBestFactory.take();
                        final Factory f4 = fourthBestFactory.take();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                currentGenerationLabel.setText(Integer.toString(gen));
                                bestFactoryFitnessLabel.setText(Double.toString(f.getFactoryFitness()));
                                bestFactoryLabel.setText(Double.toString(f.getFactoryFitness()));
                                secondBestFactoryLabel.setText(Double.toString(f2.getFactoryFitness()));
                                thirdBestFactoryLabel.setText(Double.toString(f3.getFactoryFitness()));
                                fourthBestFactoryLabel.setText(Double.toString(f4.getFactoryFitness()));
                                populateGridPane(f);
                            }
                        });


                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                } while (true);
            }
        };

        executor.execute(geneticAlgorithm);
        executor.execute(gui);

    }





    public synchronized void populateGridPane(Factory f) {
        for (int i = 0; i<10; i++) {
            for (int j=0; j<10; j++) {
                int height = f.getStations()[i][j].getHeight();
                Button temp = new Button(Integer.toString(height));

                if (height >0 && height <=20) {
                    temp.setStyle("-fx-background-color: #FFFFFF;");
                } else if (height >20 && height <=40) {
                    temp.setStyle("-fx-background-color: #E0E0E0;");
                } else if (height > 40 && height <=60) {
                    temp.setStyle("-fx-background-color: #C8C8C8;");
                } else if (height >60 && height <=80 ) {
                    temp.setStyle("-fx-background-color:#A8A8A8;");
                } else if (height > 80 && height <=100) {
                    temp.setStyle("-fx-background-color:#787878;");
                } else if (height >100 && height <=120) {
                    temp.setStyle("-fx-background-color:#606060;");
                } else if (height>120 && height <=140) {
                    temp.setStyle("-fx-background-color:#404040;");
                } else if (height > 140 && height <=160) {
                    temp.setStyle("-fx-background-color:#282828;");
                } else if (height >160) {
                    temp.setStyle("-fx-background-color:#000000;");
                }
                FactoryGrid.add(temp, i,j);
            }
        }
    }




}

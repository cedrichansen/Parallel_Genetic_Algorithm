
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

import javax.swing.*;

public class Controller {

    @FXML
    Label bestFactoryFitnessLabel;

    @FXML
    Label currentGenerationLabel;

    BlockingQueue<Integer> currentGen = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>bestFactory = new ArrayBlockingQueue(10);

    public void startAlgorithm(ActionEvent e) throws IOException, InterruptedException {
         ExecutorService executor = Executors.newFixedThreadPool(2);

         Runnable geneticAlgorithm = new Runnable() {
             @Override
             public void run() {
                 Generation a = new Generation();
                 Factory best = new Factory(10, 10);
                 best.setFitness(0.0f);
                 //a.getBestFactories()[0].printLocalFitnesses();

                 for (int i = 0; i < 10000; i++) {
                     try {
                         Thread.sleep(100);
                     } catch (InterruptedException e){
                         System.out.println("");
                     }


                     if (i == 0) {
                         best = a.getBestFactories()[0];
                     } else if (a.getBestFactories()[0].getFactoryFitness() > best.getFactoryFitness()) {
                         best = a.getBestFactories()[0];
                         //paint new best factory here

                     }

                     System.out.println("\nGeneration " + i + "\nBestFitnesses:");
                     for (int j = 0; j < a.getBestFactories().length; j++) {
                         System.out.println(j + ": " + a.getBestFactories()[j].getFactoryFitness());
                     }


                     System.out.println("\nAverage Fitness: " + a.averageFitness() + "\n");
                     System.out.println("\n\nGeneration View\n\n");
                     a.printGeneration();
                     System.out.println("\n\n\n--------------------------");

                     try {
                         //currentGen.clear();
                         currentGen.put(i);
                         bestFactory.put(best);
                     } catch (InterruptedException e) {
                         System.out.println("interrupted");
                     } catch (Exception e){
                         System.out.println("Queue full...");
                     }

                     if (a.getBestFactories()[0].getFactoryFitness() > 185) {
                         a.getBestFactories()[0].printFactoryHeight();
                         //update gui here
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
                        System.out.println("current Gen: "+ gen);
                        final Factory f = bestFactory.take();
                        if (f!=null) {
                            System.out.println("best factory fitness: " + f.getFactoryFitness());
                        }


                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                currentGenerationLabel.setText(Integer.toString(gen));
                                bestFactoryFitnessLabel.setText(Double.toString(f.getFactoryFitness()));
                            }
                        });


                    } catch (InterruptedException ie) {
                    }
                } while (true);
                //executor.shutdownNow();
            }
        };

        executor.execute(geneticAlgorithm);
        executor.execute(gui);

    }


}

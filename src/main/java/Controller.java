
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    Label bestFactoryFitnessLabel;

    @FXML
    Label currentGenerationLabel;

    BlockingQueue<Integer> currentGen = new ArrayBlockingQueue(10);
    BlockingQueue <Factory>bestFactory = new ArrayBlockingQueue(10);

    public void startAlgorithm(ActionEvent e) throws IOException, InterruptedException {
         ExecutorService executor = Executors.newFixedThreadPool(2);
         GeneticAlgorithm a = new GeneticAlgorithm(currentGen, bestFactory);

        Thread gui = new Thread(

                new Runnable() {

            public void run() {

                while (true){
                    try {

                        int currentGenInt = currentGen.take();
                        currentGenerationLabel.setText(Integer.toString(currentGenInt));
                        System.out.println("Just took from current gen queue!" + currentGenInt);
                    } catch (InterruptedException ex) {

                    }
                    try {
                        Factory best = a.bestFactory.take();
                        System.out.println("Just took from best factory queue!  " + best.getFactoryFitness());
                        bestFactoryFitnessLabel.setText(Double.toString(best.getFactoryFitness()));
                    } catch (InterruptedException ex) {

                    }

                }
            }
        });

        executor.execute(a);
        executor.execute(gui);





    }

}

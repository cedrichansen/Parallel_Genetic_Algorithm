import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sun.jvm.hotspot.opto.Block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GeneticAlgorithm implements Runnable {


    BlockingQueue <Integer>currentGen;
    BlockingQueue <Factory>bestFactory;

    GeneticAlgorithm(BlockingQueue gen, BlockingQueue best) {
        currentGen = gen;
        bestFactory = best;
    }



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

                while (bestFactory.remainingCapacity() == 0) {
                    System.out.println("blocking queue for best factory is full!");
                }
                try {
                    bestFactory.put(best);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }

            }

            System.out.println("\nGeneration " + i + "\nBestFitnesses:");
            for (int j = 0; j < a.getBestFactories().length; j++) {
                System.out.println(j + ": " + a.getBestFactories()[j].getFactoryFitness());
            }


            System.out.println("\nAverage Fitness: " + a.averageFitness() + "\n");
            System.out.println("\n\nGeneration View\n\n");
            a.printGeneration();
            System.out.println("\n\n\n--------------------------");

            if (a.getBestFactories()[0].getFactoryFitness() > 185) {
                a.getBestFactories()[0].printFactoryHeight();
                //update gui here
                break;
            }
            while (currentGen.remainingCapacity() == 0) {
                System.out.println("blocking queue for current gen full!");
            }

            try {
                //currentGen.clear();
                currentGen.put(i);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            //update gui here

            a = a.generateOffSpring();
        }

        //a.getBestFactories()[0].printLocalFitnesses();

    }
}


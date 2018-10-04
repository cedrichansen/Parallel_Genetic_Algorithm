public class GeneticAlgorithm implements Runnable {


    public void run() {
        Generation a = new Generation();
        Factory best = new Factory(10, 10);
        best.setFitness(0.0f);
        //a.getBestFactories()[0].printLocalFitnesses();

        for (int i = 0; i < 10000; i++) {
            if (i == 0) {
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
                //update gui here
                break;
            }

            //update gui here

            a = a.generateOffSpring();
        }

        //a.getBestFactories()[0].printLocalFitnesses();

    }
}


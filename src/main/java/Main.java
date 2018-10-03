public class Main {

    public static void main (String [] args) {

        runAlgorithm();
        System.out.println("Done");

    }


    public static void runAlgorithm(){
        Generation a = new Generation();
        //a.getBestFactories()[0].printLocalFitnesses();

        for (int i = 0; i<10000; i++) {
                System.out.println("\nGeneration " + i + "\nBestFitnesses:");
                for (int j = 0; j < a.getBestFactories().length; j++) {
                    System.out.println(j + ": " + a.getBestFactories()[j].getFactoryFitness());
                }

                System.out.println("\nAverage Fitness: " + a.averageFitness() + "\n");
                System.out.println("\n\nGeneration View\n\n");
                a.printGeneration();
                System.out.println("\n\n\n--------------------------");

                if (a.getBestFactories()[0].getFactoryFitness() > 200) {
                    a.getBestFactories()[0].printFactoryHeight();
                    break;
                }


            a= a.generateOffSpring();
        }

        //a.getBestFactories()[0].printLocalFitnesses();

    }

}

public class Main {

    public static void main (String [] args) {

        Factory f = new Factory(10,10);
        f.printFactoryHeight();
        System.out.println("\n\n\n");
        f.printLocalFitnesses();
        System.out.println("\n\n\nFactory Fitness: " + f.getFactoryFitness() + "\nBest Local Fitness: " +  f.getBestFitness().toString() +"\n\n\n");
        Station s = f.getBestFitness();

        // try generating a new factory by taking the best fitness of Factory f and its neighbourt
        Factory f2 = new Factory(10,10, f.getFactorySection(s.getRow()-1, s.getColumn()-1, s.getRow()+1, s.getColumn()+1));
        f2.printFactoryHeight();
        System.out.println("\n\n\n");
        f2.printLocalFitnesses();
        System.out.println("\n\n\nFactory Fitness: " + f2.getFactoryFitness() + "\nBest Local Fitness: " +  f2.getBestFitness().toString() +"\n\n\n");
        Station s2 = f.getBestFitness();


    }

}

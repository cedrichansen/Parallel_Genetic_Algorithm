public class Main {

    public static void main (String [] args) {
        Factory f = new Factory(10,10);
        f.printFactoryHeight();
        System.out.println("\n\n\n\n\n");
        f.printLocalFitnesses();
        System.out.println("\n\n\n\nFactory Fitness: " + f.getFactoryFitness());


    }

}

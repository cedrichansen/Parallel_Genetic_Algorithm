public class Main {

    public static void main (String [] args) {

        Factory f = new Factory(10,10);
        f.printFactoryHeight();
        System.out.println("\n\n\n");
        f.printLocalFitnesses();
        System.out.println("\n\n\nFactory Fitness: " + f.getFactoryFitness());
        Station [] [] section = f.getFactorySection(1,1,3,3);
        System.out.println("");

    }

}

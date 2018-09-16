import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Generation {

    private Factory [][] factories;
    private Factory [] bestFactories;

    private final int NUMROWS = 4;
    private final int NUMCOLUMNS = 8;


    public Generation() {
        factories = generateFactories(NUMROWS,NUMCOLUMNS);
        bestFactories = findBestFactories();
    }

    public Generation(Factory [][] offspring) {
        factories = offspring;
        bestFactories = findBestFactories();
    }

    public Factory [][] generateFactories(int rows,  int columns) {
        Factory [][] factories = new Factory[rows][columns];
        for (int i =0; i<rows; i++) {
            for (int j = 0; j<columns; j++) {
                Factory f = new Factory(10, 10);
                factories[i][j] = f;
            }
        }
        return factories;
    }

    /*
    * find the 4 best factories which will survive to breed new factories
    */
    public Factory[] findBestFactories() {
        bestFactories = new Factory[4];
        bestFactories[0] = factories[0][0];
        int rows =  factories.length;
        int columns = factories[0].length;
        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<columns; j++) {

                Factory temp = factories[i][j];

                   //new best factory
                if (temp.betterThan(bestFactories[0]) && temp != bestFactories[0]){
                    bestFactories[3] = bestFactories[2];
                    bestFactories[2]=bestFactories[1];
                    bestFactories[1] = bestFactories[0];
                    bestFactories[0]= temp;

                    //new second best factory
                } else if (temp.betterThan(bestFactories[1]) && temp != bestFactories[1] && temp != bestFactories[0]){
                    bestFactories[3] = bestFactories[2];
                    bestFactories[2]=bestFactories[1];
                    bestFactories[1] = temp;

                    //new third best factory
                } else if (temp.betterThan(bestFactories[2]) && temp != bestFactories[2] && temp != bestFactories[1] && temp != bestFactories[0]) {
                    bestFactories[3] = bestFactories[2];
                    bestFactories[2]= temp;

                    //new third best factory
                } else if (temp.betterThan(bestFactories[3]) && temp != bestFactories[3] && temp != bestFactories[2] && temp != bestFactories[1] && temp != bestFactories[0]){
                    bestFactories[3] = temp;
                }
            }
        }

        return bestFactories;
    }


    /*
     * establish random points to establish section of factory which will be passed onto offspring
     * Make sure no section will be generated bigger than 7x7
     */
    public static int[] generateCrossoverPoints(int rows, int columns){
        Random random = new Random();

        // [0] start row
        // [1] start column
        // [2] end row
        // [3] end column
        int startRow = random.nextInt(rows-1);
        int startColumn = random.nextInt(columns-1);
        int endRow, endColumn;

        if (rows-startRow < 8) {
            endRow = startRow + random.nextInt(rows - startRow);
        } else {
            endRow = startRow + random.nextInt(8);
        }

        if (columns - startColumn <8) {
            endColumn = startColumn + random.nextInt(columns - startColumn);
        } else {
            endColumn = startColumn + random.nextInt(8);
        }

        int [] points = {startRow, startColumn, endRow, endColumn};
        return points;
    }



    public Generation generateOffSpring(){
        Factory[][] offSpringFactories = new Factory[NUMROWS][NUMCOLUMNS];
        offSpringFactories[0][0] = bestFactories[0];
        offSpringFactories[1][0] = bestFactories[1];
        offSpringFactories[2][0] = bestFactories[2];
        offSpringFactories[3][0] = bestFactories[3];


        //generate new factories which are offspring of other factories
        for (int i = 0; i<NUMROWS; i++) {
            for (int j=1; j<NUMCOLUMNS; j++) {
                    // idea: take 2 random of the best 4 factories. One parent will contribute a subsection of its genes
                    //which at most is a 7x7 section, and the other parent will contribute the rest of the genes
                    // the mutation rate is specified in the constructor as mutation rate (which is a % value)

                    Collections.shuffle(Arrays.asList(bestFactories));
                    int [] points = generateCrossoverPoints(bestFactories[0].getRows(), bestFactories[0].getColumns());
                    Station [][] subsection = bestFactories[1].getFactorySection(points[0],points[1],points[2],points[3]);
                    Factory f = new Factory(subsection, bestFactories[0], 5 );
                    offSpringFactories[i][j] = f;

            }
        }

        Generation ga = new Generation(offSpringFactories);

        return ga;
    }


    public void printGeneration() {
        for (Factory [] f : factories) {
            String rowString = "";
            for (Factory fac : f) {
             rowString+= fac.toString() + "  ";
            }
            System.out.println(rowString);
        }
    }






    public Factory[] getBestFactories(){
        return this.bestFactories;
    }




}

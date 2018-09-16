
import java.util.ArrayList;
import java.util.Random;

public class Factory {

    private final double mutationRate = 0.1;
    private Station[][] stations;
    private int rows, columns;
    private float fitness;
    private int totalSpots;
    private int id;
    private ArrayList<Station> listOfStations;
    private ArrayList<int[]> swaps;
    private Random random;
    private boolean selected;
    private double factoryFitness;
    private Station bestFitness;

    /*
     * this constructor is used to generate the first generation of factories
     */
    public Factory(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        totalSpots = rows * columns;
        random = new Random();
        factoryFitness = 0;
        listOfStations = new ArrayList<Station>();
        swaps = new ArrayList<int[]>();
        selected = false;
        stations = new Station[rows][columns];
        stations = generateStations(rows, columns);
        assignNeighbours();
        calculateLocalFitness();
        calculateFactoryFitness();

    }

    /*
     * this constructor is used for a factory which is the offspring of another
     */
    public Factory(int rows, int columns, Station [][] subsection) {
        this.rows = rows;
        this.columns = columns;
        totalSpots = rows * columns;
        random = new Random();
        factoryFitness = 0;
        listOfStations = new ArrayList<Station>();
        swaps = new ArrayList<int[]>();
        selected = false;
        stations = new Station[rows][columns];
        insertSubsection(subsection);
        stations = generateStations(rows, columns);
        assignNeighbours();
        calculateLocalFitness();
        calculateFactoryFitness();
    }

    /*
     * Fills in the empty spots in the factory with new stations of random height
     */
    public Station[][] generateStations(int rows, int columns) {
        //Station[][] stations = new Station[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (stations[i][j] == null) {
                    Station s = new Station(random.nextInt((int) (2 * totalSpots)), i, j);
                    stations[i][j] = s;
                    listOfStations.add(s);
                }
            }
        }
        return stations;
    }

        /*
         * get the surrounding Stations and assign to each items neighbours list
         * makes calculating fitness easier
         */

    public void assignNeighbours() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stations[i][j].setNeighbours(new ArrayList<Station>());
                for (int a = -1; a < 2; a++) {
                    for (int b = -1; b < 2; b++) {
                        if (validSpot(i + a, j + b) && !(b == 0 && a == 0)) {
                            stations[i][j].addNeighbour(stations[i + a][j + b]);
                        }
                    }
                }
            }
        }
    }

    /*
     * establish random points to establish section of factory which will be passed onto offspring
     */
    public int[] generateCrossoverPoints(){
        // [0] start row
        // [1] start column
        // [2] end row
        // [3] end column
        int startRow = random.nextInt(rows-1);
        int startColumn = random.nextInt(columns-1);
        int endRow, endColumn;

        if (rows-startRow < 6) {
            endRow = startRow + random.nextInt(rows - startRow);
        } else {
            endRow = startRow + random.nextInt(6);
        }

        if (columns - startColumn <6) {
            endColumn = startColumn + random.nextInt(columns - startColumn);
        } else {
            endColumn = startColumn + random.nextInt(6);
        }

        int [] points = {startRow, startColumn, endRow, endColumn};
        return points;

    }

    /*
     * function checks to see if a specific index is out of bounds. Returns true if index is within array bounds
     */
    public boolean validSpot(int i, int j) {
        return (i >= 0 && i < rows && j >= 0 && j < columns);
    }

    /*
     * calculate fitness of each station by taking the inverse of the sum of differences between each station, and its
     * neighbours
     *
     * exclude calculating the edges and corners because their values are always skewed to be low
     */
    public void calculateLocalFitness() {
        bestFitness = stations[1][1];
        for (int i = 1; i < rows-1; i++) {
            for (int j = 1; j < columns-1; j++) {
                int sum = 0;
                for (Station s : stations[i][j].getNeighbours()) {
                    sum += Math.abs(stations[i][j].getHeight() - s.getHeight());
                }
                stations[i][j].setLocalFitness(1000 / (double) sum);
                if (stations[i][j].getLocalFitness() > bestFitness.getLocalFitness()) {
                    bestFitness = stations[i][j];
                }
            }
        }
    }


    /* calculate the factory fitness
     * which is equal to the sum of all station localFitnesses
     */

    public void calculateFactoryFitness() {
        double factoryFitness = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                factoryFitness += stations[i][j].getLocalFitness();
            }
        }
        this.factoryFitness = factoryFitness;
    }


    /*
     * returns a sub section of the factory
     */

    public Station[][] getFactorySection(int startRow, int startColumn, int endRow, int endColumn) {
        Station[][] subsection = new Station[(endRow+1) - startRow][(endColumn+1) - startColumn];

        if ((startColumn > -1) && (startRow > -1) && (endRow < rows) && (endColumn < columns) && (startColumn<=endColumn) && (startRow<=endRow)) {
            for (int i = startRow; i <= endRow; i++) {
                for (int j = startColumn; j <= endColumn; j++) {
                    subsection[i - startRow][j - startColumn] = stations[i][j];
                }
            }
        }

        return subsection;
    }


    /*
     * This function is used to insert a factory subsection (obtained from getFactorySection() ) from another factory
     *  into the another (presumably new) factory
     */

    public void insertSubsection(Station[][] subsection){
        Station topLeftCorner = subsection[0][0];
        Station bottomRightCorner = subsection[subsection.length-1][subsection[0].length-1];
        for (int i = 0; i<=bottomRightCorner.getRow()-topLeftCorner.getRow();i++) {
            for (int j = 0; j<=bottomRightCorner.getColumn()-topLeftCorner.getColumn(); j++) {
                Station temp = subsection[i][j];
                stations[i+topLeftCorner.getRow()][j+topLeftCorner.getColumn()]= temp;
            }
        }
    }


    //only configured to work properly for 10x10 factory
    public void printFactoryHeight() {
        for (int i = 0; i < rows; i++) {
            String rowString = "";
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1) {
                    if (stations[i][j].getHeight() < 10) {
                        rowString += stations[i][j].getHeight() + "   | ";
                    } else if (stations[i][j].getHeight() >= 100) {
                        rowString += stations[i][j].getHeight() + " | ";
                    } else {
                        rowString += stations[i][j].getHeight() + "  | ";
                    }

                } else {
                    rowString += stations[i][j].getHeight();
                }

            }
            System.out.println(rowString);
            if (i != rows - 1) {
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    // prints fitnesses of
    public void printLocalFitnesses() {
        for (int i = 0; i < rows; i++) {
            String rowString = "";
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1) {
                    if (stations[i][j].getLocalFitness() < 1) {
                        rowString += stations[i][j].getLocalFitnessToPrint() + "  | ";
                    }else if (stations[i][j].getLocalFitness() < 10) {
                        rowString += stations[i][j].getLocalFitnessToPrint() + " | ";
                    }
                     else {
                        rowString += stations[i][j].getLocalFitnessToPrint() + "| ";
                    }
                } else {
                    rowString += stations[i][j].getLocalFitnessToPrint();
                }

            }
            System.out.println(rowString);
            if (i != rows - 1) {
                System.out.println("----------------------------------------------------------");
            }
        }
    }





    public Station getBestFitness() {
        return bestFitness;
    }

    public double getFactoryFitness() {
        return factoryFitness;
    }

    public Station[][] getStations() {
        return stations;
    }

    public void setStations(Station[][] stations) {
        this.stations = stations;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getId() {
        return id;
    }

    public void setFactoryId(int factoryId) {
        this.id = factoryId;
    }

    public ArrayList<Station> getListOfStations() {
        return listOfStations;
    }

    public void setListOfStations(ArrayList<Station> listOfStations) {
        this.listOfStations = listOfStations;
    }

    public ArrayList<int[]> getSwaps() {
        return swaps;
    }

    public void setSwaps(ArrayList<int[]> swaps) {
        this.swaps = swaps;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}

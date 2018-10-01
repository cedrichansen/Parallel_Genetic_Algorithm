
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Factory implements Comparable, Runnable {

    private DecimalFormat df2 = new DecimalFormat(".#");

    private Station[][] stations;
    private int rows, columns;
    private float fitness;
    private int totalSpots;
    private int id;
    private ArrayList<Station> listOfStations;
    private Random random;
    private double factoryFitness=0;
    private Station bestFitness;
    private boolean originalFactory;
    private Station[][] subsection;
    private int mutationRate;


    /*
     * this constructor is used to generate the first generation of factories
     */
    public Factory(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        totalSpots = rows * columns;
        random = new Random();
        stations = new Station[rows][columns];
        originalFactory=true;
        //stations = generateStations(rows, columns);

        //comment all this out

//        assignNeighbours();
//        assignDistantNeighbours();
//        calculateLocalFitness();
//        calculateFactoryFitness();

    }


    /*
    *  used to create a factory which is a crossover between 2 parents. One parents gives a subsection, the other parent
    *  gives the rest of the genes
    */
    public Factory(Station[][] subsection, Factory secondParent, int mutationRate) {
        this.rows = secondParent.getRows();
        this.columns = secondParent.getColumns();
        totalSpots = rows * columns;
        random = new Random();
        originalFactory=false;

        this.subsection = subsection;
        stations = secondParent.stations;
        this.mutationRate = mutationRate;


        //comment all thus out
//        insertSubsection(subsection);
//        mutate(mutationRate);
//
//        assignNeighbours();
//        assignDistantNeighbours();
//        calculateLocalFitness();
//        calculateFactoryFitness();

    }


    public void run() {
        System.out.println("Running a thread: " + Thread.currentThread().getId());
        if (!originalFactory) {
            insertSubsection(this.subsection);
            mutate(this.mutationRate);
        } else {
            stations = generateStations(rows, columns);
        }
        assignNeighbours();
        assignDistantNeighbours();
        calculateLocalFitness();
        calculateFactoryFitness();
        System.out.println("thread is done: " + Thread.currentThread().getId());
    }

    /*
     * Fills in the empty spots in the factory with new stations of random height
     */
    public synchronized Station[][] generateStations(int rows, int columns) {
        //Station[][] stations = new Station[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (stations[i][j] == null) {
                    Station s = new Station(random.nextInt((int) (2 * totalSpots)), i, j);
                    stations[i][j] = s;
                }
            }
        }
        return stations;
    }


    /*
     * For any given square, there is a mutationrate % chance that the station's height changes to a new random value
     */
    public synchronized void mutate(int mutationRate) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (random.nextInt(100) < mutationRate) {
                    Station s = new Station(random.nextInt((int) (2 * totalSpots)), i, j);
                    stations[i][j] = s;
                }
            }
        }


    }



        /*
         * get the surrounding Stations and assign to each items neighbours list
         * makes calculating fitness easier
         */

    public synchronized void assignNeighbours() {
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

    public synchronized void assignDistantNeighbours() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Station current = stations[i][j];
                current.setDistantNeighbours(new ArrayList<Station>());
                int distanceToBottom = this.rows-1-current.getRow();
                int distanceToSide = this.columns-1-current.getColumn();

                //Yes I know this is a mess...

                if (distanceToBottom > 1 && distanceToSide > 1) {
                    current.getDistantNeighbours().add(stations[i + 2][j + 2]);
                }
                if (distanceToBottom > 1 && distanceToSide > 0) {
                    current.getDistantNeighbours().add(stations[i + 2][j + 1]);
                }
                if (distanceToBottom >1 ) {
                    current.getDistantNeighbours().add(stations[i + 2][j]);
                }
                if (distanceToBottom > 1 && current.getColumn() >0) {
                    current.getDistantNeighbours().add(stations[i + 2][j - 1]);
                }
                if (distanceToBottom>1 && current.getColumn() > 1) {
                    current.getDistantNeighbours().add(stations[i + 2][j - 2]);
                }
                if (distanceToBottom > 0 && current.getColumn() > 1 ) {
                    current.getDistantNeighbours().add(stations[i + 1][j - 2]);
                }
                if (distanceToBottom > 0 && distanceToSide > 1) {
                    current.getDistantNeighbours().add(stations[i + 1][j + 2]);
                }
                if (current.getColumn() > 1) {
                    current.getDistantNeighbours().add(stations[i][j - 2]);
                }
                if (distanceToSide > 1) {
                    current.getDistantNeighbours().add(stations[i][j + 2]);
                }
                if (current.getRow() >0 && current.getColumn()>1) {
                    current.getDistantNeighbours().add(stations[i - 1][j - 2]);
                }
                if (current.getRow()>0 && distanceToSide >1) {
                    current.getDistantNeighbours().add(stations[i - 1][j + 2]);
                }
                if (current.getRow()>1 && current.getColumn() >1) {
                    current.getDistantNeighbours().add(stations[i - 2][j - 2]);
                }
                if (current.getRow() >1 && current.getColumn() >0) {
                    current.getDistantNeighbours().add(stations[i - 2][j - 1]);
                }
                if (current.getRow() > 1) {
                    current.getDistantNeighbours().add(stations[i - 2][j]);
                }
                if (current.getRow() >1 && distanceToSide>0) {
                    current.getDistantNeighbours().add(stations[i - 2][j + 1]);
                }
                if (current.getRow()>1 && distanceToSide >1) {
                    current.getDistantNeighbours().add(stations[i - 2][j + 2]);
                }
            }
        }
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
    public synchronized void calculateLocalFitness() {
        bestFitness = stations[1][1];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int sum = 0;
                for (Station s : stations[i][j].getNeighbours()) {
                    sum += Math.abs(stations[i][j].getHeight() - s.getHeight());
                }
                for (Station s : stations[i][j].getDistantNeighbours()) {
                    sum += (0.5*(Math.abs(stations[i][j].getHeight()-s.getHeight())));
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

    public synchronized void calculateFactoryFitness() {
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
        Station[][] subsection = new Station[(endRow + 1) - startRow][(endColumn + 1) - startColumn];

        if ((startColumn > -1) && (startRow > -1) && (endRow < rows) && (endColumn < columns) && (startColumn <= endColumn) && (startRow <= endRow)) {
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

    public synchronized void insertSubsection(Station[][] subsection) {
        Station topLeftCorner = subsection[0][0];
        Station bottomRightCorner = subsection[subsection.length - 1][subsection[0].length - 1];
        for (int i = 0; i <= bottomRightCorner.getRow() - topLeftCorner.getRow(); i++) {
            for (int j = 0; j <= bottomRightCorner.getColumn() - topLeftCorner.getColumn(); j++) {
                Station temp = subsection[i][j];
                stations[i + topLeftCorner.getRow()][j + topLeftCorner.getColumn()] = temp;
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
                    } else if (stations[i][j].getLocalFitness() < 10) {
                        rowString += stations[i][j].getLocalFitnessToPrint() + " | ";
                    } else {
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

    public boolean betterThan(Factory f) {
        if (f == null) {
            return true;
        }
        if (this.factoryFitness - f.getFactoryFitness() > 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return df2.format(this.factoryFitness);
    }


    public int compareTo(Object o) {

        if (((Factory) o).factoryFitness - this.factoryFitness > 0) {
            return 1;
        } else if (((Factory) o).factoryFitness - this.factoryFitness < 0) {
            return -1;
        } else {
            return 0;
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


    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }


    public boolean isOriginalFactory() {
        return originalFactory;
    }

    public void setOriginalFactory(boolean originalFactory) {
        this.originalFactory = originalFactory;
    }



}

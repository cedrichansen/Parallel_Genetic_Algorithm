import java.util.ArrayList;
import java.util.Random;

public class Factory {

    // to do
    // write a function that gets all the neighbouring stations
    // for fitness function, do the least sum difference of neighbours


    private Station[][] stations;
    private int rows, columns;
    private float fitness;
    private int totalSpots;
    private int factoryId;
    private ArrayList<Station> listOfStations;
    private ArrayList<int[]> swaps;
    private Random random;
    private boolean selected;
    private double factoryFitness;

    public Factory(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        totalSpots = rows * columns;
        random = new Random();
        factoryFitness = 0;
        listOfStations = new ArrayList<Station>();
        swaps = new ArrayList<int[]>();
        selected = false;
        stations = generateStations(rows, columns);
        assignNeighbours();
        calculateLocalFitness();
        calculateFactoryFitness();

    }

    public Station[][] generateStations(int rows, int columns) {
        Station[][] stations = new Station[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Station s = new Station(random.nextInt(2* totalSpots), i, j);
                stations[i][j] = s;
                listOfStations.add(s);
            }
        }
        return stations;
    }


    public void assignNeighbours(){

        //get the surrounding Stations and assign to each items neighbours list
        // makes calculating fitness easier

        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {

                for (int a = -1; a < 2; a++) {
                    for (int b = -1; b< 2; b++){
                        if (validSpot(i+a,j+b) && !(b == 0 && a==0)) {
                            stations[i][j].addNeighbour(stations[i+a][j+b]);
                        }
                    }
                }
            }
        }
    }

    //function checks to see if a specific index is out of bounds. Returns true if index is within array bounds
    public boolean validSpot(int i, int j) {
         return (i >=0 && i<rows && j>=0 && j<columns);
    }

    public void calculateLocalFitness(){
        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<columns; j++) {
                int sum = 0;
                for (Station s : stations[i][j].getNeighbours()) {
                    sum += Math.abs(stations[i][j].getHeight() - s.getHeight());
                }
                stations[i][j].setLocalFitness(1000/(double)sum);
            }
        }
    }

    public void calculateFactoryFitness(){
        double factoryFitness = 0;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++){
                factoryFitness += stations[i][j].getLocalFitness();
            }
        }
        this.factoryFitness = factoryFitness;
    }




    //only configured to work properly for 10x10 factory
    public void printFactoryHeight() {
        for (int i = 0; i < rows; i++) {
            String rowString = "";
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1) {
                    if (stations[i][j].getHeight() < 10) {
                        rowString += stations[i][j].getHeight() + "   | ";
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


    public void printLocalFitnesses(){
        for (int i = 0; i < rows; i++) {
            String rowString = "";
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1) {
                    rowString += stations[i][j].getLocalFitnessToPrint() + "  | ";
                } else {
                    rowString += stations[i][j].getLocalFitnessToPrint();
                }

            }
            System.out.println(rowString);
            if (i != rows - 1) {
                System.out.println("------------------------------------------------------------------------");
            }
        }
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

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
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

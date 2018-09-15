import java.util.ArrayList;
import java.text.DecimalFormat;

public class Station {

    private DecimalFormat df2 = new DecimalFormat(".#");

    private int height;
    private int row;
    private int column;
    private ArrayList<Station> neighbours;



    private double localFitness;


    public Station(int height, int row, int column){
        this.height = height;
        this.row = row;
        this.column = column;
        neighbours = new ArrayList<Station>();
    }

    public String toString() {
        return "[" + row + "] " + "[" + column + "] + height: " + height;
    }

    public void setLocalFitness(double localFitness){
        this.localFitness = localFitness;
    }

    public double getLocalFitness() {
        return localFitness;
    }

    public String getLocalFitnessToPrint(){
        return df2.format(localFitness);
    }

    public void addNeighbour(Station s){
        this.neighbours.add(s);
    }

    public ArrayList<Station> getNeighbours() {
        return neighbours;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


}

package edu.unq.pconc.gameoflife.solution;

public class Celda {
    int column;
    int row;

    public Celda(int col, int row) {
        this.column = col;
        this.row = row;
    }

    @Override
    public String toString() {
        return "("+column+","+row+")";
    }
}

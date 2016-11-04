package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;

import java.awt.*;

/**
 * Created by coord-inf on 04/11/16.
 */
public class GameOfLifeGrid implements CellGrid{
    private Dimension dimension = new Dimension(50,50);


    @Override
    public boolean getCell(int col, int row) {
        return false;
    }

    @Override
    public void setCell(int col, int row, boolean cell) {

    }

    @Override
    public Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public void resize(int col, int row) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void setThreads(int threads) {

    }

    @Override
    public int getGenerations() {
        return 0;
    }

    @Override
    public void next() {

    }
}

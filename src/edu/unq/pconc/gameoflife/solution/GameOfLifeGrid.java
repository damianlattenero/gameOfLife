package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;
import javafx.scene.control.Cell;

import java.awt.*;

/**
 * Created by coord-inf on 04/11/16.
 */
public class GameOfLifeGrid implements CellGrid{
    private Dimension dimension;
    private boolean grid[][];

//    private Dimension dimension = new Dimension(50,50);

    public GameOfLifeGrid() {
        this.dimension = new Dimension();
        this.grid = new boolean[0][0];
    }

    @Override
    public boolean getCell(int col, int row) {
        return grid[col][row];
    }

    @Override
    public void setCell(int col, int row, boolean cell) {
        grid[col][row] = cell;
    }

    @Override
    public Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public void resize(int col, int row) {
        this.dimension = new Dimension(col, row);
        this.grid = new boolean[col][row];
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

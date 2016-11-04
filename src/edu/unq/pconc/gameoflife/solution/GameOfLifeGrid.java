package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;
import javafx.scene.control.Cell;

import java.awt.*;

/**
 * Created by coord-inf on 04/11/16.
 */
public class GameOfLifeGrid implements CellGrid {
    private Dimension dimension;
    private boolean grid[][];
    private int generations;

//    private Dimension dimension = new Dimension(50,50);

    public GameOfLifeGrid() {
        this.dimension = new Dimension();
        this.grid = new boolean[0][0];
        this.generations = 0;
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
    public void resize(int widht, int height) {
        this.dimension = new Dimension(20, 50);
        this.grid = new boolean[20][50];
    }

    @Override
    public void clear() {
        this.grid = new boolean[this.getDimension().width][this.getDimension().height];
    }

    @Override
    public void setThreads(int threads) {

    }

    @Override
    public int getGenerations() {
        return this.generations;
    }

    @Override
    public void next() {
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                this.vivirOMorir(col, row);
            }
        }
        this.generations++;
    }

    private void vivirOMorir(int col, int row) {
        boolean estaViva = grid[row][col];
        int vecinasVivas = 0;

        int rowStart = Math.max(row - 1, 0);
        int rowFinish = Math.min(row + 1, grid.length - 1);
        int colStart = Math.max(col - 1, 0);
        int colFinish = Math.min(col + 1, grid.length - 1);

        for (int curRow = rowStart; curRow <= rowFinish; curRow++) {
            for (int curCol = colStart; curCol <= colFinish; curCol++) {
                // do something
            }
        }
        if (estaViva) {
            this.celdaVivaViveOMuere(col, row, vecinasVivas);
        } else {
            this.celdaMuertaViveOMuere(col, row, vecinasVivas);

        }

    }

    private void celdaMuertaViveOMuere(int col, int row, int vecinasVivas) {
        if (vecinasVivas == 3) {
            grid[row][col] = true;
        }
    }

    private void celdaVivaViveOMuere(int col, int row, int vecinasVivas) {
        if (vecinasVivas < 2) {
            grid[row][col] = false;
        }
        if (vecinasVivas > 3) {
            grid[row][col] = false;
        }
        if(vecinasVivas == 2 || vecinasVivas == 3){
            grid[row][col] = true;
        }

    }
}

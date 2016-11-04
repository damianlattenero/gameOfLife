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
        this.dimension = new Dimension(widht, height);
        this.grid = new boolean[widht][height];
    }

    @Override
    public void clear() {
        this.grid = new boolean[this.getDimension().width][this.getDimension().height];
    }

    @Override
    public void setThreads(int threads) {
        for(int n=0; n<threads; n++){
            Thread t = new Thread();
            t.run();
        }
    }

    @Override
    public int getGenerations() {
        return this.generations;
    }

    @Override
    public void next() {
        boolean[][] grilla = new boolean[(int) this.dimension.getWidth()][(int) this.dimension.getHeight()];
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                this.vivirOMorir(row, col, grilla);
            }
        }
        this.generations++;
        this.grid = grilla;
    }

    private void vivirOMorir(int col, int row, boolean[][] grilla) {

        boolean[] vecinos = new boolean[10];
        try {
            vecinos[0] = grid[col - 1][row - 1];
            vecinos[1] = grid[col][row - 1];
            vecinos[2] = grid[col + 1][row - 1];
            vecinos[3] = grid[col - 1][row];
            vecinos[4] = grid[col + 1][row];
            vecinos[5] = grid[col - 1][row + 1];
            vecinos[6] = grid[col][row + 1];
            vecinos[7] = grid[col + 1][row + 1];
        } catch (Exception e) {
            //
        }


        int vecinasVivas = 0;

        for (int elem = 0; elem <= 7; elem++) {
            if (vecinos[elem] == true) {
                vecinasVivas++;
            }
        }
        if (grid[col][row]) {
            this.celdaVivaViveOMuere(col, row, vecinasVivas, grilla);
        } else {
            this.celdaMuertaViveOMuere(col, row, vecinasVivas, grilla);
        }

    }

    private void celdaMuertaViveOMuere(int col, int row, int vecinasVivas, boolean[][] grilla) {
        if (vecinasVivas == 3) {
            grilla[col][row] = true;
        }
    }

    private void celdaVivaViveOMuere(int col, int row, int vecinasVivas, boolean[][] grilla) {
        if (vecinasVivas < 2) {
            grilla[col][row] = false;
        }
        if (vecinasVivas > 3) {
            grilla[col][row] = false;

        }
        if (vecinasVivas == 3 || vecinasVivas == 2) {
            grilla[col][row] = true;
        }
    }
}

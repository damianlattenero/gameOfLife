package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;
import javafx.scene.control.Cell;
import sun.plugin.javascript.navig.Array;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by coord-inf on 04/11/16.
 */
public class GameOfLifeGrid implements CellGrid {
    private Dimension dimension;
    public boolean[][] grid;
    private int generations;
    private int threads;
    public  int threadsActivos;
//    private Dimension dimension = new Dimension(50,50);

    public GameOfLifeGrid() {
        this.dimension = new Dimension();
        this.grid = new boolean[0][0];
        this.generations = 0;
        this.threads = 0;
        this.threadsActivos = 0;
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
        this.threads = threads;
    }

    @Override
    public int getGenerations() {
        return this.generations;
    }

    @Override
    public void next() {
        boolean[][] grilla = new boolean[(int) this.dimension.getWidth()][(int) this.dimension.getHeight()];
        this.distribuirTrabajoEquitativamente(grilla);

        while(this.hayThreadsTrabajando()){
            synchronized(this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.generations++;
        this.grid = grilla;
        this.threadsActivos = this.threads;
    }

    private synchronized void distribuirTrabajoEquitativamente(boolean[][] grilla) {
        boolean[][] arrayPartition = grid;
        List<boolean[][]> arrayPartition2 = new ArrayList<boolean[][]>();
        this.threadsActivos = this.threads;

        for(int n = 0; n<this.threads; n++){
            Thread t = new CheckerThread(this, arrayPartition, grilla);
            t.start();
        }
    }

    private synchronized boolean hayThreadsTrabajando() {
        return threadsActivos > 0;
    }

}

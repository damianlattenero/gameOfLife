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
        this.threadsActivos = 0;
    }

    @Override
    public int getGenerations() {
        return this.generations;
    }

    @Override
    public synchronized void next() {
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
    }

    private void distribuirTrabajoEquitativamente(boolean[][] grilla) {
        List<Celda> celdas = new ArrayList<Celda>();
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[0].length; col++) {
                celdas.add(new Celda(col,row));
            }
        }

        int totalCell = this.grid.length * this.grid[0].length;
        int normalWorkForThreads = (Math.floorDiv(totalCell, this.threads));
        int numberOfThreadsWithExtraWork = totalCell - (normalWorkForThreads * this.threads);
        int extraWorkForThread = normalWorkForThreads + 1;
        int numberOfThreadsWithNormalWork = this.threads - numberOfThreadsWithExtraWork;
        System.out.println(totalCell);
        System.out.println(numberOfThreadsWithExtraWork);
        System.out.println(numberOfThreadsWithNormalWork);

        int takeNexExtra = extraWorkForThread;

        for(int n = 0; n<numberOfThreadsWithExtraWork; n++){

            Thread t = new CheckerThread(this, celdas.subList(0, takeNexExtra), grilla);
            System.out.println(extraWorkForThread);
            takeNexExtra+=extraWorkForThread;
            this.threadsActivos ++;
            t.start();
        }

        int takeNexNormal = normalWorkForThreads;


        for(int n = 0; n<numberOfThreadsWithNormalWork; n++){

            Thread t = new CheckerThread(this, celdas.subList(0, takeNexNormal), grilla);
            System.out.println(normalWorkForThreads);
            takeNexNormal+=normalWorkForThreads;
            this.threadsActivos++;
            t.start();
        }
    }

    private boolean hayThreadsTrabajando() {
        return threadsActivos > 0;
    }

}

package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeGrid implements CellGrid {
    private Dimension dimension;
    public boolean[][] grid;
    private int generations;
    private int threads;
    public int threadsActivos;

    public GameOfLifeGrid() {
        this.dimension = new Dimension();
        this.grid = new boolean[0][0];
        this.generations = 0;
        this.threads = 0;
        this.threadsActivos = 0;
    }

    @Override
    public synchronized boolean getCell(int col, int row) {
        return grid[col][row];
    }

    @Override
    public synchronized void setCell(int col, int row, boolean cell) {
        grid[col][row] = cell;
    }

    @Override
    public synchronized Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public synchronized void resize(int widht, int height) {
        boolean[][] newGrid = new boolean[widht][height];
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[row].length; col++) {
                if(col < height && row < widht){
                    newGrid[row][col] = grid[row][col];
                }
            }
        }

        this.dimension = new Dimension(widht, height);
        this.grid = newGrid;
    }


    @Override
    public synchronized void clear() {
        this.grid = new boolean[this.getDimension().width][this.getDimension().height];
    }

    @Override
    public synchronized void setThreads(int threads) {
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
        List<CheckerThread> ls = this.distribuirTrabajoEquitativamente(grilla);
        for (Thread t :
                ls) {
            this.threadsActivos++;
            t.start();
        }

        synchronized (this) {
            while (this.hayThreadsTrabajando()) {
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

    private synchronized List<CheckerThread> distribuirTrabajoEquitativamente(boolean[][] grilla) {
        List<Celda> celdas = new ArrayList<Celda>();
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[row].length; col++) {
                celdas.add(new Celda(col, row));
            }
        }

        int totalCell = this.grid.length * this.grid[0].length;
        int normalWorkForThreads = (totalCell / this.threads);
        int numberOfThreadsWithExtraWork = totalCell - (normalWorkForThreads * this.threads);
        int extraWorkForThread = normalWorkForThreads + 1;
        int numberOfThreadsWithNormalWork = this.threads - numberOfThreadsWithExtraWork;

        List<CheckerThread> threads = new ArrayList<>();


        threads.addAll(this.assignWork(numberOfThreadsWithNormalWork, normalWorkForThreads, celdas, grilla));
        threads.addAll(this.assignWork(numberOfThreadsWithExtraWork, extraWorkForThread, celdas, grilla));

        return threads;

    }

    synchronized private List<CheckerThread> assignWork(int numberOfThreads, int workForThreads, List<
            Celda> celdas, boolean[][] grilla) {
        List<CheckerThread> threads = new ArrayList<>();

        for (int n = 0; n < numberOfThreads
                ; n++) {

            List<Celda> work = this.takeN(workForThreads, celdas);
            System.out.println(celdas.size() + "tamaño antes remove");
            celdas.removeAll(work);
            System.out.println(celdas.size() + "tamaño desp remove");
            CheckerThread t = new CheckerThread(this, work, grilla);
            threads.add(t);
        }


        return threads;

    }

    private synchronized List<Celda> takeN(int workForThreads, List<Celda> celdas) {
        List<Celda> ret = new ArrayList<>();
        for (int n = 0; n < workForThreads; n++) {
            ret.add(celdas.get(n));
        }
        return ret;
    }

    private synchronized boolean hayThreadsTrabajando() {
        return threadsActivos > 0;
    }

}

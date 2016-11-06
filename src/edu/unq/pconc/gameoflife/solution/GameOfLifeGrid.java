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
    private boolean noTerminoDeAsignarTareas;

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
        this.dimension = new Dimension(widht, height);
        this.grid = new boolean[widht][height];
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
        this.distribuirTrabajoEquitativamente(grilla);

        while (this.hayThreadsTrabajando()) {
            synchronized (this) {
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

    private synchronized void distribuirTrabajoEquitativamente(boolean[][] grilla) {
        List<Celda> celdas = new ArrayList<Celda>();
        for (int row = 0; row < this.grid.length; row++) {
            for (int col = 0; col < this.grid[row].length; col++) {
                celdas.add(new Celda(col, row));
            }
        }

        int totalCell = this.grid.length * this.grid[0].length;
        int normalWorkForThreads = (totalCell / this.threads);
        int resto = (totalCell % this.threads);
        int numberOfThreadsWithExtraWork = totalCell - (normalWorkForThreads * this.threads);
        int extraWorkForThread = normalWorkForThreads + 1;
        int numberOfThreadsWithNormalWork = this.threads - numberOfThreadsWithExtraWork;

        List<Thread> threads = new ArrayList<>();

        this.noTerminoDeAsignarTareas = true;
        threads.addAll(this.assignWork(numberOfThreadsWithNormalWork, normalWorkForThreads, celdas, grilla));
        threads.addAll(this.assignWork(numberOfThreadsWithExtraWork, extraWorkForThread, celdas, grilla));
        this.noTerminoDeAsignarTareas = false;
        this.notifyAll();
        for (Thread t :
                threads) {
            this.threadsActivos++;
            t.start();
        }
    }

    synchronized private  List<Thread> assignWork(int numberOfThreads, int workForThreads, List<Celda> celdas, boolean[][] grilla) {
        List<Thread> threads = new ArrayList<>();

        for (int n = 0; n < numberOfThreads
                ; n++) {

            List<Celda> work = this.takeN(workForThreads, celdas);
            System.out.println(celdas.size() + "tamaño antes remove");
            celdas.removeAll(work);
            System.out.println(celdas.size() + "tamaño desp remove");
            Thread t = new CheckerThread(this, work, grilla);
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

    public boolean noTerminoDeAsignarTareas() {
        return this.noTerminoDeAsignarTareas;
    }
}

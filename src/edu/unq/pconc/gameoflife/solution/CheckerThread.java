package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.GameOfLife;

/**
 * Created by leo on 06/11/16.
 */
public class CheckerThread extends Thread {
    GameOfLifeGrid gameOfLifeGrid;
    boolean[][] arrayPartition;
    boolean[][] grilla;

    public CheckerThread(GameOfLifeGrid gameOfLifeGrid, boolean[][] arrayPartition, boolean[][] grilla) {
        this.gameOfLifeGrid = gameOfLifeGrid;
        this.arrayPartition = arrayPartition;
        this.grilla = grilla;
    }

    @Override
    public void run() {
        for (int row = 0; row < this.arrayPartition.length; row++) {
            for (int col = 0; col < this.arrayPartition[0].length; col++) {
                this.vivirOMorir(row,col,grilla);
            }
        }
        System.out.println("here" + this.getId());
        this.gameOfLifeGrid.threadsActivos--;
        synchronized (gameOfLifeGrid){
            if(this.gameOfLifeGrid.threadsActivos == 0){
                gameOfLifeGrid.notify();
            }
        }

    }

    public void vivirOMorir(int col, int row, boolean[][] grilla) {

        int vecinasVivas = 0;

        int rowStart  = Math.max( row - 1, 0   );
        int rowFinish = Math.min( row + 1, this.gameOfLifeGrid.grid[0].length - 1 );
        int colStart  = Math.max( col - 1, 0   );
        int colFinish = Math.min( col + 1, this.gameOfLifeGrid.grid.length - 1 );

        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {
                if(this.gameOfLifeGrid.grid[curCol][curRow] && (col != curCol || row != curRow)){
                    vecinasVivas++;
                }
            }
        }


        if (this.gameOfLifeGrid.grid[col][row]) {
            this.celdaVivaViveOMuere(col, row, vecinasVivas, grilla);
        } else {
            this.celdaMuertaViveOMuere(col, row, vecinasVivas, grilla);
        }

    }

    private synchronized void celdaMuertaViveOMuere(int col, int row, int vecinasVivas, boolean[][] grilla) {
        if (vecinasVivas == 3) {
            grilla[col][row] = true;
        }
    }

    private synchronized void celdaVivaViveOMuere(int col, int row, int vecinasVivas, boolean[][] grilla) {
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

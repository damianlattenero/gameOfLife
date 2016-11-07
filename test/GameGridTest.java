import edu.unq.pconc.gameoflife.solution.Celda;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameGridTest {
    GameOfLifeGrid gameOfLifeGrid;
    @Before
    public void setUp(){
        this.gameOfLifeGrid = new GameOfLifeGrid();
        this.gameOfLifeGrid.resize(20,10);
    }

    @Test
    public void gameStructureOnResize(){
        Assert.assertEquals(gameOfLifeGrid.getDimension().getWidth(), 20, 0);
        Assert.assertEquals(gameOfLifeGrid.getDimension().getHeight(), 10, 0);

        gameOfLifeGrid.grid[17][9] = true;
        gameOfLifeGrid.grid[19][9] = true;
        gameOfLifeGrid.grid[18][9] = true;

        this.gameOfLifeGrid.resize(22,10);

        Assert.assertTrue(gameOfLifeGrid.grid[18][9]);
        Assert.assertTrue(gameOfLifeGrid.grid[19][9]);
        Assert.assertTrue(gameOfLifeGrid.grid[17][9]);


        this.gameOfLifeGrid.resize(18,10);

        Assert.assertTrue(gameOfLifeGrid.grid[17][9]);


    }

    @Test
    public void getSetCell(){
        assertTrue(!this.gameOfLifeGrid.getCell(10,5));
        this.gameOfLifeGrid.setCell(10,5,true);
        assertTrue(this.gameOfLifeGrid.getCell(10,5));
    }

    @Test
    public void clearAll(){
        gameOfLifeGrid.grid[17][9] = true;
        gameOfLifeGrid.grid[19][9] = true;
        gameOfLifeGrid.grid[18][9] = true;

        this.gameOfLifeGrid.clear();

        Assert.assertTrue(!gameOfLifeGrid.grid[18][9]);
        Assert.assertTrue(!gameOfLifeGrid.grid[19][9]);
        Assert.assertTrue(!gameOfLifeGrid.grid[17][9]);




    }

    @Test
    public void setThreads(){
        gameOfLifeGrid.grid[17][9] = true;
        gameOfLifeGrid.grid[19][9] = true;
        gameOfLifeGrid.grid[18][9] = true;

        gameOfLifeGrid.setThreads(5);

        assertEquals(gameOfLifeGrid.threadsActivos, 0);
        assertEquals(gameOfLifeGrid.threads, 5);

    }

    @Test
    public void nextGeneration(){
        gameOfLifeGrid.grid[10][5] = true;
        gameOfLifeGrid.grid[9][5] = true;
        gameOfLifeGrid.grid[8][5] = true;

        gameOfLifeGrid.setThreads(4);

        this.gameOfLifeGrid.next();

        Assert.assertTrue(!gameOfLifeGrid.grid[10][5]);
        Assert.assertTrue(gameOfLifeGrid.grid[9][5]);
        Assert.assertTrue(gameOfLifeGrid.grid[9][6]);
        Assert.assertTrue(gameOfLifeGrid.grid[9][4]);
        Assert.assertTrue(!gameOfLifeGrid.grid[8][5]);

        gameOfLifeGrid.grid[10][5] = true;
        gameOfLifeGrid.grid[11][7] = true;
        gameOfLifeGrid.grid[10][6] = true;
        gameOfLifeGrid.grid[10][4] = true;
        gameOfLifeGrid.grid[8][4] = true;
        gameOfLifeGrid.grid[8][6] = true;

        this.gameOfLifeGrid.next();


        Assert.assertTrue(!gameOfLifeGrid.grid[9][5]);

        assertEquals(gameOfLifeGrid.getGenerations(),2);


    }

    @Test
    public void celda(){
        Assert.assertEquals(new Celda(5,5).toString(), "(5,5)");
    }




}
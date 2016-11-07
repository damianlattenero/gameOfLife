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
    }

}
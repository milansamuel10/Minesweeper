import org.testng.Assert;
import org.testng.annotations.Test;

public class StartOfGameTest
{
    int c = (int) (Math.random() * (21 - 10)) + 10;
    int r = (int) (Math.random() * (21 - 10)) + 10;
    int mines  = (int) (Math.random() * (c*r));
    MS_Game testGame = new MS_Game(r,c,mines);

    @Test
    public void TestNumberOfRows()
    {
        //Test that at the beginning of the game, the game's number of rows has been set properly
        Assert.assertEquals(testGame.getNumRows(),r);
    }
    @Test
    public void TestNumberOfCols()
    {
        //Test that at the beginning of the game, the game's number of cols has been set properly
        Assert.assertEquals(testGame.getNumCols(),c);
    }
    @Test
    public void TestGridSize()
    {
        //Test that at the beginning of the game, the game's grid size has been set properly
        Assert.assertEquals(testGame.getNumRows()*testGame.getNumCols(),r*c);
    }
    @Test
    public void TestNumberOfMines()
    {
        //Test that at the beginning of the game, the game's number of mines has been set properly
        Assert.assertEquals(testGame.getNumMines(),mines);
    }
    @Test
    public void TestState()
    {
        //Test that at the beginning of the game, the game's state is NOT_STARTED
        Assert.assertEquals(testGame.getState(),MS_Game.NOT_STARTED);
    }
    @Test
    public void TestMineCounter()
    {
        //Test that at the beginning of the game, no mines have been marked be user's right-click
        Assert.assertEquals(testGame.getMineCounter(),mines);
    }
    @Test
    public void TestSeconds()
    {
        //Test that at the beginning of the game, the seconds counter is set to 0
        Assert.assertEquals(testGame.getSeconds(),0);
    }
}
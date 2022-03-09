public class MS_Game
{
    public static final int PLAYING = 0, WIN = 1, LOSE = 2, NOT_STARTED = 3;

    MS_Map map;

    int numRows;
    int numCols;
    int numMines;

    int numMarked;

    long startTime;

    int deadSeconds;

    int state;

    public MS_Game(int numRows, int numCols, int numMines)
    {
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = numMines;

        this.numMarked = 0;

        this.state = NOT_STARTED;

        this.startTime = System.nanoTime();
    }

    public MS_Map getMap()
    {
        return map;
    }

    public int getNumRows()
    {
        return numRows;
    }

    public int getNumCols()
    {
        return numCols;
    }

    public int getNumMines()
    {
        return numMines;
    }

    public int getNumMarked()
    {
        return numMarked;
    }

    public int getState()
    {
        return state;
    }

    public int getSeconds()
    {
        if((System.nanoTime() - startTime) >= 1000000000)
        {
            deadSeconds++;
            startTime = System.nanoTime();
        }

        return deadSeconds;
    }

    public int getMineCounter()
    {
        return numMines - numMarked;
    }

    public void setNumMarked(int numMarked)
    {
        this.numMarked = numMarked;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public void reveal(int r, int c)
    {
        if(getState() == NOT_STARTED)
        {
            getMap().getSquare(r,c).setState(MS_Square.SHOWN);
            setState(PLAYING);
        }
        else if(getState() == PLAYING)
        {
            if(((r >= 0 && r < getMap().grid.length) && (c >= 0 && c < getMap().grid[0].length)) && getMap().getSquare(r,c).isMine())
            {
                getMap().getSquare(r,c).setState(MS_Square.SHOWN);
                getMap().getSquare(r,c).setExploded(true);
                getMap().getSquare(r,c).exploded = true;
                setState(LOSE);
            }
            else if(((r >= 0 && r < getMap().grid.length) && (c >= 0 && c < getMap().grid[0].length)) && getMap().getSquare(r,c).getState() == MS_Square.UP)
            {
                getMap().getSquare(r,c).setState(MS_Square.SHOWN);

                if(getMap().getSquare(r,c).getNumber() == 0)
                {
                    reveal(r+1,c);
                    reveal(r-1,c);
                    reveal(r,c+1);
                    reveal(r,c-1);
                    reveal(r+1,c+1);
                    reveal(r-1,c-1);
                    reveal(r+1,c-1);
                    reveal(r-1,c+1);
                }
            }
        }
    }

    public void makeGame(int r, int c)
    {
        map = new MS_Map(numCols,numRows,numMines,r,c);
    }

    public void check()
    {
        if(getState() != NOT_STARTED)
        {
            int totalSquares = numCols*numRows;

            int squaresToBeClicked = totalSquares - numMines;

            int squaresAlreadyClicked = 0;

            if(getNumMarked() <= getNumMines())
            {
                for(int r = 0; r < getMap().grid.length; r++)
                {
                    for(int c = 0; c < getMap().grid[0].length; c++)
                    {
                        if(getMap().getSquare(r,c).getState() == MS_Square.SHOWN)
                            squaresAlreadyClicked++;
                    }
                }
            }

            if(squaresAlreadyClicked == squaresToBeClicked && getState() != LOSE)
                setState(WIN);
        }
    }
}
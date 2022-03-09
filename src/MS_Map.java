public class MS_Map
{
    MS_Square[][] grid;

    int numRows;
    int numCols;
    int numMines;

    public MS_Map(int numCols, int numRows, int numMines, int clickedRow, int clickedCol)
    {
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = numMines;

        grid = new MS_Square[numRows][numCols];
        createMap(clickedRow,clickedCol);
    }

    public MS_Square getSquare(int r, int c)
    {
        return grid[r][c];
    }

    public void createMap(int clickedRow, int clickedCol)
    {
        grid = new MS_Square[numRows][numCols];

       for(int r = 0; r < grid.length; r++)
       {
           for(int c = 0; c < grid[0].length; c++)
               grid[r][c] = new MS_Square();
       }

       if(clickedRow != -1 && clickedCol !=-1)
       {
           int mineCounter = 0;

           do
           {
               int randomRow = (int)(Math.random() * numRows);
               int randomCol = (int)(Math.random() * numCols);

               if(randomRow == clickedRow && randomCol == clickedCol)
                   continue;
               else
               {
                   grid[randomRow][randomCol].setMine(true);
                   mineCounter++;
               }

           }while(mineCounter < numMines);
       }

        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                if(!(grid[r][c].isMine()))
                {
                    if(r + 1 < grid.length && grid[r+1][c].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);

                    if(r - 1 >=0 && grid[r-1][c].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);


                    if(c + 1 < grid[0].length && grid[r][c+1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);

                    if(c - 1 >= 0 && grid[r][c-1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);


                    if((r + 1 < grid.length && c + 1 < grid[0].length) && grid[r+1][c+1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);

                    if((r - 1 >= 0 && c - 1 >= 0) && grid[r-1][c-1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);


                    if((r + 1 < grid.length && c - 1 >= 0) && grid[r+1][c-1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);

                    if((r - 1 >= 0 && c + 1 < grid[0].length) && grid[r-1][c+1].isMine())
                        grid[r][c].setNumber(grid[r][c].getNumber() + 1);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        String s = "";

        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[0].length; c++)
                s += grid[r][c];

            s += "\n";
        }

        return s;
    }
}
public class MS_Square
{
    public static final int SHOWN = 0, UP = 1, FLAG = 2, QUESTION = 3;

    boolean mine;
    boolean exploded;

    int number;

    int state;

    public MS_Square()
    {
        this.mine = false;
        this.exploded = false;
        this.number = 0;
        this.state = UP;
    }

    public boolean isMine()
    {
        return mine;
    }

    public void setMine(boolean mine)
    {
        this.mine = mine;
    }

    public boolean isExploded()
    {
        return exploded;
    }

    public void setExploded(boolean exploded)
    {
        this.exploded = exploded;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        if(isMine())
            return "M";
        else if(getNumber() != 0)
            return "" + getNumber();

        return "-";
    }
}
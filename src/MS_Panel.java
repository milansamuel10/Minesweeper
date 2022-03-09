import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MS_Panel extends JPanel implements MouseListener, MouseMotionListener, Runnable
{
    private int updatesPerSecond = 35;
    private long updateCount = 0;

    boolean leftButtonPressed = false;
    boolean rightButtonPressed = false;
    boolean middleButtonPressed = false;

    BufferedImage dead;
    BufferedImage digitEight;
    BufferedImage digitEmpty;
    BufferedImage digitFive;
    BufferedImage digitFour;
    BufferedImage digitHyphen;
    BufferedImage digitNine;
    BufferedImage digitOne;
    BufferedImage digitSeven;
    BufferedImage digitSix;
    BufferedImage digitThree;
    BufferedImage digitTwo;
    BufferedImage digitZero;
    BufferedImage down;
    BufferedImage eight;
    BufferedImage empty;
    BufferedImage exploded;
    BufferedImage five;
    BufferedImage flag;
    BufferedImage four;
    BufferedImage happy;
    BufferedImage happyDown;
    BufferedImage incorrectFlag;
    BufferedImage mine;
    BufferedImage oh;
    BufferedImage one;
    BufferedImage question;
    BufferedImage seven;
    BufferedImage shades;
    BufferedImage six;
    BufferedImage three;
    BufferedImage two;
    BufferedImage unclicked;

    MS_Game game;
    int numCols, numRows, numMines;

    BufferedImage buffer;

    int mouseCol = -1;
    int mouseRow = -1;

    int mouseX = -1;
    int mouseY = -1;

    public MS_Panel(int numCols, int numRows, int numMines)
    {
        super();

        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = numMines;

        int width = 40 + numCols * 16;
        int height = 60 + numRows * 16;

        setSize(width,height);

        buffer = new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);

        game = new MS_Game(numRows,numCols,numMines);

        try
        {
            dead = ImageIO.read(new File("Mine Sweeper Images/Dead.png"));
            digitEight = ImageIO.read(new File("Mine Sweeper Images/Digit_Eight.png"));
            digitEmpty = ImageIO.read(new File("Mine Sweeper Images/Digit_Empty.png"));
            digitFive = ImageIO.read(new File("Mine Sweeper Images/Digit_Five.png"));
            digitFour = ImageIO.read(new File("Mine Sweeper Images/Digit_Four.png"));
            digitHyphen = ImageIO.read(new File("Mine Sweeper Images/Digit_Hyphen.png"));
            digitNine = ImageIO.read(new File("Mine Sweeper Images/Digit_Nine.png"));
            digitOne = ImageIO.read(new File("Mine Sweeper Images/Digit_One.png"));
            digitSeven = ImageIO.read(new File("Mine Sweeper Images/Digit_Seven.png"));
            digitSix = ImageIO.read(new File("Mine Sweeper Images/Digit_Six.png"));
            digitThree = ImageIO.read(new File("Mine Sweeper Images/Digit_Three.png"));
            digitTwo = ImageIO.read(new File("Mine Sweeper Images/Digit_Two.png"));
            digitZero = ImageIO.read(new File("Mine Sweeper Images/Digit_Zero.png"));
            down = ImageIO.read(new File("Mine Sweeper Images/Down.png"));
            eight = ImageIO.read(new File("Mine Sweeper Images/Eight.png"));
            empty = ImageIO.read(new File("Mine Sweeper Images/Empty.png"));
            exploded = ImageIO.read(new File("Mine Sweeper Images/Exploded.png"));
            five = ImageIO.read(new File("Mine Sweeper Images/Five.png"));
            flag = ImageIO.read(new File("Mine Sweeper Images/Flag.png"));
            four = ImageIO.read(new File("Mine Sweeper Images/Four.png"));
            happy = ImageIO.read(new File("Mine Sweeper Images/Happy.png"));
            happyDown = ImageIO.read(new File("Mine Sweeper Images/Happy_Down.png"));
            incorrectFlag = ImageIO.read(new File("Mine Sweeper Images/IncorrectFlag.png"));
            mine = ImageIO.read(new File("Mine Sweeper Images/Mine.png"));
            oh = ImageIO.read(new File("Mine Sweeper Images/Oh.png"));
            one = ImageIO.read(new File("Mine Sweeper Images/One.png"));
            question = ImageIO.read(new File("Mine Sweeper Images/Question.png"));
            seven = ImageIO.read(new File("Mine Sweeper Images/Seven.png"));
            shades = ImageIO.read(new File("Mine Sweeper Images/Shades.png"));
            six = ImageIO.read(new File("Mine Sweeper Images/Six.png"));
            three = ImageIO.read(new File("Mine Sweeper Images/Three.png"));
            two = ImageIO.read(new File("Mine Sweeper Images/Two.png"));
            unclicked = ImageIO.read(new File("Mine Sweeper Images/Unclicked.png"));

        }catch(Exception e)
            {e.printStackTrace();}

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void run()
    {
        // calculates how many milliseconds to wait for the next update
        int waitToUpdate = (1000 / updatesPerSecond);

        long startTime = System.nanoTime();

        while(true)
        {
            //Is true when you update...
            boolean shouldRepaint = false;

            //Finds the current time...
            long currentTime = System.nanoTime();

            //Finds out how many updates are needed...
            long updatesNeeded = (((currentTime - startTime) / 1000000)) / waitToUpdate;
            for(long x = updateCount; x < updatesNeeded; x++)
            {
                game.check();
                shouldRepaint = true;
                updateCount++;
            }

            if(shouldRepaint)
                repaint();

            //Sleep so other threads have time to run...
            try
            {
                Thread.sleep(5);
            }
            catch(Exception e)
            {
                System.out.println("Error sleeping in run method: " + e.getMessage());
            }
        }
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.RED);
        g.fillRect(2,2,getWidth() - 4,getHeight() - 4);

        g.setColor(Color.GRAY);
        for(int x = 20; x <= (getWidth() - 20 - 16); x += 16)
        {
            for(int y = 40; y <= (getHeight() - 20 - 16); y += 16)
            {
                int col = (x - 20) / 16;
                int row = (y - 40) / 16;

                if(game.getState() == MS_Game.NOT_STARTED)
                    g.drawImage(unclicked,x,y,null);
                else
                    g.drawImage(getSquareImage(game.getMap().getSquare(row,col),row,col),x,y,null);
            }
        }

        if(game.getMineCounter() >= 0)
        {
            g.drawImage(digitZero,20,(int)(8.5),null);

            if(game.getMineCounter() >= 300)
                g.drawImage(digitThree,20 + 13,(int)(8.5),null);
            else if(game.getMineCounter() >= 200)
                g.drawImage(digitTwo,20 + 13,(int)(8.5),null);
            else if(game.getMineCounter() >= 100)
                g.drawImage(digitOne,20 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13,(int)(8.5),null);

            if(game.getMineCounter() % 100 >= 90)
                g.drawImage(digitNine,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 80)
                g.drawImage(digitEight,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 70)
                g.drawImage(digitSeven,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 60)
                g.drawImage(digitSix,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 50)
                g.drawImage(digitFive,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 40)
                g.drawImage(digitFour,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 30)
                g.drawImage(digitThree,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 20)
                g.drawImage(digitTwo,20 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 100 >= 10)
                g.drawImage(digitOne,20 + 13 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13 + 13,(int)(8.5),null);

            if(game.getMineCounter() % 10 == 9)
                g.drawImage(digitNine,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 8)
                g.drawImage(digitEight,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 7)
                g.drawImage(digitSeven,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 6)
                g.drawImage(digitSix,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 5)
                g.drawImage(digitFive,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 4)
                g.drawImage(digitFour,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 3)
                g.drawImage(digitThree,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 2)
                g.drawImage(digitTwo,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(game.getMineCounter() % 10 == 1)
                g.drawImage(digitOne,20 + 13 + 13 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13 + 13 + 13,(int)(8.5),null);
        }
        else if(game.getMineCounter() < 0)
        {
            g.drawImage(digitHyphen,20,(int)(8.5),null);

            if(game.getMineCounter() <= -300)
                g.drawImage(digitThree,20 + 13,(int)(8.5),null);
            else if(game.getMineCounter() <= -200)
                g.drawImage(digitTwo,20 + 13,(int)(8.5),null);
            else if(game.getMineCounter() <= -100)
                g.drawImage(digitOne,20 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13,(int)(8.5),null);

            if(Math.abs(game.getMineCounter()) % 100 >= 90)
                g.drawImage(digitNine,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 80)
                g.drawImage(digitEight,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 70)
                g.drawImage(digitSeven,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 60)
                g.drawImage(digitSix,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 50)
                g.drawImage(digitFive,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 40)
                g.drawImage(digitFour,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 30)
                g.drawImage(digitThree,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 20)
                g.drawImage(digitTwo,20 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 100 >= 10)
                g.drawImage(digitOne,20 + 13 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13 + 13,(int)(8.5),null);

            if(Math.abs(game.getMineCounter()) % 10 == 9)
                g.drawImage(digitNine,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 8)
                g.drawImage(digitEight,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 7)
                g.drawImage(digitSeven,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 6)
                g.drawImage(digitSix,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 5)
                g.drawImage(digitFive,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 4)
                g.drawImage(digitFour,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 3)
                g.drawImage(digitThree,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 2)
                g.drawImage(digitTwo,20 + 13 + 13 + 13,(int)(8.5),null);
            else if(Math.abs(game.getMineCounter()) % 10 == 1)
                g.drawImage(digitOne,20 + 13 + 13 + 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,20 + 13 + 13 + 13,(int)(8.5),null);
        }


        if(game.getSeconds() > 9999)
        {
            g.drawImage(digitHyphen,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            g.drawImage(digitHyphen,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            g.drawImage(digitHyphen,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            g.drawImage(digitHyphen,getWidth() - 20 - 13,(int)(8.5),null);
        }
        else if(game.getSeconds() >= 0)
        {
            if(game.getSeconds() >= 9000)
                g.drawImage(digitNine,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 8000)
                g.drawImage(digitEight,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 7000)
                g.drawImage(digitSeven,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 6000)
                g.drawImage(digitSix,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 5000)
                g.drawImage(digitFive,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 4000)
                g.drawImage(digitFour,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 3000)
                g.drawImage(digitThree,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 2000)
                g.drawImage(digitTwo,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() >= 1000)
                g.drawImage(digitOne,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,getWidth() - 20 - 13 - 13 - 13 - 13,(int)(8.5),null);


            if(game.getSeconds() % 1000 >= 900)
                g.drawImage(digitNine,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 800)
                g.drawImage(digitEight,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 700)
                g.drawImage(digitSeven,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 600)
                g.drawImage(digitSix,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 500)
                g.drawImage(digitFive,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 400)
                g.drawImage(digitFour,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 300)
                g.drawImage(digitThree,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 200)
                g.drawImage(digitTwo,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 1000 >= 100)
                g.drawImage(digitOne,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,getWidth() - 20 - 13 - 13 - 13,(int)(8.5),null);

            if(game.getSeconds() % 100 >= 90)
                g.drawImage(digitNine,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 80)
                g.drawImage(digitEight,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 70)
                g.drawImage(digitSeven,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 60)
                g.drawImage(digitSix,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 50)
                g.drawImage(digitFive,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 40)
                g.drawImage(digitFour,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 30)
                g.drawImage(digitThree,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 20)
                g.drawImage(digitTwo,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 100 >= 10)
                g.drawImage(digitOne,getWidth() - 20 - 13 - 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,getWidth() - 20 - 13 - 13,(int)(8.5),null);

            if(game.getSeconds() % 10 == 9)
                g.drawImage(digitNine,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 8)
                g.drawImage(digitEight,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 7)
                g.drawImage(digitSeven,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 6)
                g.drawImage(digitSix,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 5)
                g.drawImage(digitFive,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 4)
                g.drawImage(digitFour,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 3)
                g.drawImage(digitThree,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 2)
                g.drawImage(digitTwo,getWidth() - 20 - 13,(int)(8.5),null);
            else if(game.getSeconds() % 10 == 1)
                g.drawImage(digitOne,getWidth() - 20 - 13,(int)(8.5),null);
            else
                g.drawImage(digitZero,getWidth() - 20 - 13,(int)(8.5),null);
        }

        if(game.getState() == MS_Game.WIN || game.getState() == MS_Game.LOSE)
        {
            if(game.getState() == MS_Game.WIN)
                g.drawImage(shades,getWidth()/2 - 12,8,null);
            else if(game.getState() == MS_Game.LOSE)
                g.drawImage(dead,getWidth()/2 - 12,8,null);

            for(int x = 20; x <= (getWidth() - 20 - 16); x += 16)
            {
                for(int y = 40; y <= (getHeight() - 20 - 16); y += 16)
                {
                    int col = (x - 20) / 16;
                    int row = (y - 40) / 16;

                    if(game.getMap().getSquare(row,col).getState() == MS_Square.UP && game.getState() == MS_Game.WIN)
                        game.getMap().getSquare(row,col).setState(MS_Square.FLAG);
                    else if(game.getMap().getSquare(row,col).getState() == MS_Square.UP && game.getState() == MS_Game.LOSE)
                        game.getMap().getSquare(row,col).setState(MS_Square.SHOWN);

                    g.drawImage(getSquareImage(game.getMap().getSquare(row,col),row,col),x,y,null);
                }
            }
        }
        else
            g.drawImage(happy,getWidth()/2 - 12,8,null);

        if((mouseX > getWidth()/2 - 12 && mouseX < getWidth()/2 + 12) && (mouseY > 8 && mouseY < 32) && (leftButtonPressed))
            g.drawImage(happyDown,getWidth()/2 - 12,8,null);
        else if(leftButtonPressed && game.getState() != MS_Game.LOSE && game.getState() != MS_Game.WIN)
            g.drawImage(oh,getWidth()/2 - 12,8,null);

        if(leftButtonPressed && rightButtonPressed && isInGrid(mouseX,mouseY) && game.getState() == MS_Game.PLAYING)
        {
            int c = mouseCol = (mouseX - 20) / 16;
            int r = mouseRow = (mouseY - 40) / 16;

            if(game.getMap().grid[r][c].getState() == MS_Square.UP)
                g.drawImage(down,16*c+20,16*r+40,null);

            if(r + 1 < game.getMap().grid.length && game.getMap().grid[r+1][c].getState() == MS_Square.UP)
                g.drawImage(down,(16*c)+20,(16*(r+1))+40,null);

            if(r - 1 >=0 && game.getMap().grid[r-1][c].getState() == MS_Square.UP)
                g.drawImage(down,(16*c)+20,(16*(r-1))+40,null);


            if(c + 1 < game.getMap().grid[0].length && game.getMap().grid[r][c+1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c+1))+20,(16*r)+40,null);

            if(c - 1 >= 0 && game.getMap().grid[r][c-1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c-1))+20,(16*r)+40,null);


            if((r + 1 < game.getMap().grid.length && c + 1 < game.getMap().grid[0].length) && game.getMap().grid[r+1][c+1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c+1))+20,(16*(r+1))+40,null);

            if((r - 1 >= 0 && c - 1 >= 0) && game.getMap().grid[r-1][c-1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c-1))+20,(16*(r-1))+40,null);


            if((r + 1 < game.getMap().grid.length && c - 1 >= 0) && game.getMap().grid[r+1][c-1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c-1))+20,(16*(r+1))+40,null);

            if((r - 1 >= 0 && c + 1 < game.getMap().grid[0].length) && game.getMap().grid[r-1][c+1].getState() == MS_Square.UP)
                g.drawImage(down,(16*(c+1))+20,(16*(r-1))+40,null);
        }
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();

        Thread t = new Thread(this);
        t.start();
    }

    public void mousePressed(MouseEvent e)
    {
        if(SwingUtilities.isLeftMouseButton(e))
            leftButtonPressed = true;
        if(SwingUtilities.isRightMouseButton(e) && game.getState() != MS_Game.NOT_STARTED)
            rightButtonPressed = true;
        if(SwingUtilities.isMiddleMouseButton(e))
            middleButtonPressed = true;

        mouseX = e.getX();
        mouseY = e.getY();

        mouseRow = -1;
        mouseCol = -1;

        if(isInGrid(mouseX,mouseY))
        {
            mouseCol = (int)((mouseX - 20.) / 16.);
            mouseRow = (int)((mouseY - 40.) / 16.);
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();

        mouseRow = -1;
        mouseCol = -1;

        if(SwingUtilities.isLeftMouseButton(e))
        {
            if((mouseX > getWidth()/2 - 12 && mouseX < getWidth()/2 + 12) && (mouseY > 8 && mouseY < 32) && leftButtonPressed)
            {
                if(game.getState() == MS_Game.WIN || game.getState() == MS_Game.LOSE)
                    game = new MS_Game(numRows,numCols,numMines);
            }

            leftButtonPressed = false;

            if(isInGrid(mouseX,mouseY))
            {
                mouseCol = (int)((mouseX - 20.) / 16.);
                mouseRow = (int)((mouseY - 40.) / 16.);

                if(game.getState() == MS_Game.NOT_STARTED)
                {
                    game.makeGame(mouseRow,mouseCol);

                    if(game.getMap().getSquare(mouseRow,mouseCol).getState() == MS_Square.UP)
                        game.reveal(mouseRow,mouseCol);
                }
                else if(game.getState() == MS_Game.PLAYING && game.getMap().getSquare(mouseRow,mouseCol).getState() == MS_Square.UP)
                {
                    if(game.getMap().getSquare(mouseRow,mouseCol).isMine())
                    {
                        game.getMap().getSquare(mouseRow,mouseCol).setState(MS_Square.SHOWN);
                        game.getMap().getSquare(mouseRow,mouseCol).setExploded(true);
                        game.setState(MS_Game.LOSE);
                    }
                    else
                    {
                        game.reveal(mouseRow,mouseCol);
                        game.check();
                    }
                }
            }
        }
        if(SwingUtilities.isRightMouseButton(e) && game.getState() == MS_Game.PLAYING)
        {
            rightButtonPressed = false;

            if(isInGrid(mouseX,mouseY))
            {
                mouseCol = (int)((mouseX - 20.) / 16.);
                mouseRow = (int)((mouseY - 40.) / 16.);

                if(game.getMap().getSquare(mouseRow,mouseCol).getState() == MS_Square.UP)
                {
                    game.getMap().getSquare(mouseRow,mouseCol).setState(MS_Square.FLAG);

                    game.setNumMarked(game.getNumMarked() + 1);
                }
                else if(game.getMap().getSquare(mouseRow,mouseCol).getState() == MS_Square.FLAG)
                {
                    game.getMap().getSquare(mouseRow,mouseCol).setState(MS_Square.QUESTION);

                    game.setNumMarked(game.getNumMarked() - 1);
                }
                else if(game.getMap().getSquare(mouseRow,mouseCol).getState() == MS_Square.QUESTION)
                    game.getMap().getSquare(mouseRow,mouseCol).setState(MS_Square.UP);
            }
        }
        if(SwingUtilities.isMiddleMouseButton(e))
            middleButtonPressed = false;
    }

    //Method below not used!
    public void mouseClicked(MouseEvent e)
    {
    }

    //Method below not used!
    public void mouseEntered(MouseEvent e)
    {
    }

    //Method below not used!
    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();

        mouseCol = -1;
        mouseRow = -1;

        if(isInGrid(mouseX,mouseY))
        {
            mouseCol = (int)((mouseX - 20.) / 16.);
            mouseRow = (int)((mouseY - 40.) / 16.);
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        if(SwingUtilities.isLeftMouseButton(e))
            leftButtonPressed = true;
        if(SwingUtilities.isRightMouseButton(e) && game.getState() != MS_Game.NOT_STARTED)
            rightButtonPressed = true;
        if(SwingUtilities.isMiddleMouseButton(e))
            middleButtonPressed = true;

        mouseX = e.getX();
        mouseY = e.getY();

        mouseCol = -1;
        mouseRow = -1;

        if(isInGrid(mouseX,mouseY))
        {
            mouseCol = (int)((mouseX - 20.) / 16.);
            mouseRow = (int)((mouseY - 40.) / 16.);

        }
    }

    public boolean isInGrid(int x, int y)
    {
        if((x > 20 && x < getWidth() - 20) && (y > 40 && y < getHeight() - 20))
            return true;
        else
            return false;
    }

    public BufferedImage getSquareImage(MS_Square s, int row, int col)
    {
        if(game.getState() == MS_Game.LOSE)
        {
            if(s.getState() == MS_Square.FLAG && !(s.isMine()))
                return incorrectFlag;

            if(s.getState() == MS_Square.FLAG)
                return flag;
            if(s.getState() == MS_Square.QUESTION)
                return question;

            if(s.isExploded())
                return exploded;

            if(s.isMine())
                return mine;

            if(s.isExploded())
                return exploded;

            if(s.getNumber() == 1)
                return one;
            if(s.getNumber() == 2)
                return two;
            if(s.getNumber() == 3)
                return three;
            if(s.getNumber() == 4)
                return four;
            if(s.getNumber() == 5)
                return five;
            if(s.getNumber() == 6)
                return six;
            if(s.getNumber() == 7)
                return seven;
            if(s.getNumber() == 8)
                return eight;

            return down;
        }
        else
        {
            if(s.getState() == MS_Square.FLAG)
                return flag;
            if(s.getState() == MS_Square.QUESTION)
                return question;

            if(s.getState() == MS_Square.SHOWN)
            {
                if(s.isExploded())
                    return exploded;

                if(s.isMine())
                    return mine;

                if(s.isExploded())
                    return exploded;

                if(s.getNumber() == 1)
                    return one;
                if(s.getNumber() == 2)
                    return two;
                if(s.getNumber() == 3)
                    return three;
                if(s.getNumber() == 4)
                    return four;
                if(s.getNumber() == 5)
                    return five;
                if(s.getNumber() == 6)
                    return six;
                if(s.getNumber() == 7)
                    return seven;
                if(s.getNumber() == 8)
                    return eight;

                return down;
            }

            if(leftButtonPressed && !rightButtonPressed && mouseRow == row && mouseCol == col)
                return down;

            return unclicked;
        }
    }
}
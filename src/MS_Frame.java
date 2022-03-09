import java.awt.*;
import javax.swing.*;

public class MS_Frame extends JFrame
{
    public MS_Frame(int numCols, int numRows, int numMines)
    {
        super("Mine Sweeper by Milan Saju Samuel");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        pack();

        MS_Panel p = new MS_Panel(numCols,numRows,numMines);

        Insets frameInsets = getInsets();

        int frameWidth = 40 + numCols * 16
                + (frameInsets.left + frameInsets.right);
        int frameHeight = 60 + numRows * 16
                + (frameInsets.top + frameInsets.bottom);

        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setLayout(null);

        add(p);

        pack();

        setVisible(true);

        setLocationRelativeTo(null);
    }
}
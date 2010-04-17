
/**
 *
 * @author rmb5299
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.applet.AudioClip;



public class editor extends JApplet implements MouseListener
{

    private int levelheight = 100;      //variable height of map
    private int levelwidth = 100;       //variable width of map
    private int numObjects = 1;         //number of objects loaded
    private int numLevelsprites = 1;    //number of level sprites
    private int levelarray[][];         //integer array showing where level sprite get displayed
    private Image levelsprites[];       //array of level sprites
    private Image levelobjects[];       //array of level object
    //private levelSpace levelblock;      //ignore this
    private Color C;
    private Image test;


    //0 is nothing positive numbers are level geometry negatives are object indexes
    int currentSpriteIndex = 1;


    @Override
    public void init()
    {
        //panels for level sprites and object sprites
        JPanel levelGeom = new JPanel();
        JPanel Objects = new JPanel();

        //image variables
        levelarray = new int[levelheight][levelwidth];
        levelsprites = new Image[2];
        levelobjects = new Image[1];

        levelsprites[1] = getImage(getDocumentBase(), "brick.png");
        //ignore this
        test = getImage(getDocumentBase(),"brick.png");

        addMouseListener(this);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(C);
        drawGrid(g);
        drawLevelSprites(g);
        //g.drawImage(test, 0, 0, test.getWidth(this), test.getHeight(this) , this);//draw image
    }

    public void drawLevelSprites(Graphics g)
    {
        for(int x =0; x< levelwidth; x++)
        {
            for(int y = 0; y < levelheight; y++)
            {
                if(levelarray[x][y] == 1)
                    g.drawImage(levelsprites[1], x*32, y*32, levelsprites[1].getWidth(this), levelsprites[1].getHeight(this) , this);//draw image
            }
        }
    }
    public void drawGrid(Graphics g)
    {
        for(int x =0; x< levelwidth; x++)
        {
            for(int y = 0; y < levelheight; y++)
            {
                g.drawLine(0, y*32, levelwidth*32, y*32);
                g.drawLine(x*32, 0, x*32, levelheight*32);
            }
        }
    }

    public void mousePressed(MouseEvent e)
    {}
    public void mouseClicked(MouseEvent e)
    {
            int x = e.getX();
            int y = e.getY();

            int arrayx = x /32;
            int arrayy = y /32;

            levelarray[arrayx][arrayy] = currentSpriteIndex;
            C = Color.BLUE;
            repaint();  //repaint
    }
    public void mouseReleased(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
}

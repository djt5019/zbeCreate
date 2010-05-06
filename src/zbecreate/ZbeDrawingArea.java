/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import zbecreate.tile.ZbeTile;
import static zbecreate.ZbeCreateView.*;

/**
 *
 * @author dan
 */
class ZbeDrawingArea extends JPanel implements MouseListener, MouseMotionListener{
    /**
     * Will contain the functions that are relevant to the drawing area. The
     * purpose of this class is to create a panel upon which the user can draw.
     * The DrawingArea class will first initalize the mouse action listeners then
     * await for the user to interact with the panel.
     */

    private int size = ZbeTile.tileSize;
    private boolean mouseDragging = false;
    private ArrayList<ZbeTile> tileList;
    private ArrayList<ZbeTile> wallList;
    private BufferedImage background;
    private ZbeTile[][] tileGraph;
    private ZbeFilter filter;

    public ZbeDrawingArea(){
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        tileList = new ArrayList();
        wallList = new ArrayList();
        tileGraph = new ZbeTile[2000][2000];
        initBackground();
        initXMLButton();
    }

    private void initBackground(){
        String imageLocation = "";

        try{
            imageLocation = new File(".").getCanonicalPath();
            imageLocation += "/src/zbecreate/resources/background.png";
            background = ImageIO.read(new File(imageLocation));
        }catch(Exception ex){ ex.printStackTrace(); System.exit(1); }

    }



    private void initXMLButton(){
        filter = new ZbeFilter("XML Document");
        filter.add("xml");

        xmlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setFileFilter(filter);

                int value = chooser.showSaveDialog(null);

                if( value == javax.swing.JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getAbsolutePath();
                    try{
                        exportXML(name, tileList, wallList);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Exports the tile information in the tileList and wallList to a file and location
     * of the users choice. This will iterate through the ArrayList of Tiles getting
     * the tileID, paletteID, and horizontal/vertical flip values.
     * Upon each iteration the tile information will be written to the file
     * "filename.xml".
     * @param filename The name of the file the user wishes to write to.
     * @throws IOException
     */
    private void exportXML(String filename, ArrayList<ZbeTile> tiles, ArrayList<ZbeTile> walls)
            throws IOException{

        if(walls == null && tiles == null)
            return;

        System.out.println("EXPORTING");
        BufferedWriter out = new BufferedWriter( new FileWriter(filename + ".xml") );

        if( walls != null && !walls.isEmpty()){
            out.write("<zbe>\n<levels>\n<objects>\n");
            for(ZbeTile s : walls){
                int x = s.getXcoord();
                int y = s.getYcoord();
                int id= s.getTileID();

                String str = "\t<object id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" weight=\"255\" hgrav=\"0\" vgrav=\"0\"/> </object>\n";

                out.write(str);
            }
        }

        if( tiles != null && !tiles.isEmpty()){
            out.write("\n</zbe>\n</levels>\n</objects>\n\n");
            out.write("<Zbebackground>\n\t<background>\n\t\t<row>\n");
            for( ZbeTile s : tiles){
                String id = "id = \"" + s.getTileID() + "\"\n";
                String pa = "palette = \"" + s.getPaletteID() + "\"\n";
                String hF = "hflip = \"" + s.getHFlipValue() + "\"\n";
                String vF = "hflip = \"" + s.getVFlipValue() + "\"\n";

                String statement = "\t\t\t<file   " + id +
                                       "\t\t\t\t" + pa +
                                       "\t\t\t\t" + hF +
                                       "\t\t\t\t" + vF +
                                       "\t\t\t/>\n";

                out.write(statement);
            }
            out.write("\n\t\t</row>\n\t</background>\n\n</Zbebackground>");
        }

        out.close();
    }

    /**
     * Attempts to either place or remove tile at the location passed.
     * @param e The mouse event passed to the function; used to get X and Y coordinates.
     */
    public void mouseClicked(MouseEvent e)  {
        int x = e.getX();
        int y = e.getY();

        if(mouse.equals(MouseSelection.PLACE))
            placeTile(x,y);
        else if(mouse.equals(MouseSelection.DELETE))
            eraseTile(x,y);
        repaint();
    }

    /**
     * Will place a new tile at the coordinates (x,y) on the drawing panel,
     * if the tile already exists in that location its color will be updated.
     * This function will calculate the size of the tile which is
     * by default 8x8 pixels.  After calculating the correct size and
     * creating a rectangle that will fill the entire tile it will add
     * it into the tileList.
     * @param x The X coordinate where the user clicked
     * @param y The Y coordinate where the user clicked
     */
    public void placeTile(int x, int y){

        if( !mouse.equals(MouseSelection.PLACE))
            return;

        int pid   = currentColor.getRGB();
        int hFlip = hFlipCbox.getSelectedIndex();
        int vFlip = vFlipCbox.getSelectedIndex();
        int modX  = ((x - (x % size)));
        int modY  = ((y - (y % size)));
        int tid   = modX + modY*levelWidth;

        System.out.println(modX + "\n" + modY);
        boolean wall = (tileCbox.getSelectedIndex() == 1) ? true:false;
        System.out.println(wall);
        if(modX < 0 || modY < 0)
            return;

        Rectangle r = new Rectangle(modX,modY,size,size);

        ZbeTile t = new ZbeTile(currentColor, tid, pid, hFlip, vFlip, modX, modY,wall, r);

        if( tileGraph[modX][modY] == null){
            tileGraph[modX][modY] = t;

            if( wall == true)
                wallList.add(t);
            else
                tileList.add(t);
        }
        else{
            //If the tile has already been placed update the color
            ZbeTile temp = tileGraph[modX][modY];

            if(temp.isWallTile() == wall)
                temp.setColor(currentColor);
        }
    }

    /**
     * Erases the tile located at position (x,y) on the drawing panel and
     * removes it from the ArrayList.
     * @param x  The X coordinate on the drawing panel
     * @param y  The Y coordinate on the drawing panel
     */
    public void eraseTile(int x, int y){
        int modX  = ((x - (x % size)));
        int modY  = ((y - (y % size)));
        int tid   = modX + modY*levelWidth;

        if( modX < 0 || modY < 0)
            return;

        if(tileGraph[modX][modY] == null)
            return;

        tileGraph[modX][modY] = null;

        for(ZbeTile temp : tileList){
            if(temp.getTileID() == tid){
                if(temp.isWallTile() == true)
                    wallList.remove(temp);
                else
                    tileList.remove(temp);
                break;
            }
        }
    }

    /**
     * Will initate the dragging sequence, if the user is already dragging
     * then ignore any additional events until the user stops dragging.
     * @param e The mouse event passed to the function
     */
    public void mousePressed(MouseEvent e)  {
        if(mouseDragging == true)
            return;

        mouseDragging = true;
    }

    /**
     * During the dragging sequence the function will attempt to either place
     * or erase tiles over the area of the drawing board where the user drags.
     * @param e The mouse event passed to the function
     */
    public void mouseDragged(MouseEvent e) {
        if( !mouseDragging)
            return;

        if(mouse.equals(MouseSelection.PLACE))
            placeTile(e.getX(), e.getY());
        else if(mouse.equals(MouseSelection.DELETE))
            eraseTile(e.getX(), e.getY());

        repaint();
    }

    /**
     * Sets the mouseDragging flag to false
     * @param e The mouse event passed to the function.
     */
    public void mouseReleased(MouseEvent e) { mouseDragging = false; }
    /**Unused*/
    public void mouseMoved(MouseEvent e)    {}
    /**Unused*/
    public void mouseEntered(MouseEvent e)  {}
    /**Unused*/
    public void mouseExited(MouseEvent e)   {}

    /**
     * Draws the background image of the grey tiles upon the board then iterates
     * through the list of background and wall tiles and draws each.
     * @param g The graphics data from the drawing panel.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graph = (Graphics2D)g;

        System.out.println("TILES = "  + (tileList.size()+wallList.size()));

        graph.drawImage(background, null, this);

        for(ZbeTile temp : tileList){
            graph.setColor(temp.getTileColor());
            graph.fill(temp.getRect());
        }

        for(ZbeTile temp : wallList){
            graph.setColor(temp.getTileColor());
            graph.fill(temp.getRect());
        }
    }
    
}
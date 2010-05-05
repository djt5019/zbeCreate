/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.tile;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author dan
 */
public class ZbeTile{
    /**@author Dan Tracy
     * The tile class will be used to represent a single 8x8 tile on
     * the drawing panel.  Each tile will keep track of its own tileID,
     * paletteID, Color, size, coordinates, as well as the Horizontal and
     * Vertical flip values.
     */
    public static final int tileSize = 8;
    private int hFlipValue;
    private int vFlipValue;
    private int tileID;
    private int paletteID;
    private Color myColor;
    private int xCoord;
    private int yCoord;
    private Rectangle rect;

    public ZbeTile(Color c, int tid, int pid, int hFlip, int vFlip, int x, int y, Rectangle r){
        this.tileID     = tid;
        this.paletteID  = pid;
        this.hFlipValue = hFlip;
        this.vFlipValue = vFlip;
        this.myColor    = c;
        this.xCoord     = x;
        this.yCoord     = y;
        this.rect       = r;
    }

    public Color getTileColor() { return this.myColor; }
    public Rectangle getRect(){ return this.rect; }
    public int getHFlipValue(){ return this.hFlipValue; }
    public int getVFlipValue(){ return this.vFlipValue; }
    public int getPaletteID() { return this.paletteID; }
    public int getTileID(){ return this.tileID; }
    public int getXcoord(){ return this.xCoord; }
    public int getYcoord(){ return this.yCoord;}

    public void setColor(Color newColor){ this.myColor = newColor; }
}

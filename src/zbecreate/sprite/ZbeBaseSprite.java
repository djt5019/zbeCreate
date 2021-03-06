package zbecreate.sprite;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import zbecreate.ZbePaint;

/**
 * @author Dan
 */

//! This class is meant to be derived for the several different
//! types of sprites in the zoidbergengine. It is most anaglous to the
//! 'object' class within the ZBE program.

public class ZbeBaseSprite extends JPanel{

    protected Point2D.Double position;
    protected Image myImage;
    protected int tileWidth;
    protected int tileHeight;
    protected String location;
    
    protected int tileID;
    protected int paletteID;
    protected int hFlipValue;
    protected int vFlipValue;
    protected int numSquares;
    protected int spriteNumber;
    
    public ZbeBaseSprite(int num, int tileId, int paletteId, int hFlip, int vFlip, String loc){
        spriteNumber = num;
        tileID       = tileId;
        paletteID    = paletteId;
        hFlipValue   = hFlip;
        vFlipValue   = vFlip;
        location     = loc;
        tileWidth    = tileHeight = 50;
        position     = new Point2D.Double();
        numSquares   = (int)Math.ceil( (float)tileHeight / ZbePaint.getTileSize() )+1;
        
        loadImage(location);
    }

    public void loadImage(String imageLocation){
        if( imageLocation == null)
            return;
        myImage = java.awt.Toolkit.getDefaultToolkit().getImage(imageLocation);
    }

    public Image getImage(){ return this.myImage; }
    public void deleteImage(){ this.myImage = null; }

    public void setPosition(int x, int y){ this.position.setLocation(x,y); }
    public int getXPosition(){ return (int)position.x; }
    public int getYPosition(){ return (int)position.y; }
    public int getSpriteNum(){ return this.spriteNumber; }
    public int getTileWidth(){ return this.tileWidth; }
    public int getTileHeight(){ return this.tileHeight; }
    public int getHFlipValue(){ return this.hFlipValue; }
    public int getVFlipValue(){ return this.vFlipValue; }
    public int getPalletteNum(){ return this.paletteID; }
    public int getTileID() { return this.tileID; }
    public int getNumSquares(){ return this.numSquares; }

    public void setTileWidth(int width){ this.tileWidth = width; }
    public void setTileHeight(int height){ this.tileHeight = height; }
    public void setHFlipValue(int hFlip){ this.hFlipValue = hFlip; }
    public void setVFlipValue(int vFlip){ this.vFlipValue = vFlip; }
    public void setPalletteNum(int pallete){ this.paletteID = pallete; }
    public void setTileID(int tileNum) { this.tileID = tileNum; }

    public static void exportXML(ArrayList<ZbeBaseSprite> list) throws IOException{
        if( list == null || list.isEmpty() )
            return;
        
        BufferedWriter out = new BufferedWriter( new FileWriter("out.xml") );
        
        out.write("<Zbebackground>\n");
        out.write("\t<background>\n");
        out.write("\t\t<row>\n");
        for( ZbeBaseSprite s : list){
            String id = "id = \"" + s.getTileID() + "\"\n";
            String pa = "palette = \"" + s.getPalletteNum() + "\"\n";
            String hF = "hflip = \"" + s.getHFlipValue() + "\"\n";
            String vF = "hflip = \"" + s.getVFlipValue() + "\"\n";
            
            String statement = "\t\t\t<file   " + id +
                                   "\t\t\t\t" + pa +
                                   "\t\t\t\t" + hF +
                                   "\t\t\t\t" + vF +
                                   "\t\t\t/>\n";
            
            out.write(statement);
        }
        out.write("\n\t\t</row>\n");
        out.write("\t</background>\n");
        out.write("\n</Zbebackground>");
        
        out.close();
    }

}

package zbecreate.sprite;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * @author Dan
 */

//! This class is meant to be derived for the several different
//! types of sprites in the zoidbergengine. It is most anaglous to the
//! 'object' class within the ZBE program.

public class ZbeBaseSprite extends JPanel{

    protected Point2D.Double position;
    protected java.awt.Image myImage;
    protected int tileWidth;
    protected int tileHeight;
    protected String location;
    
    protected int tileNumber;
    protected int palleteNumber;
    protected int hFlipValue;
    protected int vFlipValue;
    
    public ZbeBaseSprite(){
        tileWidth = tileHeight = 50;
        position = new Point2D.Double();
    }

    public void loadImage(String imageLocation){
        myImage = java.awt.Toolkit.getDefaultToolkit().getImage(imageLocation);
    }

    public java.awt.Image getImage(){ return this.myImage; }
    public void deleteImage(){ this.myImage = null; }

    public void setPosition(int x, int y){ this.position.setLocation(x,y); }
    public int getXPosition(){ return (int)position.x; }
    public int getYPosition(){ return (int)position.y; }

    public int getTileWidth(){ return this.tileWidth; }
    public int getTileHeight(){ return this.tileHeight; }
    public int getHFlipValue(){ return this.hFlipValue; }
    public int getVFlipValue(){ return this.vFlipValue; }
    public int getPalletteNum(){ return this.palleteNumber; }
    public int getTileID() { return this.tileNumber; }

    public void setTileWidth(int width){ this.tileWidth = width; }
    public void setTileHeight(int height){ this.tileHeight = height; }
    public void setHFlipValue(int hFlip){ this.hFlipValue = hFlip; }
    public void setVFlipValue(int vFlip){ this.vFlipValue = vFlip; }
    public void setPalletteNum(int pallete){ this.palleteNumber = pallete; }
    public void setTileID(int tileNum) { this.tileNumber = tileNum; }

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
        out.write("\n     </row>\n");
        out.write("  </background>\n");
        out.write("\n</Zbebackground>");
        
        out.close();
    }

}

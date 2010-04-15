package zbecreate.sprite;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

/**
 * @author Dan
 */

//! This class is meant to be derived for the several different
//! types of sprites in the zoidbergengine. It is most anaglous to the
//! 'object' class within the program.

public abstract class ZbeBaseSprite extends JPanel{

    protected Point2D.Double position;
    protected java.awt.Image myImage;
    protected int tileWidth;
    protected int tileHeight;
    protected String location;

    private DropTarget dropTarget;
    private DropTargetListener dtListener;
    private int acceptableActions = DnDConstants.ACTION_MOVE;

    public ZbeBaseSprite(){
        tileWidth = tileHeight = 50;
        position = new Point2D.Double();

        this.dtListener = new DTListener();

        this.dropTarget = new DropTarget(this,
                          this.acceptableActions,
                          this.dtListener,
                          true);
        
    }

    public void loadImage(){
        
        try{
            location = new java.io.File(".").getCanonicalPath();
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }

        location += "/src/zbecreate/resources/about.png";

        myImage = java.awt.Toolkit.getDefaultToolkit().getImage(location);
    }

    public java.awt.Image getImage(){
        return this.myImage;
    }

    public void deleteImage(){
        this.myImage = null;
    }    

    public void setPosition(int x, int y){
        this.position.setLocation(x,y);
    }

    public int getXPosition(){
        return (int)position.x;
    }

    public int getYPosition(){
        return (int)position.y;
    }

    public int getTileWidth(){ return this.tileWidth; }
    public int getTileHeight(){ return this.tileHeight; }

    public abstract void exportXML();
    
}

package zbecreate.sprite;

/**
 * @author Dan
 */

public class ZbePlatformSprite extends ZbeBaseSprite{

    public ZbePlatformSprite(String name){
        loadImage(name);
    }
    
    @Override
    public void exportXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

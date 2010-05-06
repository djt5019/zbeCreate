/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

import java.awt.Image;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeBaseSpriteTest {

    public ZbeBaseSpriteTest() {
    }

    /**
     * Test of loadImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testLoadImage() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        String imageLocation = "";
        instance.loadImage(imageLocation);
    }

    /**
     * Test of getImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetImage() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");

        instance.loadImage("./src/zbecreate/resources/about.png");
        Image expResult = instance.getImage();

        assertFalse(expResult == null);
    }

    /**
     * Test of deleteImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testDeleteImage() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");

        instance.deleteImage();

        assertTrue(instance.getImage() == null);
    }

    /**
     * Test of setPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetPosition() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        
        instance.setPosition(200, 200);

        assertTrue( instance.getXPosition()==200 && instance.getYPosition()==200);
    }

    /**
     * Test of getXPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetXPosition() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        int expResult = 120;
        instance.setPosition(120, 0);

        assertEquals(instance.getXPosition(), expResult);
    }

    /**
     * Test of getYPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetYPosition() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");

        int expResult = 200;
        instance.setPosition(200,200);

        assertEquals(expResult, instance.getYPosition());
    }

    /**
     * Test of getSpriteNum method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetSpriteNum() {
        ZbeBaseSprite instance = new ZbeBaseSprite(2310,0,0,0,0,"");
        
        int expResult = 2310;
        int result = instance.getSpriteNum();

        assertEquals(expResult, result);
    }

    /**
     * Test of getTileWidth method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileWidth() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        
        int expResult = 50;
        
        assertEquals(expResult, instance.getTileWidth());
    }

    /**
     * Test of getTileHeight method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileHeight() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        
        int expResult = 50;
        int result = instance.getTileHeight();

        assertEquals(expResult, result);
    }

    /**
     * Test of getHFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetHFlipValue() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,1,0,"");
        
        int expResult = 1;
        int result = instance.getHFlipValue();

        assertEquals(expResult, result);
    }

    /**
     * Test of getVFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetVFlipValue() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,1,"");
        
        int expResult = 1;
        int result = instance.getVFlipValue();

        assertEquals(expResult, result);
    }

    /**
     * Test of getPalletteNum method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetPalletteNum() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,2310,0,0,"");
        
        int expResult = 2310;
        int result = instance.getPalletteNum();

        assertEquals(expResult, result);
    }

    /**
     * Test of getTileID method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileID() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,60,0,0,0,"");
        
        int expResult = 60;
        int result = instance.getTileID();

        assertEquals(expResult, result);
    }

    /**
     * Test of getNumSquares method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetNumSquares() {
        ZbeBaseSprite instance = new ZbeBaseSprite(0,0,0,0,0,"");
        
        int expResult = (int)Math.ceil( (float)instance.getTileHeight() / 8 )+1;
        int result = instance.getNumSquares();
        
        assertEquals(expResult, result);
    }

}
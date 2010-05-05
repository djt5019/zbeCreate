/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

import java.awt.Image;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeBaseSpriteTest {

    public ZbeBaseSpriteTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testLoadImage() {
        System.out.println("loadImage");
        String imageLocation = "";
        ZbeBaseSprite instance = null;
        instance.loadImage(imageLocation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        ZbeBaseSprite instance = null;
        Image expResult = null;
        Image result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteImage method, of class ZbeBaseSprite.
     */
    @Test
    public void testDeleteImage() {
        System.out.println("deleteImage");
        ZbeBaseSprite instance = null;
        instance.deleteImage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        int x = 0;
        int y = 0;
        ZbeBaseSprite instance = null;
        instance.setPosition(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getXPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetXPosition() {
        System.out.println("getXPosition");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getXPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getYPosition method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetYPosition() {
        System.out.println("getYPosition");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getYPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpriteNum method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetSpriteNum() {
        System.out.println("getSpriteNum");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getSpriteNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileWidth method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileWidth() {
        System.out.println("getTileWidth");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getTileWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileHeight method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileHeight() {
        System.out.println("getTileHeight");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getTileHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetHFlipValue() {
        System.out.println("getHFlipValue");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getHFlipValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetVFlipValue() {
        System.out.println("getVFlipValue");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getVFlipValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPalletteNum method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetPalletteNum() {
        System.out.println("getPalletteNum");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getPalletteNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileID method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetTileID() {
        System.out.println("getTileID");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getTileID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumSquares method, of class ZbeBaseSprite.
     */
    @Test
    public void testGetNumSquares() {
        System.out.println("getNumSquares");
        ZbeBaseSprite instance = null;
        int expResult = 0;
        int result = instance.getNumSquares();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileWidth method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetTileWidth() {
        System.out.println("setTileWidth");
        int width = 0;
        ZbeBaseSprite instance = null;
        instance.setTileWidth(width);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileHeight method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetTileHeight() {
        System.out.println("setTileHeight");
        int height = 0;
        ZbeBaseSprite instance = null;
        instance.setTileHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetHFlipValue() {
        System.out.println("setHFlipValue");
        int hFlip = 0;
        ZbeBaseSprite instance = null;
        instance.setHFlipValue(hFlip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVFlipValue method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetVFlipValue() {
        System.out.println("setVFlipValue");
        int vFlip = 0;
        ZbeBaseSprite instance = null;
        instance.setVFlipValue(vFlip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPalletteNum method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetPalletteNum() {
        System.out.println("setPalletteNum");
        int pallete = 0;
        ZbeBaseSprite instance = null;
        instance.setPalletteNum(pallete);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileID method, of class ZbeBaseSprite.
     */
    @Test
    public void testSetTileID() {
        System.out.println("setTileID");
        int tileNum = 0;
        ZbeBaseSprite instance = null;
        instance.setTileID(tileNum);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportXML method, of class ZbeBaseSprite.
     */
    @Test
    public void testExportXML() throws Exception {
        System.out.println("exportXML");
        ArrayList<ZbeBaseSprite> list = null;
        ZbeBaseSprite.exportXML(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
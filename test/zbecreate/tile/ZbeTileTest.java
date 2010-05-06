/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.tile;

import java.awt.Color;
import java.awt.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeTileTest {

    public ZbeTileTest() {
    }

    /**
     * Test of getTileColor method, of class ZbeTile.
     */

    @Test
    public void testGetTileColor() {
        //System.out.println("getTileColor");
        ZbeTile instance = new ZbeTile(Color.black, 0,0,0,0,0,0,false,null);
        
        Color expResult = Color.black;
        Color result = instance.getTileColor();

        assertEquals(expResult, result);
    }

    /**
     * Test of getRect method, of class ZbeTile.
     */
    @Test
    public void testGetRect() {
        //System.out.println("getRect");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,0,0,false,new Rectangle(10,10,10,10));


        Rectangle expResult = new Rectangle(10,10,10,10);
        Rectangle result = instance.getRect();
        
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getHFlipValue method, of class ZbeTile.
     */
    @Test
    public void testGetHFlipValue() {
        //System.out.println("getHFlipValue");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,0,0,false,null);
        
        int expResult = 0;
        int result = instance.getHFlipValue();

        assertEquals(expResult, result);
    }

    /**
     * Test of getVFlipValue method, of class ZbeTile.
     */
    @Test
    public void testGetVFlipValue() {
        //System.out.println("getVFlipValue");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,0,0,false,null);
        
        int expResult = 0;
        int result = instance.getVFlipValue();

        assertEquals(expResult, result);
    }

    /**
     * Test of getPaletteID method, of class ZbeTile.
     */
    @Test
    public void testGetPaletteID() {
        ///.out.println("getPaletteID");
        ZbeTile instance = new ZbeTile(null, 0,120,0,0,0,0,false,null);

        int expResult = 120;
        int result = instance.getPaletteID();

        assertEquals(expResult, result);
    }

    /**
     * Test of getTileID method, of class ZbeTile.
     */
    @Test
    public void testGetTileID() {
        //System.out.println("getTileID");
        ZbeTile instance = new ZbeTile(null, 30,0,0,0,0,0,false,null);

        int expResult = 30;
        int result = instance.getTileID();

        assertEquals(expResult, result);
    }

    /**
     * Test of getXcoord method, of class ZbeTile.
     */
    @Test
    public void testGetXcoord() {
        //System.out.println("getXcoord");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,10,0,false,null);

        int expResult = 10;
        int result = instance.getXcoord();

        assertEquals(expResult, result);
    }

    /**
     * Test of getYcoord method, of class ZbeTile.
     */
    @Test
    public void testGetYcoord() {
        //System.out.println("getYcoord");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,0,20,false,null);

        int expResult = 20;
        int result = instance.getYcoord();

        assertEquals(expResult, result);
    }

    /**
     * Test of isWallTile method, of class ZbeTile.
     */
    @Test
    public void testIsWallTile() {
        //System.out.println("isWallTile");
        ZbeTile instance = new ZbeTile(null, 0,0,0,0,0,0,true,null);
        ZbeTile instance2 = new ZbeTile(null, 0,0,0,0,0,0,false,null);

        assertEquals(instance.isWallTile(), true);
        assertEquals(instance2.isWallTile(), false);
    }

}
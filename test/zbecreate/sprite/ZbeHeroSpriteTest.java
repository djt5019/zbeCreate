/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

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
public class ZbeHeroSpriteTest {

    public ZbeHeroSpriteTest() {
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
     * Test of setLives method, of class ZbeHeroSprite.
     */
    @Test
    public void testSetLives() {
        System.out.println("setLives");
        int l = 0;
        ZbeHeroSprite instance = null;
        instance.setLives(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHealth method, of class ZbeHeroSprite.
     */
    @Test
    public void testSetHealth() {
        System.out.println("setHealth");
        int h = 0;
        ZbeHeroSprite instance = null;
        instance.setHealth(h);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHealth method, of class ZbeHeroSprite.
     */
    @Test
    public void testGetHealth() {
        System.out.println("getHealth");
        ZbeHeroSprite instance = null;
        int expResult = 0;
        int result = instance.getHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLives method, of class ZbeHeroSprite.
     */
    @Test
    public void testGetLives() {
        System.out.println("getLives");
        ZbeHeroSprite instance = null;
        int expResult = 0;
        int result = instance.getLives();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportXML method, of class ZbeHeroSprite.
     */
    @Test
    public void testExportXML() {
        System.out.println("exportXML");
        ZbeHeroSprite instance = null;
        instance.exportXML();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
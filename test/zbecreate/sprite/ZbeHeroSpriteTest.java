/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeHeroSpriteTest {

    public ZbeHeroSpriteTest() {
    }

    @Test
    public void testSetLives() {
        int l = 120;
        ZbeHeroSprite instance = new ZbeHeroSprite(0,0,0,0,0,"");
        instance.setLives(l);

        assertTrue(instance.getLives() == l);
    }

    
    @Test
    public void testGetHealth() {
        ZbeHeroSprite instance = new ZbeHeroSprite(0,0,0,0,0,"");

        int expResult = 100;
        int result = instance.getHealth();

        assertEquals(expResult, result);
    }

    @Test
    public void testGetLives() {
        ZbeHeroSprite instance = new ZbeHeroSprite(0,0,0,0,0,"");

        int expResult = 5;
        int result = instance.getLives();

        assertEquals(expResult, result);
    }

}
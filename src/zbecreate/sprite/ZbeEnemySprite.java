/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

import java.awt.Graphics;

/**
 * @author Dan
 */

public class ZbeEnemySprite extends ZbeBaseSprite{

    private int health;
    private int lives;


    public ZbeEnemySprite(String location) {
        health = 100;
        lives  = 5;
        loadImage(location);
    }

    public void exportXML(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

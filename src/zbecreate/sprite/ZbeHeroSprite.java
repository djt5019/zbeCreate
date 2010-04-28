/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

/**
 * @author Dan
 */

public class ZbeHeroSprite extends ZbeBaseSprite{

    private int health = 0;
    private int lives  = 0;    

    public ZbeHeroSprite(String location) {
        health = 100;
        lives  = 5;
        loadImage(location);
    }

    public void setLives(int l){
        this.lives = l;
    }

    public void setHealth(int h){
        this.health = h;
    }

    public int getHealth(){ return health; }
    public int getLives() { return lives; }

    
    public void exportXML(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate.sprite;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;

/**
 * @author Dan
 */

public class ZbeImageUtil {

    
    public static BufferedImage getBufferedImage(String imageFile, Component c){
        Image image = c.getToolkit().getImage(imageFile);

        waitForImage(image, c);

        BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(c), image.getHeight(c), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, c);

        return(bufferedImage);
    }

    public static boolean waitForImage(Image image, Component c) {
        MediaTracker tracker = new MediaTracker(c);

        tracker.addImage(image, 0);

        try {
            tracker.waitForAll();
        } 
        catch(InterruptedException ie) {
            ie.printStackTrace();
        }
        return(!tracker.isErrorAny());
    }
    
}

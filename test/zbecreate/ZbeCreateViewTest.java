/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate;

import java.io.File;
import org.jdesktop.application.SingleFrameApplication;
import org.junit.Test;
import zbecreate.tile.ZbeTile;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeCreateViewTest extends SingleFrameApplication{

    public ZbeCreateViewTest() {
    }
    /**
     * Test of exportXML method, of class ZbeCreateView.
     */
    @Test
    public void testExportXML() throws Exception {
        ZbeCreateView instance = new ZbeCreateView(this);

        java.util.ArrayList<ZbeTile> tiles = new java.util.ArrayList<ZbeTile>();
        java.util.ArrayList<ZbeTile> walls = new java.util.ArrayList<ZbeTile>();

        for(int i = 0; i < 1000; ++i){
            ZbeTile wall = new ZbeTile(null,i,0,0,0,0,0,true,null);
            ZbeTile tile = new ZbeTile(null,i,0,0,0,0,0,false,null);

            walls.add(wall);
            tiles.add(tile);
        }

        assertTrue( !tiles.isEmpty() );
        assertTrue( !walls.isEmpty() );

        File f = new File(".");
        String testLocation = f.getCanonicalPath() + "/ZoidbergCreatorViewTestData";
        String testResult = testLocation + ".xml";

        File temp = new File(testResult);
        temp.delete();

        instance.exportXML(testLocation, null, null);
        f = new File(testResult);
        assertFalse( f.exists() );
        f.delete();

        instance.exportXML(testLocation,tiles,walls);
        f = new File(testResult);
        assertTrue( f.exists() );
        f.delete();

        instance.exportXML(testLocation, null, walls);
        f = new File(testResult);
        assertTrue( f.exists() );
        f.delete();

        instance.exportXML(testLocation, tiles, null);
        f = new File(testResult);
        assertTrue( f.exists() );
        f.delete();

    }

    @Override
    protected void startup() {
    }

}
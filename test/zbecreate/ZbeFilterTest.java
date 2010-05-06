/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zbecreate;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dan
 */
public class ZbeFilterTest {

    public ZbeFilterTest() {
    }

    private void runTests(){
        this.testAccept();
        this.testAdd();
        this.testSetDescription();
    }
    /**
     * Test of add method, of class ZbeFilter.
     */
    @Test
    public void testAdd() {
        ZbeFilter instance = new ZbeFilter("");

        String[] ex = new String[3];
        ex[0] = "jpg";
        ex[1] = "png";
        ex[2] = "gif";

        instance.add(ex[0]);
        instance.add(ex[1]);
        instance.add(ex[2]);

        java.util.ArrayList list = instance.getExtensions();

        assertTrue(list.size() == 3);

        for(int i = 0; i < list.size(); ++i){
            System.out.println(list.get(i).toString());
            assertTrue( list.get(i).toString().equals(ex[i]) );
        }
    }

    /**
     * Test of accept method, of class ZbeFilter.
     */
    @Test
    public void testAccept() {
        ZbeFilter instance = new ZbeFilter("");

        instance.add("png");

        File file = new File("./src/zbecreate/resources/about.png");
        File file2 = new File("/stc/zbecreate/tile/ZbeTile.java");

        boolean result = instance.accept(file);        
        assertEquals(result, true);

        result = instance.accept(file2);
        assertEquals(result, false);
    }

    /**
     * Test of setDescription method, of class ZbeFilter.
     */
    @Test
    public void testSetDescription() {
        ZbeFilter instance = new ZbeFilter("TEST DESCRIPTION");

        String description = "TEST DESCRIPTION";

        assertTrue(description.equals(instance.getDescription()));
    }

}
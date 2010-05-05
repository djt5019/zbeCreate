package zbecreate;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author dan
 */
class ZbeFilter extends javax.swing.filechooser.FileFilter{
    private ArrayList exts;
    private String desc;

    public ZbeFilter(String description){
        exts = new ArrayList();
        setDescription(description);
    }

    /**
     * Adds the extension into the list of accepted extensions.
     *
     * @author Dan Tracy
     */
    public void add(String ex){
        exts.add(ex);
    }


    /**
     *  If the file extension is in the list the it will appear in the window.
     *
     * @author Dan Tracy
     * @param f This is the file that will be tested for a valid extension.
     */
    @Override
    public boolean accept(File f) {
        if ( f.isDirectory() ) {
            return true;
        }
        else if ( f.isFile() ) {
            Iterator it = exts.iterator();
            while (it.hasNext()) {
                if (f.getName().endsWith((String) it.next()))
                    return true;
            }
        }

        return false;
    }

    public void setDescription(String s){
        desc = s;
    }

    @Override
    public String getDescription() {
        return desc;
    }
}

package zbecreate;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author dan
 */
class ZbeFilter extends javax.swing.filechooser.FileFilter{
    /**
     * Holds all the file extensions that will be visible in the File Chooser.
     */
    private ArrayList exts;
    /**
     * The description of the files included.
     */
    private String desc;

    public ZbeFilter(String description){
        exts = new ArrayList();
        setDescription(description);
    }

    /**
     * Adds the extension into the list of accepted extensions.
     * @param ex The extension to add to the list of accepted extensions.
     */
    public void add(String ex){
        exts.add(ex);
    }


    /**
     *  If the file extension is in the list the it will appear in the window.
     * @param file This is the file that will be tested for a valid extension.
     */
    @Override
    public boolean accept(File file) {
        if ( file.isDirectory() ) {
            return true;
        }
        else if ( file.isFile() ) {
            Iterator it = exts.iterator();
            while (it.hasNext()) {
                if (file.getName().endsWith((String) it.next()))
                    return true;
            }
        }

        return false;
    }

    /**
     *  Updates the description with the new description.
     *  @param description The new description
     */
    public void setDescription(String description){
        desc = description;
    }

    /**
     *  Returns the description of the file extensions
     *  @return String description
     */
    @Override
    public String getDescription() {
        return desc;
    }
}

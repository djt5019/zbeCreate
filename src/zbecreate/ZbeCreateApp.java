/*
 * ZbeCreateApp.java
 */

package zbecreate;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ZbeCreateApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ZbeCreateView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ZbeCreateApp
     */
    public static ZbeCreateApp getApplication() {
        return Application.getInstance(ZbeCreateApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(final String[] args) {
        launch(ZbeCreateApp.class, args);
    }
}

package zbecreate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import zbecreate.tile.ZbeTile;

/**
 * The application's main frame.
 */
public class ZbeCreateView extends FrameView{

    private Color currentColor = Color.WHITE;
    private Color[] previousColors;
    private ArrayList<ZbeTile> tileList;
    private ArrayList<ZbeTile> wallList;
    private BufferedImage background;
    private ZbeTile[][] tileGraph;
    private ZbeFilter filter;
    private int levelHeight;
    private int levelWidth;

    public ZbeCreateView(SingleFrameApplication app) {
        super(app);
        super.getFrame().setName("zbeCreate");

        initComponents();

        mouse = MouseSelection.PLACE;
        levelWidth = drawingPanel.getPreferredSize().height;
        levelHeight = drawingPanel.getPreferredSize().width;

        initBackground();
        initColorPanel();
        initPrevColorsPanel(prevColor1);
        initPrevColorsPanel(prevColor2);
        initPrevColorsPanel(prevColor3);
        initButtons();
        initXMLButton();
        
        tileGraph = new ZbeTile[levelWidth][levelHeight];
        
        previousColors = new Color[3];
        tileList = new ArrayList();
        wallList = new ArrayList();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    /**
     * This function will help keep track of the colors that the user chooses
     * by storing them into an array.  As a user selects a new color the old
     * ones are shifted down by one to make room for the new color. The
     * oldest color in the array is overwritten.
     * @param newColor The color that will be stored in the most recent color block
     */
    private void shiftColors(Color newColor){
        
        previousColors[2] = previousColors[1];
        previousColors[1] = previousColors[0];
        previousColors[0] = newColor;
        prevColor1.setBackground(previousColors[0]);
        prevColor2.setBackground(previousColors[1]);
        prevColor3.setBackground(previousColors[2]);

    }

    /**
     * The initColorPanel function will initalize the mouse action listeners
     * for the color panel.  Upon clicking the colorPanel the user will be
     * prompted with the JColorChooser in order to choose a new color.  The
     * selected color will be saved into the currentColor variable.
     */
    private void initColorPanel() {
        colorPanel.addMouseListener( new MouseListener(){

            public void mouseClicked(MouseEvent e)  {
                shiftColors(currentColor);
                currentColor = JColorChooser.showDialog(null, "Color Chooser", Color.yellow);
                colorPanel.setBackground(currentColor);

            }
            public void mousePressed(MouseEvent e)  {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e)  {}
            public void mouseExited(MouseEvent e)   {}

        });
    }

    /**
     * The initPrevColorsPanel function will initalize the mouse action listeners
     * for the previous color panels.  Upon clicking one of the three previous
     * color panels the color contained within the panel will be copied into
     * the currentColor variable.  Clicking on the panels will not cause the
     * colors to shift.
     */
    private void initPrevColorsPanel(final JPanel panel){
        
        panel.addMouseListener( new MouseListener(){

            public void mouseClicked(MouseEvent e)  {
                currentColor = panel.getBackground();
                colorPanel.setBackground(currentColor);
            }
            public void mousePressed(MouseEvent e)  {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e)  {}
            public void mouseExited(MouseEvent e)   {}

        });
    }
    
    private void initBackground(){
        String imageLocation = "";

        try{
            imageLocation = new File(".").getCanonicalPath();
            imageLocation += "/src/zbecreate/resources/background.png";
            background = ImageIO.read(new File(imageLocation));
        }catch(Exception ex){ ex.printStackTrace(); System.exit(1); }

    }

    private void initXMLButton(){
        filter = new ZbeFilter("XML Document");
        filter.add("xml");

        xmlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setFileFilter(filter);

                int value = chooser.showSaveDialog(sidePanel);

                if( value == javax.swing.JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getAbsolutePath();
                    try{
                        exportXML(name);
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void initButtons(){

        ActionListener listener = new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if( e.getActionCommand().equals("PLACE")){
                    System.out.println("MOUSE = PLACE");
                    mouse = MouseSelection.PLACE;
                }
                else if( e.getActionCommand().equals("DELETE")){
                    System.out.println("MOUSE = DELETE");
                    mouse = MouseSelection.DELETE;
                }
                else if( e.getActionCommand().equals("SETBG")){
                    System.out.println("MOUSE = SETBG");
                    mouse = MouseSelection.SETBG;
                }
            }
        };

        mouseSelectionBtnGroup.add(placeTileBtn);
        mouseSelectionBtnGroup.add(deleteTileBtn);
        mouseSelectionBtnGroup.add(setBgBtn);

        placeTileBtn.setActionCommand("PLACE");
        deleteTileBtn.setActionCommand("DELETE");
        setBgBtn.setActionCommand("SETBG");

        placeTileBtn.addActionListener( listener);
        deleteTileBtn.addActionListener(listener);
        setBgBtn.addActionListener(listener);

        mouseSelectionBtnGroup.setSelected(placeTileBtn.getModel(), true);

    }

    /**
     * The showAboutBox function will create an instance of the ZbeCreateAboutBox
     * class and will display it to the user.
     */
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ZbeCreateApp.getApplication().getMainFrame();
            aboutBox = new ZbeCreateAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ZbeCreateApp.getApplication().show(aboutBox);
    }

    /**
     * This will iterate through the ArrayList of Tiles getting the tileID,
     * paletteID, and horizontal/vertical flip values.  Upon each iteration
     * the tile information will be written to the file "filename.xml".
     * @param filename The name of the file the user wishes to write to.
     * @throws IOException
     */
    public void exportXML(String filename) throws IOException{

        System.out.println("EXPORTING");
        BufferedWriter out = new BufferedWriter( new FileWriter(filename + ".xml") );

        if( !wallList.isEmpty() ){
            out.write("<zbe>\n<levels>\n<objects>\n");
            for(ZbeTile s : wallList){
                int x = s.getXcoord();
                int y = s.getYcoord();
                int id= s.getTileID();

                String str = "\t<object id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" weight=\"255\" hgrav=\"0\" vgrav=\"0\"/> </object>\n";

                out.write(str);
            }
        }

        if( !tileList.isEmpty() ){
            out.write("\n</zbe>\n</levels>\n</objects>\n\n");
            out.write("<Zbebackground>\n\t<background>\n\t\t<row>\n");
            for( ZbeTile s : tileList){
                String id = "id = \"" + s.getTileID() + "\"\n";
                String pa = "palette = \"" + s.getPaletteID() + "\"\n";
                String hF = "hflip = \"" + s.getHFlipValue() + "\"\n";
                String vF = "hflip = \"" + s.getVFlipValue() + "\"\n";

                String statement = "\t\t\t<file   " + id +
                                       "\t\t\t\t" + pa +
                                       "\t\t\t\t" + hF +
                                       "\t\t\t\t" + vF +
                                       "\t\t\t/>\n";

                out.write(statement);
            }
            out.write("\n\t\t</row>\n\t</background>\n\n</Zbebackground>");
        }

        out.close();
    }

    private class DrawingArea extends JPanel implements MouseListener, MouseMotionListener{
        /**
         * Will contain the functions that are relevant to the drawing area. The
         * purpose of this class is to create a panel upon which the user can draw.
         * The DrawingArea class will first initalize the mouse action listeners then
         * await for the user to interact with the panel.
         */

        private int size = ZbeTile.tileSize;
        private boolean mouseDragging = false;

        public DrawingArea(){
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        public void mouseClicked(MouseEvent e)  {
            int x = e.getX();
            int y = e.getY();

            if(mouse.equals(MouseSelection.PLACE))
                placeTile(x,y);
            else if(mouse.equals(MouseSelection.DELETE))
                eraseTile(x,y);
            repaint();
        }

        /**
         * This function will calculate the size of the tile which is
         * by default 8x8 pixels.  After calculating the correct size and
         * creating a rectangle that will fill the entire tile it will add
         * it into the tileList.
         * @param x The X coordinate where the user clicked
         * @param y The Y coordinate where the user clicked
         */
        public void placeTile(int x, int y){
            if( !mouse.equals(MouseSelection.PLACE))
                return;

            int pid   = currentColor.getRGB();
            int hFlip = hFlipCbox.getSelectedIndex();
            int vFlip = vFlipCbox.getSelectedIndex();
            int modX  = ((x - (x % size)));
            int modY  = ((y - (y % size)));
            int tid   = modX + modY*levelWidth;

            boolean wall = (tileCbox.getSelectedIndex() == 1) ? true:false;

            if(modX < 0 || modY < 0)
                return;

            Rectangle r = new Rectangle(modX,modY,size,size);

            ZbeTile t = new ZbeTile(currentColor, tid, pid, hFlip, vFlip, modX, modY,wall, r);

            if( tileGraph[modX][modY] == null){
                tileGraph[modX][modY] = t;
                if( wall == true)
                    wallList.add(t);
                else
                    tileList.add(t);
            }
            else{
                //If the tile has already been placed update the color
                ZbeTile temp = tileGraph[modX][modY];

                if(temp.isWallTile() == wall)
                    temp.setColor(currentColor);
            }
        }

        public void eraseTile(int x, int y){
            int modX  = ((x - (x % size)));
            int modY  = ((y - (y % size)));
            int tid   = modX + modY*levelWidth;

            if( modX < 0 || modY < 0)
                return;

            if(tileGraph[modX][modY] == null)
                return;

            tileGraph[modX][modY] = null;

            for(ZbeTile temp : tileList){
                if(temp.getTileID() == tid){
                    if(temp.isWallTile() == true)
                        wallList.remove(temp);
                    else
                        tileList.remove(temp);
                    break;
                }
            }
        }

        public void mousePressed(MouseEvent e)  {
            if(mouseDragging == true)
                return;

            mouseDragging = true;
        }

        public void mouseDragged(MouseEvent e) {
            if( !mouseDragging)
                return;

            if(mouse.equals(MouseSelection.PLACE))
                placeTile(e.getX(), e.getY());
            else if(mouse.equals(MouseSelection.DELETE))
                eraseTile(e.getX(), e.getY());

            repaint();
        }

        public void mouseReleased(MouseEvent e) { mouseDragging = false; }
        public void mouseMoved(MouseEvent e)    {}
        public void mouseEntered(MouseEvent e)  {}
        public void mouseExited(MouseEvent e)   {}

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D graph = (Graphics2D)g;

            System.out.println("TILES = "  + tileList.size()+wallList.size());

            graph.drawImage(background, null, this);

            for(ZbeTile temp : tileList){
                graph.setColor(temp.getTileColor());
                graph.fill(temp.getRect());
            }

            for(ZbeTile temp : wallList){
                graph.setColor(temp.getTileColor());
                graph.fill(temp.getRect());
            }

        }
    }


    // <editor-fold defaultstate="collapsed" desc="Auto Generated Code">
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        placeTileBtn = new javax.swing.JRadioButton();
        deleteTileBtn = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        colorPanel = new javax.swing.JPanel();
        spritePropertiesLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hFlipCbox = new javax.swing.JComboBox();
        vFlipCbox = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        setBgBtn = new javax.swing.JRadioButton();
        prevColor3 = new javax.swing.JPanel();
        prevColor2 = new javax.swing.JPanel();
        prevColor1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        xmlBtn = new javax.swing.JButton();
        tileCbox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        drawScrollPanel = new javax.swing.JScrollPane();
        drawingPanel = new DrawingArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jFrame1 = new javax.swing.JFrame();
        mouseSelectionBtnGroup = new javax.swing.ButtonGroup();
        jMenu1 = new javax.swing.JMenu();
        showGridlinesOptionMenuItem = new javax.swing.JCheckBoxMenuItem();
        spriteSelectionBtnGroup = new javax.swing.ButtonGroup();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(zbecreate.ZbeCreateApp.class).getContext().getResourceMap(ZbeCreateView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new java.awt.BorderLayout());

        sidePanel.setName("sidePanel"); // NOI18N
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        placeTileBtn.setText(resourceMap.getString("placeTileBtn.text")); // NOI18N
        placeTileBtn.setName("placeTileBtn"); // NOI18N
        sidePanel.add(placeTileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        deleteTileBtn.setText(resourceMap.getString("deleteTileBtn.text")); // NOI18N
        deleteTileBtn.setName("deleteTileBtn"); // NOI18N
        sidePanel.add(deleteTileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jSeparator1.setName("jSeparator1"); // NOI18N
        sidePanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 170, 10));

        colorPanel.setBackground(resourceMap.getColor("colorPanel.background")); // NOI18N
        colorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorPanel.setName("colorPanel"); // NOI18N
        colorPanel.setLayout(new java.awt.GridLayout(1, 0));
        sidePanel.add(colorPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 30, 30));

        spritePropertiesLabel.setText(resourceMap.getString("spritePropertiesLabel.text")); // NOI18N
        spritePropertiesLabel.setName("spritePropertiesLabel"); // NOI18N
        sidePanel.add(spritePropertiesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, -1, -1));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        sidePanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        sidePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        hFlipCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disabled", "Enabled" }));
        hFlipCbox.setName("hFlipCbox"); // NOI18N
        sidePanel.add(hFlipCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 110, 20));

        vFlipCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disabled", "Enabled" }));
        vFlipCbox.setName("vFlipCbox"); // NOI18N
        sidePanel.add(vFlipCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 110, 20));

        jSeparator2.setName("jSeparator2"); // NOI18N
        sidePanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, 10));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        sidePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        sidePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        setBgBtn.setText(resourceMap.getString("setBgBtn.text")); // NOI18N
        setBgBtn.setName("setBgBtn"); // NOI18N
        sidePanel.add(setBgBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        prevColor3.setBackground(resourceMap.getColor("prevColor1.background")); // NOI18N
        prevColor3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        prevColor3.setName("prevColor3"); // NOI18N

        javax.swing.GroupLayout prevColor3Layout = new javax.swing.GroupLayout(prevColor3);
        prevColor3.setLayout(prevColor3Layout);
        prevColor3Layout.setHorizontalGroup(
            prevColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        prevColor3Layout.setVerticalGroup(
            prevColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        sidePanel.add(prevColor3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 30, 30));

        prevColor2.setBackground(resourceMap.getColor("prevColor1.background")); // NOI18N
        prevColor2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        prevColor2.setName("prevColor2"); // NOI18N

        javax.swing.GroupLayout prevColor2Layout = new javax.swing.GroupLayout(prevColor2);
        prevColor2.setLayout(prevColor2Layout);
        prevColor2Layout.setHorizontalGroup(
            prevColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        prevColor2Layout.setVerticalGroup(
            prevColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        sidePanel.add(prevColor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 30, 30));

        prevColor1.setBackground(resourceMap.getColor("prevColor1.background")); // NOI18N
        prevColor1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        prevColor1.setName("prevColor1"); // NOI18N

        javax.swing.GroupLayout prevColor1Layout = new javax.swing.GroupLayout(prevColor1);
        prevColor1.setLayout(prevColor1Layout);
        prevColor1Layout.setHorizontalGroup(
            prevColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        prevColor1Layout.setVerticalGroup(
            prevColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        sidePanel.add(prevColor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 30, 30));

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        sidePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, -1));

        xmlBtn.setText(resourceMap.getString("xmlBtn.text")); // NOI18N
        xmlBtn.setName("xmlBtn"); // NOI18N
        sidePanel.add(xmlBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        tileCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Background", "Wall" }));
        tileCbox.setName("tileCbox"); // NOI18N
        sidePanel.add(tileCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, 20));

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        sidePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, -1, -1));

        mainPanel.add(sidePanel, java.awt.BorderLayout.LINE_START);

        drawScrollPanel.setName("drawScrollPanel"); // NOI18N

        drawingPanel.setBackground(resourceMap.getColor("drawingPanel.background")); // NOI18N
        drawingPanel.setName("drawingPanel"); // NOI18N
        drawingPanel.setPreferredSize(new java.awt.Dimension(2000, 2000));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2000, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2000, Short.MAX_VALUE)
        );

        drawScrollPanel.setViewportView(drawingPanel);

        mainPanel.add(drawScrollPanel, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(zbecreate.ZbeCreateApp.class).getContext().getActionMap(ZbeCreateView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 699, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jFrame1.setName("jFrame1"); // NOI18N

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setName("jMenu1"); // NOI18N

        showGridlinesOptionMenuItem.setSelected(true);
        showGridlinesOptionMenuItem.setName("showGridlinesOptionMenuItem"); // NOI18N
        jMenu1.add(showGridlinesOptionMenuItem);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel colorPanel;
    protected javax.swing.JRadioButton deleteTileBtn;
    protected javax.swing.JScrollPane drawScrollPanel;
    protected javax.swing.JPanel drawingPanel;
    public static javax.swing.JComboBox hFlipCbox;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.ButtonGroup mouseSelectionBtnGroup;
    protected javax.swing.JRadioButton placeTileBtn;
    private javax.swing.JPanel prevColor1;
    private javax.swing.JPanel prevColor2;
    private javax.swing.JPanel prevColor3;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton setBgBtn;
    public static javax.swing.JCheckBoxMenuItem showGridlinesOptionMenuItem;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel spritePropertiesLabel;
    private javax.swing.ButtonGroup spriteSelectionBtnGroup;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JComboBox tileCbox;
    public static javax.swing.JComboBox vFlipCbox;
    private javax.swing.JButton xmlBtn;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    // </editor-fold>

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    /**
     * MouseSelection holds all possible tile and sprite interactions.
     */
    public static enum MouseSelection {

        /**
         * Sets the mouse variable to delete tiles when clicked.
         */
        DELETE,
        /**
         * Sets the mouse variable to place new tiles on the drawing panel.
         */
        PLACE,
        /**
         * Sets the background of the drawing panel to the currently selected color.
         */
        SETBG
    };
    /**
     * Holds the current tile selection from the button group.
     */
    public static MouseSelection mouse;
}

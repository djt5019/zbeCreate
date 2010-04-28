/*
 * ZbeCreateView.java
 */

package zbecreate;

import java.awt.Color;
import java.awt.GridLayout;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The application's main frame.
 */
public class ZbeCreateView extends FrameView implements ActionListener{

    protected String spriteImageLocation;
    public static enum mouseSelection{ SELECT, DELETE, PLACE};
    public static mouseSelection mouse = mouseSelection.SELECT;

    public static JLabel propertyLabel;
    public static JLabel hFlipLabel;
    public static JLabel vFlipLabel;


    public ZbeCreateView(SingleFrameApplication app) {
        super(app);

        initComponents();

        propertyLabel = new JLabel("Sprite Properties");
        hFlipLabel = new JLabel("Sprite H-Flip Value");
        vFlipLabel = new JLabel("Sprite V-Flip Value");

        mouseSelectionBtnGroup.add(selectSpriteBtn);
        mouseSelectionBtnGroup.add(placeSpriteBtn);
        mouseSelectionBtnGroup.add(deleteSpriteBtn);

        selectSpriteBtn.setActionCommand("SELECT");
        placeSpriteBtn.setActionCommand("PLACE");
        deleteSpriteBtn.setActionCommand("DELETE");

        selectSpriteBtn.addActionListener(this);
        placeSpriteBtn.addActionListener( this);
        deleteSpriteBtn.addActionListener(this);

        mouseSelectionBtnGroup.setSelected(selectSpriteBtn.getModel(), true);

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

    public void actionPerformed(ActionEvent e){
        if( e.getActionCommand().equals("SELECT") ){
            System.out.println("MOUSE = SELECT");
            mouse = mouseSelection.SELECT;
        }
        else if( e.getActionCommand().equals("PLACE")){

            System.out.println("MOUSE = PLACE");
            mouse = mouseSelection.PLACE;
        }
        else if( e.getActionCommand().equals("DELETE")){

            System.out.println("MOUSE = DELETE");
            mouse = mouseSelection.DELETE;
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ZbeCreateApp.getApplication().getMainFrame();
            aboutBox = new ZbeCreateAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ZbeCreateApp.getApplication().show(aboutBox);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        selectSpriteBtn = new javax.swing.JRadioButton();
        placeSpriteBtn = new javax.swing.JRadioButton();
        deleteSpriteBtn = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        previewPanel = new javax.swing.JPanel();
        exportXMLBtn = new javax.swing.JButton();
        spritePropertiesLabel = new javax.swing.JLabel();
        tileIDLabel = new javax.swing.JLabel();
        tileIDtxt = new javax.swing.JFormattedTextField();
        paletteIDLabel = new javax.swing.JLabel();
        paletteIDtxt = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hFlipCbox = new javax.swing.JComboBox();
        vFlipCbox = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        drawScrollPanel = new javax.swing.JScrollPane();
        drawingPanel = new ZbePaint();
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

        selectSpriteBtn.setText(resourceMap.getString("selectSpriteBtn.text")); // NOI18N
        selectSpriteBtn.setName("selectSpriteBtn"); // NOI18N
        sidePanel.add(selectSpriteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        placeSpriteBtn.setText(resourceMap.getString("placeSpriteBtn.text")); // NOI18N
        placeSpriteBtn.setName("placeSpriteBtn"); // NOI18N
        sidePanel.add(placeSpriteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        deleteSpriteBtn.setText(resourceMap.getString("deleteSpriteBtn.text")); // NOI18N
        deleteSpriteBtn.setName("deleteSpriteBtn"); // NOI18N
        sidePanel.add(deleteSpriteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jSeparator1.setName("jSeparator1"); // NOI18N
        sidePanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 170, 10));

        previewPanel.setBackground(resourceMap.getColor("previewPanel.background")); // NOI18N
        previewPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        previewPanel.setName("previewPanel"); // NOI18N
        previewPanel.setLayout(new java.awt.GridLayout());
        sidePanel.add(previewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 80, 80));

        exportXMLBtn.setText(resourceMap.getString("exportXMLBtn.text")); // NOI18N
        exportXMLBtn.setName("exportXMLBtn"); // NOI18N
        sidePanel.add(exportXMLBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, -1));

        spritePropertiesLabel.setText(resourceMap.getString("spritePropertiesLabel.text")); // NOI18N
        spritePropertiesLabel.setName("spritePropertiesLabel"); // NOI18N
        sidePanel.add(spritePropertiesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, -1, -1));

        tileIDLabel.setText(resourceMap.getString("tileIDLabel.text")); // NOI18N
        tileIDLabel.setName("tileIDLabel"); // NOI18N
        sidePanel.add(tileIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        tileIDtxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tileIDtxt.setText(resourceMap.getString("tileIDtxt.text")); // NOI18N
        tileIDtxt.setName("tileIDtxt"); // NOI18N
        sidePanel.add(tileIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 80, -1));

        paletteIDLabel.setText(resourceMap.getString("paletteIDLabel.text")); // NOI18N
        paletteIDLabel.setName("paletteIDLabel"); // NOI18N
        sidePanel.add(paletteIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        paletteIDtxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paletteIDtxt.setText(resourceMap.getString("paletteIDtxt.text")); // NOI18N
        paletteIDtxt.setName("paletteIDtxt"); // NOI18N
        sidePanel.add(paletteIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 80, -1));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        sidePanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, -1));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        sidePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, -1));

        hFlipCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disabled", "Enabled" }));
        hFlipCbox.setName("hFlipCbox"); // NOI18N
        sidePanel.add(hFlipCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, 20));

        vFlipCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disabled", "Enabled" }));
        vFlipCbox.setName("vFlipCbox"); // NOI18N
        sidePanel.add(vFlipCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, -1, 20));

        jSeparator2.setName("jSeparator2"); // NOI18N
        sidePanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 170, 10));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        sidePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        sidePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

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
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 686, Short.MAX_VALUE)
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
    protected javax.swing.JRadioButton deleteSpriteBtn;
    protected javax.swing.JScrollPane drawScrollPanel;
    protected javax.swing.JPanel drawingPanel;
    public static javax.swing.JButton exportXMLBtn;
    public static javax.swing.JComboBox hFlipCbox;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.ButtonGroup mouseSelectionBtnGroup;
    private javax.swing.JLabel paletteIDLabel;
    public static javax.swing.JFormattedTextField paletteIDtxt;
    protected javax.swing.JRadioButton placeSpriteBtn;
    public static javax.swing.JPanel previewPanel;
    private javax.swing.JProgressBar progressBar;
    protected javax.swing.JRadioButton selectSpriteBtn;
    public static javax.swing.JCheckBoxMenuItem showGridlinesOptionMenuItem;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel spritePropertiesLabel;
    private javax.swing.ButtonGroup spriteSelectionBtnGroup;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel tileIDLabel;
    public static javax.swing.JFormattedTextField tileIDtxt;
    public static javax.swing.JComboBox vFlipCbox;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    
}

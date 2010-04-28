package zbecreate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import zbecreate.sprite.*;
import zbecreate.ZbeCreateView.mouseSelection;
import static zbecreate.ZbeCreateView.mouse;
import static zbecreate.ZbeCreateView.previewPanel;
import static zbecreate.ZbeCreateView.exportXMLBtn;

public class ZbePaint extends JPanel implements MouseMotionListener, MouseListener {

    private ArrayList<ZbeBaseSprite> list;
    private final int levelHeight = 100;
    private final int levelWidth  = 100;
    private final int tileSize    = 8;
    private JFileChooser imageChooser;
    private Image tilePreview;
    private Graphics graphicsBuffer;
    private JPanel levelSprites[];
    private ImageIcon icon;
    private JLabel iconLabel;
    private String imageLocation;

    public ZbePaint() {
        list = new ArrayList<ZbeBaseSprite>();
        imageChooser = new JFileChooser();
        iconLabel = new JLabel();
        this.setSize(levelHeight, levelWidth);

        zbeFilter filter = new zbeFilter();
        imageChooser.setFileFilter(filter);

        try{
            imageLocation = new java.io.File(".").getCanonicalPath();
        }catch(Exception ex){ ex.printStackTrace(); System.exit(1); }

        //Set Zoidbergs mouth to the default image
        imageLocation += "/src/zbecreate/resources/about.png";

        icon = new ImageIcon(imageLocation, "Click to load a sprite");
        iconLabel.setIcon(icon);
        iconLabel.setVisible(true);
        previewPanel.add(iconLabel);
        previewPanel.addMouseListener(this);

        addMouseListener(this);
        addMouseMotionListener(this);

       exportXMLBtn.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    System.out.println("EXPORTING");
                    ZbeBaseSprite.exportXML( list );
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }});
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D) g;

        for(int i = 0; i < levelHeight; ++i)
            for(int j = 0; j< levelWidth; ++j)
            {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, j*tileSize, levelWidth*tileSize, j*tileSize);
                g.drawLine(i*tileSize, 0, i*tileSize, levelHeight*tileSize);
            }

        for(int i = 0; i < list.size(); i++){
            ZbeBaseSprite temp = list.get(i);
            int h = temp.getTileHeight();
            int w = temp.getTileWidth();
            int x = temp.getXPosition();
            int y = temp.getYPosition();

            if( mouse.equals(mouseSelection.PLACE) ){
                System.out.printf("Printing sprite: %d\n", list.size());
                graph.drawImage(temp.getImage(), x-(h/2), y-(w/2), h, w, this);
            }
        }
    }

    public void placeSprite(int x, int y){

        ZbeBaseSprite temp = new ZbeHeroSprite(imageLocation);
        temp.setPosition(x, y);
        list.add(temp);

        repaint();
    }



    public void deleteSprite(){
    }

    public void moveSprite(){
    }

    public void mousePressed(MouseEvent e) {
        if( e.getSource().equals(previewPanel) )
        {
            int result = imageChooser.showOpenDialog(this);
            if( result == JFileChooser.APPROVE_OPTION){
                imageLocation = imageChooser.getSelectedFile().getAbsolutePath();
                //set image icon
            }
            return;
        }

        if( !mouse.equals( mouseSelection.PLACE) )
            return;

        int x = e.getX();
        int y = e.getY();

        placeSprite(x,y);
    }


    public void mouseDragged(MouseEvent e) {}
    public void mouseReleased(MouseEvent e){}
    public void mouseMoved(MouseEvent e)   {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e)  {}
    public void mouseClicked(MouseEvent e) {}

    private class zbeFilter extends javax.swing.filechooser.FileFilter{
        private ArrayList exts;
        private String desc;

        public zbeFilter(){
            exts = new ArrayList();
            exts.add("jpg");
            exts.add("png");
            exts.add("gif");
            setDescription("Image Files (jpg, png, gif)");
        }

        public void add(String ex){
            exts.add(ex);
        }

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

} 
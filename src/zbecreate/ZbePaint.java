package zbecreate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import zbecreate.sprite.*;

import static zbecreate.ZbeCreateView.*;

public class ZbePaint extends JPanel implements MouseMotionListener, MouseListener {

    private ArrayList<ZbeBaseSprite> list;
    private final int levelHeight = 100;
    private final int levelWidth  = 100;
    private static final int tileSize    = 8;
    private JFileChooser imageChooser;
    private ImageIcon icon;
    private Image background;
    private JLabel iconLabel;
    private String imageLocation;
    private ZbeBaseSprite[][] level;
    private static int count = 0;

    public ZbePaint() {
        list = new ArrayList<ZbeBaseSprite>();
        imageChooser = new JFileChooser();
        iconLabel = new JLabel();
        zbeFilter filter = new zbeFilter();
        level = new ZbeBaseSprite[1000][1000];

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
       // colorPanel.addMouseListener(this);

        this.setSize(levelHeight, levelWidth);

        imageChooser.setFileFilter(filter);

        try{
            imageLocation = new java.io.File(".").getCanonicalPath();
            imageLocation += "/src/zbecreate/resources/about.png";
        }catch(Exception ex){ ex.printStackTrace(); System.exit(1); }

        addPreviewImage(imageLocation);

        //colorPanel.add(iconLabel);
        
        /*exportXMLBtn.addActionListener( new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        try{
        System.out.println("EXPORTING");
        ZbeBaseSprite.exportXML( list );
        }catch(IOException ex){
        ex.printStackTrace();
        }
        }});*/
    }

    public void addPreviewImage(String location){
        Image preview = null;
        try{
            preview = ImageIO.read( new File(imageLocation));
        }catch(Exception ex){ }

        Image tempImage = preview.getScaledInstance(70,70, Image.SCALE_FAST);
        icon = new ImageIcon(tempImage);
        iconLabel.setIcon(icon);
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

        if( background != null){
            int size = background.getWidth(this);
            
            for(int i = 0; i < levelWidth*levelWidth; i+=size)
                graph.drawImage(background,i,0, null);

        }

        for(int i = 0; i < list.size(); i++){
            ZbeBaseSprite temp = list.get(i);
            int h = temp.getTileHeight();
            int w = temp.getTileWidth();
            int x = temp.getXPosition();
            int y = temp.getYPosition();

            graph.drawImage(temp.getImage(), x-(h/2), y-(w/2), h, w, this);
        }
    }

    public void placeSprite(int x, int y){
/*
        int modX = x/tileSize;
        int modY = y/tileSize;
        int hF = hFlipCbox.getSelectedIndex();
        int vF = vFlipCbox.getSelectedIndex();
        int tid;
        int pid;
        String tileId = tileIDtxt.getText();
        String paleId = paletteIDtxt.getText();
        
        try{
            tid = Integer.parseInt(tileId);
        }
        catch(NumberFormatException ex){
            tileIDtxt.setText("");
            tileIDtxt.setText("Invalid Input");
            return;
        }

        try{
            pid = Integer.parseInt(paleId);
        }
        catch(NumberFormatException ex){
            paletteIDtxt.setText("");
            paletteIDtxt.setText("Invalid Input");
            return;
        }

        ZbeBaseSprite temp = new ZbeHeroSprite(count++,tid,pid,hF,vF,imageLocation);
        temp.setPosition(x, y);
        int squares = temp.getNumSquares();

        int diffX = modX - squares;
        int diffY = modY - squares;

        if( diffX < 1)
            diffX = 0;

        if( diffY < 1)
            diffY = 0;
        
        for( int i = diffX; i < modX + squares; ++i )
            for( int j = diffY; j < modY + squares; ++j)
                level[i][j] = temp;
        
        list.add(temp);

        repaint();
  */
    }

    public static int getTileSize(){
        return ZbePaint.tileSize;
    }

    public synchronized void deleteSprite(int x, int y){
        ZbeBaseSprite sprite = level[x][y];

        if( sprite == null)
            return;

        int squares = sprite.getNumSquares();
        int modX = x - squares;
        int modY = y - squares;

        if( modX < 1)
            modX = 0;
        if( modY < 1)
            modY = 0;


        int key = sprite.getSpriteNum();
        //System.out.printf("ModX = %d\nSquares = %d\nKey = %d\n",modX,squares,key);

        for( ZbeBaseSprite s : list )
            if( key == s.getSpriteNum() ){
                list.remove(s);
                break;
            }
                
        sprite.deleteImage();
        

        for(int i = modX; i < modX + squares; ++i )
            for(int j = modY; j < modY + squares; ++j){
                level[i][j] = null;
            }

        sprite = null;


        repaint();
    }

    public void moveSprite(){
    }

    public void mousePressed(MouseEvent e) {

        // If the user clicks on the Tile Preview Panel open the
        // window dialog
        /*if( e.getSource().equals(colorPanel) )
        {
            int result = imageChooser.showOpenDialog(this);
            if( result == JFileChooser.APPROVE_OPTION){
                imageLocation = imageChooser.getSelectedFile().getAbsolutePath();
                addPreviewImage(imageLocation);
            }
            return;
        }*/

        if( mouse.equals(MouseSelection.DELETE)){
            int x = e.getX()/tileSize;
            int y = e.getY()/tileSize;

            deleteSprite(x,y);
        }
        else if( mouse.equals(MouseSelection.PLACE)){
            int x = e.getX();
            int y = e.getY();

            if( level[x/tileSize][y/tileSize] == null)
                placeSprite(x,y);
        }
        else if( mouse.equals(MouseSelection.SETBG)){
            try{
                background = ImageIO.read( new File(imageLocation));
            }catch(Exception ex){ }
        }
        else if( mouse.equals(MouseSelection.UNSETBG)){
            background = null;
            this.setBackground(Color.WHITE);
        }

        repaint();
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
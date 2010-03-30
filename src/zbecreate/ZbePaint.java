package zbecreate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ZbePaint extends JApplet {

   JComboBox colorChoice, figureChoice;

    @Override
   public void init() {

      setBackground(Color.gray);
      getContentPane().setBackground(Color.gray);

      Display canvas = new Display();
      getContentPane().add(canvas,BorderLayout.CENTER);

      JPanel buttonBar = new JPanel();
      buttonBar.setBackground(Color.gray);
      getContentPane().add(buttonBar, BorderLayout.SOUTH);

      JPanel choiceBar = new JPanel();
      choiceBar.setBackground(Color.gray);
      getContentPane().add(choiceBar, BorderLayout.NORTH);

      JButton fill = new JButton("Set Background");
      fill.addActionListener(canvas);
      buttonBar.add(fill);

      JButton clear = new JButton("Clear");
      clear.addActionListener((ActionListener) canvas);
      buttonBar.add(clear);

      colorChoice = new JComboBox();
      colorChoice.addItem("Black");
      colorChoice.addItem("Red");
      colorChoice.addItem("Green");
      colorChoice.addItem("Blue");
      colorChoice.addItem("Cyan");
      colorChoice.addItem("Magenta");
      colorChoice.addItem("Yellow");
      colorChoice.addItem("White");
      colorChoice.setBackground(Color.white);
      choiceBar.add(colorChoice);

      figureChoice = new JComboBox();
      figureChoice.addItem("Curve");
      figureChoice.addItem("Straight Line");
      figureChoice.addItem("Rectangle");
      figureChoice.addItem("Oval");
      figureChoice.addItem("RoundRect");
      figureChoice.addItem("Filled Rectangle");
      figureChoice.addItem("Filled Oval");
      figureChoice.addItem("Filled RoundRect");
      figureChoice.setBackground(Color.white);
      choiceBar.add(figureChoice);

   }  // end init()

    @Override
   public Insets getInsets() {
      return new Insets(3,3,3,3);
   }

   private class Display extends JPanel
              implements MouseListener, MouseMotionListener, ActionListener {

      private final static int
                  BLACK = 0,
                  RED = 1,            // Some constants to make
                  GREEN = 2,          // the code more readable.
                  BLUE = 3,           // These numbers code for
                  CYAN = 4,           // the different drawing colors.
                  MAGENTA = 5,
                  YELLOW = 6,
                  WHITE = 7;

      private final static int
                 CURVE = 0,
                 LINE = 1,
                 RECT = 2,               // Some constants that code
                 OVAL = 3,               // for the different types of
                 ROUNDRECT = 4,          // figure the program can draw.
                 FILLED_RECT = 5,
                 FILLED_OVAL = 6,
                 FILLED_ROUNDRECT = 7;


      /* Some variables used for backing up the contents of the panel. */

      Image OSI;  // The off-screen image (created in checkOSI()).

      int widthOfOSI, heightOfOSI;  // Current width and height of OSI.  These
                                    // are checked against the size of the applet,
                                    // to detect any change in the panel's size.
                                    // If the size has changed, a new OSI is created.
                                    // The picture in the off-screen image is lost
                                    // when that happens.


      /* The following variables are used when the user is sketching a
         curve while dragging a mouse. */

      private int mouseX, mouseY;   // The location of the mouse.

      private int prevX, prevY;     // The previous location of the mouse.

      private int startX, startY;   // The starting position of the mouse.
                                    // (Not used for drawing curves.)

      private boolean dragging;     // This is set to true when the user is drawing.

      private int figure;    // What type of figure is being drawn.  This is
                             //    specified by the figureChoice menu.

      private Graphics dragGraphics;  // A graphics context for the off-screen image,
                                      // to be used while a drag is in progress.

      private Color dragColor;  // The color that is used for the figure that is
                                // being drawn.

      Display() {
             // Constructor.  When this component is first created, it is set to
             // listen for mouse events and mouse motion events from
             // itself.  The initial background color is white.
         addMouseListener(this);
         addMouseMotionListener(this);
         setBackground(Color.white);
      }


      private void drawFigure(Graphics g, int shape, int x1, int y1, int x2, int y2) {

         if (shape == LINE) {
               // For a line, just draw the line between the two points.
            g.drawLine(x1,y1,x2,y2);
            return;
         }
         int x, y;  // Top left corner of rectangle that contains the figure.
         int w, h;  // Width and height of rectangle that contains the figure.
         if (x1 >= x2) {  // x2 is left edge
            x = x2;
            w = x1 - x2;
         }
         else {          // x1 is left edge
            x = x1;
            w = x2 - x1;
         }
         if (y1 >= y2) {  // y2 is top edge
            y = y2;
            h = y1 - y2;
         }
         else {          // y1 is top edge.
            y = y1;
            h = y2 - y1;
         }
         switch (shape) {   // Draw the appropriate figure.
            case RECT:
               g.drawRect(x, y, w, h);
               break;
            case OVAL:
               g.drawOval(x, y, w, h);
               break;
            case ROUNDRECT:
               g.drawRoundRect(x, y, w, h, 20, 20);
               break;
            case FILLED_RECT:
               g.fillRect(x, y, w, h);
               break;
            case FILLED_OVAL:
               g.fillOval(x, y, w, h);
               break;
            case FILLED_ROUNDRECT:
               g.fillRoundRect(x, y, w, h, 20, 20);
               break;
         }
      }


      private void repaintRect(int x1, int y1, int x2, int y2) {
            // Call repaint on a rectangle that contains the points (x1,y1)
            // and (x2,y2).  (Add a 1-pixel border along right and bottom
            // edges to allow for the pen overhang when drawing a line.)
         int x, y;  // top left corner of rectangle that contains the figure
         int w, h;  // width and height of rectangle that contains the figure
         if (x2 >= x1) {  // x1 is left edge
            x = x1;
            w = x2 - x1;
         }
         else {          // x2 is left edge
            x = x2;
            w = x1 - x2;
         }
         if (y2 >= y1) {  // y1 is top edge
            y = y1;
            h = y2 - y1;
         }
         else {          // y2 is top edge.
            y = y2;
            h = y1 - y2;
         }
         repaint(x,y,w+1,h+1);
      }


      private void checkOSI() {
           // This method is responsible for creating the off-screen image.
           // It should be called before using the OSI.  It will make a new OSI if
           // the size of the panel changes.
         if (OSI == null || widthOfOSI != getSize().width || heightOfOSI != getSize().height) {
                // Create the OSI, or make a new one if panel size has changed.
            OSI = null;  // (If OSI already exists, this frees up the memory.)
            OSI = createImage(getSize().width, getSize().height);
            widthOfOSI = getSize().width;
            heightOfOSI = getSize().height;
            Graphics OSG = OSI.getGraphics();  // Graphics context for drawing to OSI.
            OSG.setColor(getBackground());
            OSG.fillRect(0, 0, widthOfOSI, heightOfOSI);
            OSG.dispose();
         }
      }


        @Override
      public void paintComponent(Graphics g) {
           // Copy the off-screen image to the screen,
           // after checking to make sure it exists.  Then,
           // if a shape other than CURVE is being drawn,
           // draw it on top of the image from the OSI.
         checkOSI();
         g.drawImage(OSI, 0, 0, this);
         if (dragging && figure != CURVE) {
            g.setColor(dragColor);
            drawFigure(g,figure,startX,startY,mouseX,mouseY);
         }
      }


      public void actionPerformed(ActionEvent evt) {
              // Respond when the user clicks on a button.  The
              // command must be either "Clear" or "Set Background".
         String command = evt.getActionCommand();
         checkOSI();
         if (command.equals("Set Background")) {
                // Set background color before clearing.
                // Change the selected color so it is different
                // from the background color.
            setBackground(getCurrentColor());
            if (colorChoice.getSelectedIndex() == BLACK)
               colorChoice.setSelectedIndex(WHITE);
            else
               colorChoice.setSelectedIndex(BLACK);
         }
         Graphics g = OSI.getGraphics();
         g.setColor(getBackground());
         g.fillRect(0,0,getSize().width,getSize().height);
         g.dispose();
         repaint();
      }


      private Color getCurrentColor() {
               // Check the colorChoice menu to find the currently
               // selected color, and return the appropriate color
               // object.
         int currentColor = colorChoice.getSelectedIndex();
         switch (currentColor) {
            case BLACK:
               return Color.black;
            case RED:
               return Color.red;
            case GREEN:
               return Color.green;
            case BLUE:
               return Color.blue;
            case CYAN:
               return Color.cyan;
            case MAGENTA:
               return Color.magenta;
            case YELLOW:
               return Color.yellow;
            default:
               return Color.white;
         }
      }


      public void mousePressed(MouseEvent evt) {
            
         if (dragging == true) 
             return;

         prevX = startX = evt.getX();  // Save mouse coordinates.
         prevY = startY = evt.getY();

         figure = figureChoice.getSelectedIndex();
         dragColor = getCurrentColor();
         dragGraphics = OSI.getGraphics();
         dragGraphics.setColor(dragColor);

         dragging = true;  // Start drawing.

      } // end mousePressed()


      public void mouseReleased(MouseEvent evt) {

          if (dragging == false)
             return;  // Nothing to do because the user isn't drawing.
          dragging = false;
          mouseX = evt.getX();
          mouseY = evt.getY();
          if (figure == CURVE) {
                 // A CURVE is drawn as a series of LINEs
              drawFigure(dragGraphics,LINE,prevX,prevY,mouseX,mouseY);
              repaintRect(prevX,prevY,mouseX,mouseY);
          }
          else if (figure == LINE) {
             repaintRect(startX,startY,prevX,prevY);
             if (mouseX != startX || mouseY != startY) {
                   // Draw the line only if it has non-zero length.
                drawFigure(dragGraphics,figure,startX,startY,mouseX,mouseY);
                repaintRect(startX,startY,mouseX,mouseY);
             }
          }
          else {
             repaintRect(startX,startY,prevX,prevY);
             if (mouseX != startX && mouseY != startY) {
                   // Draw the shape only if both its height
                   // and width are both non-zero.
                drawFigure(dragGraphics,figure,startX,startY,mouseX,mouseY);
                repaintRect(startX,startY,mouseX,mouseY);
             }
          }
          dragGraphics.dispose();
          dragGraphics = null;
      }


      public void mouseDragged(MouseEvent evt) {
              
          if (dragging == false)
             return;  

          mouseX = evt.getX();  
          mouseY = evt.getY();  

          if (figure == CURVE) {
             drawFigure(dragGraphics,LINE,prevX,prevY,mouseX,mouseY);
             repaintRect(prevX,prevY,mouseX,mouseY);
          }
          else {
             repaintRect(startX,startY,prevX,prevY);
             repaintRect(startX,startY,mouseX,mouseY);
          }

          prevX = mouseX; 
          prevY = mouseY;

      } 


      public void mouseEntered(MouseEvent evt) { }   
      public void mouseExited(MouseEvent evt) { }    
      public void mouseClicked(MouseEvent evt) { }   
      public void mouseMoved(MouseEvent evt) { }     
   }
} 
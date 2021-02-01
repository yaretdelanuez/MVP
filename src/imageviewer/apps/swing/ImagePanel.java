
package imageviewer.apps.swing;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;


public class ImagePanel extends JPanel implements ImageDisplay {
    private Image image;
    private Image imageNext;
    private ImageDisplay.Shift shift;
    private int x;
    
    
    public ImagePanel() {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }
    
    @Override
    public void paint(Graphics g) {
         if (this.image == null) return;
        BufferedImage image = load(this.image);
        g.drawImage(image, x, 0, this);
        if(x==0) return;
        BufferedImage imageNext = load(this.imageNext);
        int offset = x > 0 ? -(imageNext.getWidth() - x) : x + image.getWidth();
        g.drawImage(imageNext, offset, 0, null);
    }

    @Override
    public void display(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    public Image image() {
        return this.image;
    }

    private BufferedImage load(Image image) {
        try {
            return ImageIO.read(new File(image.getName()));
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
/*    
    private static class Box {
        final int x;
        final int y;
        final int width;
        final int height;
        
        private Box(int imageWidth, int imageHeight, int panelWidth, int panelHeight) {
            double imageRatio = imageWidth / imageHeight;
            double panelRatio = panelWidth / panelHeight;
            this.width = (int) (imageRatio >= panelRatio ? panelWidth : imageWidth * panelHeight / imageHeight);
            this.height = (int) (imageRatio <= panelRatio ? panelHeight : imageHeight * panelWidth / imageWidth);
            this.x = (int) ((panelWidth - this.width) / 2);
            this.y = (int) ((panelHeight - this.height) / 2);
        }
    }
   */ 
    @Override
    public void on(ImageDisplay.Shift shift) {
        this.shift = shift;
    }

  
 
    
    private class MouseHandler implements MouseListener,MouseMotionListener{
        private int initial; 

        @Override
        public void mouseClicked(MouseEvent event) {

        }

        @Override
        public void mousePressed(MouseEvent event) {
            this.initial = event.getX();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            if (Math.abs(event.getX() - initial) > getWidth() / 2) image = imageNext;
            x = 0;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            x = event.getX() - initial;
            repaint();
            if (x > 0) imageNext = shift.right();
            if (x < 0) imageNext = shift.left();
            
        }

        @Override
        public void mouseMoved(MouseEvent event) {
        }

    }  
}

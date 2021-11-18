//HIDE
//OUT canvas.png

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas
{
    private static Canvas canvas = new Canvas();

    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private BufferedImage background;
    private JFrame frame;
    private CanvasComponent component;

    private static final int MIN_SIZE = 100;
    private static final int MARGIN = 10;
    private static final int LOCATION_OFFSET = 120;

    class CanvasComponent extends JComponent
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(java.awt.Color.BLACK);
            if (background != null)
            {
                g.drawImage(background, 0, 0, null);
            }               
            for (Shape s : new ArrayList<Shape>(shapes))
            {
                Graphics2D g2 = (Graphics2D) g.create();
                s.paintShape(g2);
                g2.dispose();
            }
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(Window.width, Window.height);
        }
    }

    private Canvas()
    {
        component = new CanvasComponent();

        if (System.getProperty(" ") == null)
        {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(component);
            frame.pack();
            frame.setLocation(0,0);
            frame.setResizable(false);
            frame.setVisible(true);
        }
        else
        {
            final String SAVEFILE ="canvas.png";
            final Thread currentThread = Thread.currentThread();
            Thread watcherThread = new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            final int DELAY = 10;

                            while (currentThread.getState() != State.TERMINATED)
                            {
                                Thread.sleep(DELAY);
                            }
                            saveToDisk(SAVEFILE);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                };
            watcherThread.start();
        }
    }

    public static Canvas getInstance()
    {
        return canvas;
    }

    public void show(Shape s)
    {
        if (!shapes.contains(s))
        {
            shapes.add(s);
        }
    }

    public void repaint()
    {
        if (frame == null) return;
        Dimension dim = component.getPreferredSize();
        if (dim.getWidth() > component.getWidth()
                || dim.getHeight() > component.getHeight())
        {
            frame.pack();
        }
        else
        {
            frame.repaint();
        }
    }

    public void nextImage(Shape i){
        show(i);
        repaint();
    }

    /**
     * Pauses so that the user can see the picture before it is transformed.
     */
    public static void pause()
    {
        JFrame frame = getInstance().frame;
        if (frame == null) return;
        JOptionPane.showMessageDialog(frame, "Click Ok to continue");
    }

    /**
     * Takes a snapshot of the screen, fades it, and sets it as the background.
     */
    public static void snapshot()
    {
        Dimension dim = getInstance().component.getPreferredSize();
        java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, rect.width, rect.height);
        g.setColor(java.awt.Color.BLACK);
        getInstance().component.paintComponent(g);
        float factor = 0.9f;
        float base = 255f * (1f - factor);
        RescaleOp op = new RescaleOp(factor, base, null);
        BufferedImage filteredImage
           = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, filteredImage);
        getInstance().background = filteredImage;
        getInstance().component.repaint();
    }

    public void saveToDisk(String fileName)
    {
        Dimension dim = component.getPreferredSize();
    	java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
    	BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fill(rect);
        g.setColor(java.awt.Color.BLACK);
        component.paintComponent(g);
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        try
        {
            ImageIO.write(image, extension, new File(fileName));
        } 
        catch(IOException e)
        {
            System.err.println("Was unable to save the image to " + fileName);
        }
    	g.dispose();    	
    }
}

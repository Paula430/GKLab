import sun.java2d.pipe.RenderingEngine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Transforms2D extends JPanel  {

    private class Display extends JPanel {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);

            Graphics2D g2 = (Graphics2D)g;
            g2.translate(300,300);

            int whichTransform = transformSelect.getSelectedIndex();

            switch(whichTransform){
                case 0:
                    break;
                case 1:

                    g2.scale(0.5,0.5);
                    break;
                case 2:
                    g2.rotate(Math.toRadians(45));
                    break;
                case 3:
                case 7:
                    g2.scale(0.5,1);
                    g2.rotate(Math.toRadians(180));
                    break;
                case 4:
                    g2.shear(0,0.35);
                    break;
                case 5:
                    g2.scale(1,0.5);
                    g2.translate(0,-450);
                    break;
                case 6:
                    g2.shear(0,-0.5);
                    g2.rotate(Math.toRadians(90));
                    break;
                case 8:
                    g2.rotate(Math.toRadians(45));
                    g2.scale(1,0.5);
                    g2.translate(0,300);
                    break;
                case 9:
                    g2.translate(140, 0);
                    g2.shear(0, 0.25);
                    g2.rotate(Math.toRadians(180));
                    break;
            }

            int[] xpoints = new int[5];
            int[] ypoints = new int[5];

            for(int i = 1; i < 6; i++)
            {
                xpoints[i-1] = (int) (150*Math.cos((2*Math.PI/5)*i));
            }

            for(int i = 1; i < 6; i++)
            {
                ypoints[i-1] = (int) (150*Math.sin((2*Math.PI/5)*i));
            }

            Polygon pentagon = new Polygon(xpoints, ypoints, 5);
            g2.rotate(Math.toRadians(-17));
            g2.fillPolygon(pentagon);








           //Polygon pentagon=new Polygon(new int[] {-100,100,160,0,-160}, new int[]{150,150,-30,-150,-30},5);
            //g2.fillPolygon(pentagon);
        }
    }

    private Display display;
    private BufferedImage pic;
    private JComboBox<String> transformSelect;

    public Transforms2D() throws IOException {
        //pic = ImageIO.read(getClass().getClassLoader().getResource("shuttle.jpg"));
        display = new Display();
        display.setBackground(Color.GRAY);
        display.setPreferredSize(new Dimension(600,600));
        transformSelect = new JComboBox<String>();
        transformSelect.addItem("None");
        for (int i = 1; i < 10; i++) {
            transformSelect.addItem("No. " + i);
        }
        transformSelect.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.repaint();
            }
        });
        setLayout(new BorderLayout(3,3));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.GRAY,10));
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER));
        top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        top.add(new JLabel("Transform: "));
        top.add(transformSelect);
        add(display,BorderLayout.CENTER);
        add(top,BorderLayout.NORTH);
    }


    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("2D Transforms");
        window.setContentPane(new Transforms2D());
        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( (screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2 );
        window.setVisible(true);
    }
}

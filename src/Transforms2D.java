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
            g.setColor(Color.WHITE);


            Graphics2D g2 = (Graphics2D)g;
            //g2.translate(300,300);  // Moves (0,0) to the center of the display.


            int whichTransform = transformSelect.getSelectedIndex();

            // TODO Apply transforms here, depending on the value of whichTransform!
            Polygon pentagon2=new Polygon(new int[] {60,200,260,130,0},new int[]{260,260,120,0,120},5);



            switch(whichTransform){
                case 0:
                    g2.fillPolygon(pentagon2);
                    break;
                case 1:
                    g2.translate(170,150);
                    g2.fillPolygon(pentagon2);
                    break;
                case 2:
                    g2.rotate(Math.PI/8);
                    g2.translate(250,-30);
                    g2.scale(1.5,1.5);
                    g2.translate(-50, -50);
                    g2.fill(pentagon2);
                    break;
                case 3:
                case 7:
                    g2.translate(300,300);
                    g2.rotate(Math.toRadians(180));
                    g2.scale(1,1.2);
                    g2.translate(-120,-120);
                    g2.fill(pentagon2);
                    break;
                case 4:
                    g2.scale(1.5,1.5);
                    AffineTransform sat = AffineTransform.getTranslateInstance(50, 50);
                    sat.shear(.5, 0);
                    g2.transform(sat);
                    g2.fill(pentagon2);
                    break;
                case 5:
                    g2.scale(1.5,0.5  );
                    g2.translate(80,0);
                    g2.fill(pentagon2);
                    break;
                case 6:
                    g2.translate(300,300);
                    g2.rotate(Math.toRadians(90));
                    g2.translate(-200, -180);
                    g2.scale(1.5,1.5);
                    AffineTransform sat2 = AffineTransform.getTranslateInstance(50, 50);
                    sat2.shear(.5, 0);
                    g2.transform(sat2);
                    g2.translate(-100,-100);
                    g2.fill(pentagon2);
                    break;
                case 8:
                    g2.rotate(Math.PI/8);
                    g2.scale(2,1);
                    g2.translate(80,120);
                    g2.fill(pentagon2);
                    break;
                case 9:
                    g2.translate(300,300);
                    g2.rotate(Math.toRadians(180));
                    g2.scale(1.5,1.5);
                    g2.translate(-120,-120);
                    AffineTransform sat3 = AffineTransform.getTranslateInstance(50, 50);
                    sat3.shear(0, .5);
                    g2.transform(sat3);
                    g2.translate(-80,-100);
                    g2.fill(pentagon2);
                    break;
            }
        }
    }

    private Display display;
    private BufferedImage pic;
    private JComboBox<String> transformSelect;

    public Transforms2D() throws IOException {
        //pic = ImageIO.read(getClass().getClassLoader().getResource("shuttle.jpg"));
        display = new Display();
        display.setBackground(Color.BLACK);
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

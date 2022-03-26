import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class SubroutineHierarchy extends JPanel {

	public static void main(String[] args) {
		JFrame window = new JFrame("Subroutine Hierarchy");
		window.setContentPane(new SubroutineHierarchy());
		window.pack();
		window.setLocation(100, 60);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	//-------------------------- Create the world and implement the animation --------------

	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;

	private final static double X_LEFT = -4;
	private final static double X_RIGHT = 4;
	private final static double Y_BOTTOM = -3;
	private final static double Y_TOP = 3;

	private final static Color BACKGROUND = Color.WHITE;

	private float pixelSize;

	private int frameNumber = 0;

	// TODO:  Define any other necessary state variables.


	private void drawWorld(Graphics2D g2) {

		// TODO: Draw the content of the scene.
		rotatingPolygon(g2,1,1,-105,-15);
		rotatingPolygon(g2,1,1,55,-135);
		filledTriangle(g2,Color.blue,1,1,0,0);
		filledRect(g2,1,1,0,0);


		rotatingPolygon(g2,0.7,0.7,-193,150);
		rotatingPolygon(g2,0.7,0.7,-95,80);
		filledTriangle(g2,new Color(128,0,128),0.8,0.8,-5,2);
		filledRect(g2,0.6,0.6,-1,6);

		rotatingPolygon(g2,0.4,0.4,147,27);
		rotatingPolygon(g2,0.4,0.4,82,72);
		filledTriangle(g2, new Color(0,128,0),0.5,0.5,3,2.5);
		filledRect(g2,0.4,0.4,0.2,13.5);

	}

	private void updateFrame() {
		frameNumber++;
		// TODO: If other updates are needed for the next frame, do them here.

	}


	// TODO: Define methods for drawing objects in the scene.



	private void rotatingPolygon(Graphics2D g2, double scaleX, double scaleY,double translateX, double translateY) {
		AffineTransform saveTransform = g2.getTransform();
		int[] xpoints = new int[5];
		int[] ypoints = new int[5];

		for (int i = 1; i <= 5; i++) {
			xpoints[i - 1] = (int) (150 * Math.cos((2 * Math.PI / 5) * i));
		}

		for (int i = 1; i <= 5; i++) {
			ypoints[i - 1] = (int) (150 * Math.sin((2 * Math.PI / 5) * i));
		}

		Polygon pentagon = new Polygon(xpoints, ypoints, 5);
		Color saveColor = g2.getColor();

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(4));
		g2.rotate(Math.toRadians(18));
		g2.scale(0.015, 0.015);
		g2.translate(translateX, translateY);
		g2.scale(0.3,0.3);
		g2.scale(scaleX,scaleY);
		g2.rotate( Math.toRadians( frameNumber*0.75 ));

		for (int i = 1; i <= 5; i++) {
			g2.drawLine(xpoints[i - 1], ypoints[i - 1], 0, 0);
		}

		g2.draw(pentagon);
		g2.setColor(saveColor);
		g2.setTransform(saveTransform);

	}



	//------------------- Some methods for drawing basic shapes. ----------------
	
	private static void line(Graphics2D g2) { // Draws a line from (-0.5,0) to (0.5,0)
		g2.draw( new Line2D.Double( -0.5,0, 0.5,0) );
	}

	private static void rect(Graphics2D g2) { // Strokes a square, size = 1, center = (0,0)
		g2.draw(new Rectangle2D.Double(-0.5,-0.5,1,1));
	}

	private static void filledRect(Graphics2D g2,double scaleX, double scaleY,double translateX, double translateY) { // Fills a red rectangle, size = 1, center = (0,0)
		AffineTransform saveTransform = g2.getTransform();
		g2.setColor(Color.RED);
		g2.rotate(Math.toRadians(-18));
		g2.translate(0.35,-1.1);
		g2.scale(3.1,0.2);
		g2.translate(translateX, translateY);
		g2.scale(scaleX,scaleY);
		g2.fill(new Rectangle2D.Double(-0.5,-0.5,1,1));
		g2.setTransform(saveTransform);

	}

	private static void circle(Graphics2D g2) { // Strokes a circle, diameter = 1, center = (0,0)
		g2.draw(new Ellipse2D.Double(-0.5,-0.5,1,1));
	}

	private static void filledCircle(Graphics2D g2) { // Fills a circle, diameter = 1, center = (0,0)
		g2.draw(new Ellipse2D.Double(-0.5,-0.5,1,1));
	}
	
	private static void filledTriangle(Graphics2D g2,Color color,double scaleX, double scaleY,double translateX, double translateY) { // width = 1, height = 1, center of base is at (0,0);

		AffineTransform saveTransform = g2.getTransform();
		Path2D path = new Path2D.Double();
		g2.setColor(color);
		path.moveTo(-0.5,0);
		path.lineTo(0.5,0);
		path.lineTo(0,1);
		path.closePath();
		g2.scale(0.5,1.2);
		g2.translate(0,-1);
		g2.translate(-0.2,-1);
		g2.translate(translateX, translateY);
		g2.scale(scaleX,scaleY);
		g2.fill(path);
		g2.setTransform(saveTransform);

	}



	//--------------------------------- Implementation ------------------------------------

	private JPanel display;

	public SubroutineHierarchy() {
		display = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				applyLimits(g2, X_LEFT, X_RIGHT, Y_TOP, Y_BOTTOM, false);
				g2.setStroke( new BasicStroke(pixelSize) ); // set default line width to one pixel.
				drawWorld(g2);  // draw the world
			}
		};
		display.setPreferredSize( new Dimension(WIDTH,HEIGHT));
		display.setBackground( BACKGROUND );
		final Timer timer = new Timer(17,new ActionListener() { // about 60 frames per second
			public void actionPerformed(ActionEvent evt) {
				updateFrame();
				repaint();
			}
		});

		final JCheckBox animationCheck = new JCheckBox("Run Animation");
		animationCheck.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (animationCheck.isSelected()) {
					if ( ! timer.isRunning() )
						timer.start();
				}
				else {
					if ( timer.isRunning() )
						timer.stop();
				}
			}
		});
		JPanel top = new JPanel();
		top.add(animationCheck);
		setLayout(new BorderLayout(5,5));
		setBackground(Color.DARK_GRAY);
		setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY,4) );
		add(top,BorderLayout.NORTH);
		add(display,BorderLayout.CENTER);
	}

	private void applyLimits(Graphics2D g2, double xleft, double xright, 
			double ytop, double ybottom, boolean preserveAspect) {
		int width = display.getWidth();   // The width of the drawing area, in pixels.
		int height = display.getHeight(); // The height of the drawing area, in pixels.
		if (preserveAspect) {
			// Adjust the limits to match the aspect ratio of the drawing area.
			double displayAspect = Math.abs((double)height / width);
			double requestedAspect = Math.abs(( ybottom-ytop ) / ( xright-xleft ));
			if (displayAspect > requestedAspect) {
				double excess = (ybottom-ytop) * (displayAspect/requestedAspect - 1);
				ybottom += excess/2;
				ytop -= excess/2;
			}
			else if (displayAspect < requestedAspect) {
				double excess = (xright-xleft) * (requestedAspect/displayAspect - 1);
				xright += excess/2;
				xleft -= excess/2;
			}
		}
		double pixelWidth = Math.abs(( xright - xleft ) / width);
		double pixelHeight = Math.abs(( ybottom - ytop ) / height);
		pixelSize = (float)Math.min(pixelWidth,pixelHeight);
		g2.scale( width / (xright-xleft), height / (ybottom-ytop) );
		g2.translate( -xleft, -ytop );
	}

}
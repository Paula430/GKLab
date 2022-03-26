import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class SceneGraph extends JPanel {

	public static void main(String[] args) {
		JFrame window = new JFrame("Scene Graph 2D");
		window.setContentPane( new SceneGraph() );
		window.pack();
		window.setLocation(100,60);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}


	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;

	private final static double X_LEFT = -4;
	private final static double X_RIGHT = 4;
	private final static double Y_BOTTOM = -3;
	private final static double Y_TOP = 3;

	private final static Color BACKGROUND = Color.WHITE;

	private float pixelSize;

	private int frameNumber = 0;

	private CompoundObject world;

	// TODO: Define global variables to represent animated objects in the scene.

	private TransformedObject rotatingPolygon1;
	private TransformedObject rotatingPolygon2;
	private TransformedObject rotatingPolygon3;
	private TransformedObject rotatingPolygon4;
	private TransformedObject rotatingPolygon5;
	private TransformedObject rotatingPolygon6;
	private TransformedObject triangle1;
	private TransformedObject rectangle1;


	private void createWorld() {

		world = new CompoundObject();

		// TODO: Create objects and add them to the scene graph.
		//1

		rotatingPolygon1=new TransformedObject(polygon);
		rotatingPolygon1.setScale(0.26,0.26).setTranslation(-1.4,-0.45).setColor(Color.BLACK);
		world.add(rotatingPolygon1);

		rotatingPolygon2=new TransformedObject(polygon);
		rotatingPolygon2.setScale(0.26,0.26).setTranslation(1.4,-1.35).setColor(Color.BLACK);
		world.add(rotatingPolygon2);

		rectangle1=new TransformedObject(filledRect);
		rectangle1.setRotation(-18).setScale(3,0.2).setTranslation(0,-0.9).setColor(Color.RED);
		world.add(rectangle1);

		triangle1=new TransformedObject(filledTriangle);
		triangle1.setScale(0.5,1.4).setTranslation(0,-2.3).setColor(Color.BLUE);
		world.add(triangle1);

		//2

		rotatingPolygon3=new TransformedObject(polygon);
		rotatingPolygon3.setScale(0.2,0.2).setTranslation(-3.4,1.05).setColor(Color.BLACK);
		world.add(rotatingPolygon3);

		rotatingPolygon4=new TransformedObject(polygon);
		rotatingPolygon4.setScale(0.2,0.2).setTranslation(-1.83,0.55).setColor(Color.BLACK);
		world.add(rotatingPolygon4);

		rectangle1=new TransformedObject(filledRect);
		rectangle1.setRotation(-18).setScale(1.7,0.15).setTranslation(-2.6,0.8).setColor(Color.RED);
		world.add(rectangle1);

		triangle1=new TransformedObject(filledTriangle);
		triangle1.setScale(0.38,1.05).setTranslation(-2.6,-0.2).setColor(new Color(102,0,102));
		world.add(triangle1);

		//3

		rotatingPolygon5=new TransformedObject(polygon);
		rotatingPolygon5.setScale(0.13,0.13).setTranslation(1.4,1.2).setColor(Color.BLACK);
		world.add(rotatingPolygon5);

		rotatingPolygon6=new TransformedObject(polygon);
		rotatingPolygon6.setScale(0.13,0.13).setTranslation(2.63,0.8).setColor(Color.BLACK);
		world.add(rotatingPolygon6);

		rectangle1=new TransformedObject(filledRect);
		rectangle1.setRotation(-18).setScale(1.35,0.1).setTranslation(2,1).setColor(Color.RED);
		world.add(rectangle1);

		triangle1=new TransformedObject(filledTriangle);
		triangle1.setScale(0.25,0.7).setTranslation(2,0.3).setColor(new Color(0,110,0));
		world.add(triangle1);

	}

	public void updateFrame() {
		frameNumber++;

		// TODO: Update state in preparation for drawing the next frame.
		rotatingPolygon1.setRotation(frameNumber*0.75);
		rotatingPolygon2.setRotation(frameNumber*0.75);
		rotatingPolygon3.setRotation(frameNumber*0.75);
		rotatingPolygon4.setRotation(frameNumber*0.75);
		rotatingPolygon5.setRotation(frameNumber*0.75);
		rotatingPolygon6.setRotation(frameNumber*0.75);



	}

	private static abstract class SceneGraphNode {
		Color color;

		SceneGraphNode setColor(Color c) {
			this.color = c;
			return this;
		}
		final void draw(Graphics2D g) {
			Color saveColor = null;
			if (color != null) {
				saveColor = g.getColor();
				g.setColor(color);
			}
			doDraw(g);
			if (saveColor != null) {
				g.setColor(saveColor);
			}
		}
		abstract void doDraw(Graphics2D g);
	}

	private static class CompoundObject extends SceneGraphNode {
		ArrayList<SceneGraphNode> subobjects = new ArrayList<SceneGraphNode>();
		CompoundObject add(SceneGraphNode node) {
			subobjects.add(node);
			return this;
		}
		void doDraw(Graphics2D g) {
			for (SceneGraphNode node : subobjects)
				node.draw(g);
		}
	}

	private static class TransformedObject extends SceneGraphNode {
		SceneGraphNode object;
		double rotationInDegrees = 0;
		double scaleX = 1, scaleY = 1;
		double translateX = 0, translateY = 0;
		TransformedObject(SceneGraphNode object) {
			this.object = object;
		}
		TransformedObject setRotation(double degrees) {
			rotationInDegrees = degrees;
			return this;
		}
		TransformedObject setTranslation(double dx, double dy) {
			translateX = dx;
			translateY = dy;
			return this;
		}
		TransformedObject setScale(double sx, double sy) {
			scaleX = sx;
			scaleY = sy;
			return this;
		}
		void doDraw(Graphics2D g) {
			AffineTransform savedTransform = g.getTransform();
			if (translateX != 0 || translateY != 0)
				g.translate(translateX,translateY);
			if (rotationInDegrees != 0)
				g.rotate( rotationInDegrees/180.0 * Math.PI);
			if (scaleX != 1 || scaleY != 1)
				g.scale(scaleX,scaleY);
			object.draw(g);
			g.setTransform(savedTransform);
		}
	}

	private static SceneGraphNode line = new SceneGraphNode() { 
		void doDraw(Graphics2D g) {  g.draw( new Line2D.Double( -0.5,0, 0.5,0) ); }
	};

	private static SceneGraphNode rect = new SceneGraphNode() {
		void doDraw(Graphics2D g) {  g.draw(new Rectangle2D.Double(-0.5,-0.5,1,1)); }
	};

	private static SceneGraphNode filledRect = new SceneGraphNode() {
		void doDraw(Graphics2D g) {  g.fill(new Rectangle2D.Double(-0.5,-0.5,1,1)); }
	};

	private static SceneGraphNode circle = new SceneGraphNode() {
		void doDraw(Graphics2D g) {  g.draw(new Ellipse2D.Double(-0.5,-0.5,1,1)); }
	};

	private static SceneGraphNode filledCircle = new SceneGraphNode() {
		void doDraw(Graphics2D g) {  g.fill(new Ellipse2D.Double(-0.5,-0.5,1,1)); }
	};

	private static SceneGraphNode filledTriangle = new SceneGraphNode() {
		void doDraw(Graphics2D g) {  // width = 1, height = 1, center of base is at (0,0);
			Path2D path = new Path2D.Double();
			path.moveTo(-0.5, 0);
			path.lineTo(0.5, 0);
			path.lineTo(0, 1);
			path.closePath();
			g.fill(path);
		}
	};

	private static SceneGraphNode polygon= new SceneGraphNode() {
		@Override
		void doDraw(Graphics2D g) {

			int[] xpoints = new int[5];
			int[] ypoints = new int[5];

			for (int i = 1; i <= 5; i++) {
				xpoints[i - 1] = (int) (150 * Math.cos((2 * Math.PI / 5) * i));
			}

			for (int i = 1; i <= 5; i++) {
				ypoints[i - 1] = (int) (150 * Math.sin((2 * Math.PI / 5) * i));
			}

			Polygon pentagon = new Polygon(xpoints, ypoints, 5);
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(4));
			g.rotate(Math.toRadians(18));
			g.scale(0.015,0.015);


			for (int i = 1; i <= 5; i++) {
				g.drawLine(xpoints[i-1], ypoints[i-1],0, 0);
			}

			g.draw(pentagon);

		}


	};


	//--------------------------------- Implementation ------------------------------------

	private JPanel display;

	public SceneGraph() {
		display = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				applyLimits(g2, X_LEFT, X_RIGHT, Y_TOP, Y_BOTTOM, false);
				g2.setStroke( new BasicStroke(pixelSize) ); // set default line width to one pixel.
				world.draw(g2);
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
		createWorld();
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
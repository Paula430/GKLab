import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.gl2.GLUT;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_TRIANGLE_FAN;
/**
 * CPSC 424, Fall 2015, Lab 4:  Some objects in 3D.  The arrow keys
 * can be used to rotate the object.  The number keys 1 through 6
 * select the object.  The space bar toggles the use of anaglyph
 * stereo.
 */
public class Lab5 extends GLJPanel implements GLEventListener, KeyListener{

    /**
     * A main routine to create and show a window that contains a
     * panel of type Lab5.  The program ends when the user closes the
     * window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("lab5");
        Lab5 panel = new Lab5();
        window.setContentPane(panel);
        window.pack();
        window.setResizable(false);
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    /**
     * Constructor for class Lab4.
     */
    public Lab5() {
        super( new GLCapabilities(null) );
        setPreferredSize( new Dimension(700,700) );
        addGLEventListener(this);
        addKeyListener(this);
    }


    //------------------- TODO: Complete this section! ---------------------

    private int objectNumber = 2;

    private boolean useAnaglyph = false;

    private int rotateX = 0;    // Rotations of the cube about the axes.
    private int rotateY = 0;   //   (Controlled by arrow, PageUp, PageDown keys;
    private int rotateZ = 0;    //    Home key sets all rotations to 0.)
    private GLUT glut = new GLUT(); // An object for drawing GLUT shapes.




    private void trojkat(float j, float k, float n, GL2 gl2) {

        gl2.glBegin(GL_TRIANGLE_FAN);

        gl2.glVertex3d((Math.cos(j * 2 * Math.PI / n)), (Math.sin(j * 2 * Math.PI / n)), 1.5);
        gl2.glVertex3d((Math.cos(k * 2 * Math.PI / n)), (Math.sin(k * 2 * Math.PI / n)), 1.5);
        gl2.glVertex3d(0, 0, 0);

        gl2.glEnd();
    }

    private void piramida(float size, float n, GL2 gl2) {

        gl2.glScalef(size, size, size);
        gl2.glRotatef(0, 0, 0, 0);
        gl2.glTranslatef(0, 0, 0);
        gl2.glColor3f(0.4f, 0.4f, 0.4f);

        for (int i = 0; i < n; i++) {
            trojkat(i - 1, i, n, gl2);
        }

    }

    private void spirala(GL2 gl2,int n) {

        gl2.glColor3f(0.4f, 0.4f, 0.4f);
        gl2.glBegin(GL2.GL_LINE_STRIP);
        double z,y;
        var angle=2*Math.PI /36;

        for(float i=1;i<=n*36;i++)
        {
            z=Math.cos(angle*i);
            y=Math.sin(angle*i);
            gl2.glVertex3d(i/36,y*0.02*i,z*0.02*i);

         }

        gl2.glEnd();

    }

    /**
     * The method that draws the current object, with its modeling transformation.
     */

    private void draw(GL2 gl2) {

        gl2.glRotatef(rotateZ,0,0,1);   // Apply rotations to complete object.
        gl2.glRotatef(rotateY,0,1,0);
        gl2.glRotatef(rotateX,1,0,0);

        // TODO: Draw the currently selected object, number 1, 2, 3, 4, 5, or 6.
        // (Objects should lie in the cube with x, y, and z coordinates in the
        // range -5 to 5.)

        int N=5;

        if(objectNumber==1){
            piramida(3,N,gl2);
        }else if(objectNumber==2) {
            spirala(gl2,5);
        }


    }

    //-------------------- Draw the Scene  -------------------------

    /**
     * The display method is called when the panel needs to be drawn.
     * It's called when the window opens and it is called by the keyPressed
     * method when the user hits a key that modifies the scene.
     */
    public void display(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();

        if (useAnaglyph) {
            gl2.glDisable(GL2.GL_COLOR_MATERIAL);
            gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{1,1,1,1}, 0);
        }
        else {
            gl2.glEnable(GL2.GL_COLOR_MATERIAL);  // in non-anaglyph mode, glColor* is respected
        }
        gl2.glNormal3f(0,0,1); // (Make sure normal vector is correct for object 1.)

        gl2.glClearColor( 0, 0, 0, 1 ); // Background color (black).
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );


        if (useAnaglyph == false) {
            gl2.glLoadIdentity();
            gl2.glTranslated(0,0,-15);
            draw(gl2);
        }
        else {
            gl2.glLoadIdentity();
            gl2.glColorMask(true, false, false, true);
            gl2.glRotatef(4,0,1,0);
            gl2.glTranslated(1,0,-15);
            draw(gl2);
            gl2.glColorMask(true, false, false, true);
            gl2.glClear(GL2.GL_DEPTH_BUFFER_BIT);
            gl2.glLoadIdentity();
            gl2.glRotatef(-4,0,1,0);
            gl2.glTranslated(-1,0,-15);
            gl2.glColorMask(false, true, true, true);
            draw(gl2);
            gl2.glColorMask(true, true, true, true);
        }
    }
    
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glFrustum(-3.5, 3.5, -3.5, 3.5, 5, 25);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glLightfv(GL2.GL_LIGHT0,GL2.GL_DIFFUSE,new float[] {0.7f,0.7f,0.7f},0);
        gl2.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glLineWidth(3);  // make wide lines for the stellated dodecahedron.
    }

    public void dispose(GLAutoDrawable drawable) {
        // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // called when user resizes the window
    }

    // ----------------  Methods from the KeyListener interface --------------

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        boolean repaint = true;
        if ( key == KeyEvent.VK_LEFT )
            rotateY -= 6;
        else if ( key == KeyEvent.VK_RIGHT )
            rotateY += 6;
        else if ( key == KeyEvent.VK_DOWN)
            rotateX += 6;
        else if ( key == KeyEvent.VK_UP )
            rotateX -= 6;
        else if ( key == KeyEvent.VK_PAGE_UP )
            rotateZ += 6;
        else if ( key == KeyEvent.VK_PAGE_DOWN )
            rotateZ -= 6;
        else if ( key == KeyEvent.VK_HOME )
            rotateX = rotateY = rotateZ = 0;
        else if (key == KeyEvent.VK_1)
            objectNumber = 1;
        else if (key == KeyEvent.VK_2)
            objectNumber = 2;
        else if (key == KeyEvent.VK_SPACE)
            useAnaglyph = ! useAnaglyph;
        else
            repaint = false;
        if (repaint)
            repaint();
    }

    public void keyReleased(KeyEvent evt) {
    }

    public void keyTyped(KeyEvent evt) {
    }

}
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author  
 * Juan Cairo GonzÃ¡lez Resendiz
 *
 */
public class Light implements GLEventListener {

    static GLU glu = new GLU();
    static GLUT glut = new GLUT();

    public static void main(String[] args) {
        Frame frame = new Frame("Light.java");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Light());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
// Run this on another thread than the AWT event queue to
// make sure the call to Animator.stop() completes before
// exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

// Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    @Override

    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        //
        float light_position[] = {1, 1, 1, 0.0f};
        float white_light[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float lmodel_ambient[] = {0.1f, 0.1f, 0.1f, 1.0f};
        float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
        //GL2.GL_FLOAT mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f };
        float mat_shininess[] = {50.0f};

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, white_light, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, white_light, 0);
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 3);

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mat_shininess, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0, 0, 0, 0);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        glut.glutSolidSphere(1.0, 20, 16);
        gl.glFlush();
    }

//@Override
    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h) {
        GL2 gl = glad.getGL().getGL2();
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (w <= (h * 2)) //
        {
            gl.glOrtho(-6.0, 6.0, -3.0 * ((float) h * 2) / (float) w, //
                    3.0 * ((float) h * 2) / (float) w, -10.0, 10.0);
        } else {
            gl.glOrtho(-6.0 * (float) w / ((float) h * 2), //
                    6.0 * (float) w / ((float) h * 2), -3.0, 3.0, -10.0, 10.0);
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 @author
 *  
 * Juan Cairo GonzÃ¡lez Resendiz
 *
 */
public class Pelotita implements GLEventListener {

    private static float y1 = 0;
    private static float y2 = 0;
    private float cx = 0, cy = 0;
    private float ctx, cty;

    public static void main(String[] args) {

        Frame frame = new Frame("Pelotita.java");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Pelotita());
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
        randomDirection();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(GLAutoDrawable glad) {
        final GL2 gl = glad.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        ball(glad);

        cx += ctx;
        cy += cty;

    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    private void randomDirection() {
        Random random = new Random();
        int direction = random.nextInt(3);

        if (direction == 0) {
            ctx = 0.01f;
            cty = 0.01f;
        }

        if (direction == 1) {
            ctx = -0.01f;
            cty = 0.01f;
        }

        if (direction == 2) {
            ctx = -0.01f;
            cty = -0.01f;
        }

        if (direction == 3) {
            ctx = 0.01f;
            cty = -0.01f;
        }

    }

    public void ball(GLAutoDrawable glad) {
        final GL2 gl = glad.getGL().getGL2();

        checkCollision();

        float radius = 0.08f;

        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i <= 180; i++) {
            double angle = 2 * ((Math.PI * i) / 180);
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x + cx, y + cy);
            if (cx > 0.91) {
                gl.glColor3f(1, 0, 0);

            }
            if (cx < -0.91) {
                gl.glColor3f(1, 1, 0);

            }

            if (cy >= 1) {
                gl.glColor3f(1, 0, 1);

            }
            if (cy <= -1) {
                gl.glColor3f(0, 1, 1);
            }
        }
        gl.glEnd();
    }

    private void checkCollision() {

        if (cx > 0.91) {
            ctx *= -1;

        }

        if (cx < -0.91) {
            ctx *= -1;

        }

        if (cx <= -0.9 && ((cy >= -0.1f + y1) && (cy <= 0.1f + y1))) {
            ctx *= -1;
        }

        if (cx >= 0.9 && ((cy >= -0.1f + y2) && (cy <= 0.1f + y2))) {
            ctx *= -1;
        }

        if (cy >= 1 || cy <= -1) {
            cty *= -1;
        }
    }

}

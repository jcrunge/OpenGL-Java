import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author  DaliaOropeza
 *          EstrellaBelen
 *          Jcrunge CairoGonzalez
 */
public class CurvasBezierCora implements GLEventListener {

    static final float[]

             puntosctrlv1 =  {0.0f, 4.0f, 0.0f,
                              -5.0f, 8.0f, 0.0f,
                               0.0f, -8.0f, 0.0f,
                               0.0f, -5.0f, 0.0f,
                               5.0f, 8.0f, 0.0f,
                              0.0f, 4.0f, 0.0f};
    static GL2 gl;

    public static void main(String[] args) {
        Frame frame = new Frame("CurvasBezier.java");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new CurvasBezierCora());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            // Run this on another thread than the AWT event queue to
            // make sure the call to Animator.stop() completes before
            // exiting
            new Thread (new Runnable() {
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
        gl = glad.getGL().getGL2();
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
        gl.glLoadIdentity();
        gl.glMap1f(GL2.GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 6, puntosctrlv1,0);
        gl.glEnable(GL2.GL_MAP1_VERTEX_3);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-10.0, 10.0, -10.0, 10.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    @Override
    public void dispose(GLAutoDrawable glad) {
    }
    @Override
    public void display(GLAutoDrawable drawable) {
        int i;
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLineWidth(3.0f);
        gl.glColor3f(0,0,0);
         //Curva de Bezier
        /*gl.glBegin(GL2.GL_LINE_STRIP);
        for(i=0;i<=30;i++){
            gl.glEvalCoord1f(i/30.0f);
        }
        gl.glEnd();*/
        gl.glMapGrid1f(30, 0.0f, 1.0f);
        /*gl.glEvalMesh1(GL2.GL_POINT, 1, 30);*/
        gl.glEvalMesh1(GL2.GL_LINE, 0, 30);
        //Puntos de control
        gl.glPointSize(6);
        gl.glColor3d(1.0, 0.75, 0.50);
        gl.glBegin(GL2.GL_POINTS);
        for(i=0;i<12;i+=3){
            /*gl.glVertex3f(puntosctrlv1[i], puntosctrlv1[i+1],
            puntosctrlv1[i+2]);*/
            gl.glVertex3fv(puntosctrlv1, i);
        }
        gl.glEnd();
        gl.glFlush();
    }
    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }
}

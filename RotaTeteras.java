import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Estrella Belen Cortés Delgado
 *         Juan Cairo González Resendiz
 *        
 */

public class RotaTeteras implements GLEventListener {

static GL2 gl;
static GLUT glut;
static float[] ambiente = {0, 0, 0, 0};
static float[] difusa = {1, 1, 1, 1};
static float[] pocision = {0, 3, 3, 0};
float centrar=0, x=0;


   public static void main(String[] args) {
     Frame frame = new Frame("RotaTeteras.java");

        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new RotaTeteras());
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
    gl = glad.getGL().getGL2();
glut = new GLUT();
//gl.glClearColor(1, 1, 1, 0);
gl.glMatrixMode(GL2.GL_PROJECTION);
gl.glLoadIdentity();
gl.glOrtho(0.0, 20.0, 0.0, 20.0, -10.0, 10.0);
gl.glMatrixMode(GL2.GL_MODELVIEW);
gl.glLoadIdentity();
gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambiente, 0);
gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, difusa, 0);
gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, pocision, 0);
gl.glFrontFace(GL2.GL_CW);
gl.glEnable(GL2.GL_LIGHTING);
gl.glEnable(GL2.GL_LIGHT0);
gl.glEnable(GL2.GL_NORMALIZE);
gl.glEnable(GL2.GL_DEPTH_TEST);
gl.glDepthFunc(GL2.GL_LESS);



}
@Override
public void dispose(GLAutoDrawable glad) {
}
@Override
public void display(GLAutoDrawable glad) {
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
if(centrar==0)
{
    gl.glTranslatef(10.0f, 10.0f, 0.0f);
gl.glPushMatrix();
    centrar++;
}
// Cobre
dibujaTetera(-5.0f, 0.0f,
0.329412f, 0.223529f, 0.027451f,
0.780392f, 0.568627f, 0.113725f,
0.992157f, 0.941176f, 0.807843f,
0.21794872f);

// Plata
dibujaTetera(0.0f, 6.0f,
0.19225f, 0.019225f, 0.19225f,
0.50754f, 0.50754f, 0.50754f,
0.508273f, 0.508273f, 0.508273f,
0.4f);

// Plástico Rojo
dibujaTetera(5.0f, 0.0f,
0.0f, 0.0f, 0.0f,
0.5f, 0.0f, 0.0f,
0.7f, 0.6f, 0.6f,
0.25f);
// Plástico Verde
gl.glPushMatrix();
dibujaTetera(0.0f, -6.0f,
0.0f, 0.0f, 0.0f,
0.1f, 0.35f, 0.1f,
0.45f, 0.55f, 0.45f,
0.25f);

if(x<=360)
{
//rotar verticalmente
gl.glRotatef(5.0f,1.0f,0.0f,0.0f);
 // (grados, vertical, horizontal, circulo)
gl.glPushMatrix();
gl.glPopMatrix();
gl.glPopAttrib();
}
if(x>360 &&x <=720)
{
//rotar Horizontalmente
gl.glRotatef(5.0f,0.0f,1.0f,0.0f);
 // (grados, vertical, horizontal, circulo)
gl.glPushMatrix();
gl.glPopMatrix();
gl.glPopAttrib();
}
if(x==720)
x=0;
x=x+5;

gl.glFlush();
}
@Override
public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
}

void dibujaTetera(float x, float y,float ambr, float ambv, float amba,
float difr, float difv, float difa,float espr, float espv, float espa,
float brillo){

float[] mat={0.0f, 0.0f, 0.0f, 1.0f};
gl.glPushMatrix();
gl.glTranslatef(x, y, 0.0f);
//Ambiente
mat[0] = ambr;
mat[1] = ambv;
mat[2] = amba;
gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat, 0);
//Difuso
mat[0] = difr;
mat[1] = difv;
mat[2] = difa;
gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat, 0);
//Especular
mat[0] = espr;
mat[1] = espv;
mat[2] = espa;
gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat, 0);
gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, brillo * 128.0f);
glut.glutSolidTeapot(2.5);
gl.glPopMatrix();
}

}

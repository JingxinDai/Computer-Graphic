/**
 * A model for the palm of a hand as a sphere scaled in one direction.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 * 
 */

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

public class Palm extends Circular implements Displayable {

	/**
	 * The OpenGL handle to the display list which contains all the components which
	 * comprise this cylinder.
	 */
	private int callListHandle;
	private float scale_x;
	private float scale_y;
	private float scale_z;

	/**
	 * Instantiates this object with the specified radius and OpenGL utility toolkit
	 * object for drawing the sphere.
	 * 
	 * @param radius The radius of this object.
	 * @param glut   The OpenGL utility toolkit object for drawing the sphere.
	 */
	public Palm(final double radius,final float scale_x, final float scale_y, final float scale_z ,final GLUT glut) {
		super(radius, glut);
		this.scale_x = scale_x;
		this.scale_y = scale_y;
		this.scale_z = scale_z;
	}
	public Palm(final double radius,final float scale_x, final float scale_y, final float scale_z,final GLU glu) {
		super(radius, glu);
		this.scale_x = scale_x;
		this.scale_y = scale_y;
		this.scale_z = scale_z;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param gl {@inheritDoc}
	 * @see edu.bu.cs.cs480.Displayable#draw(javax.media.opengl.GL)
	 */
	@Override
	public void draw(GL2 gl) {
		gl.glCallList(this.callListHandle);
	}

	/**
	 * Defines the OpenGL call list which draws a scaled sphere.
	 * 
	 * @param gl {@inheritDoc}
	 * 
	 * @see edu.bu.cs.cs480.Displayable#initialize(javax.media.opengl.GL)
	 */
	@Override
	public void initialize(final GL2 gl) {
		
		this.callListHandle = gl.glGenLists(1);

		// create an ellipsoid for the palm by scaling a sphere
		gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
		gl.glPushMatrix();
		// position this so that the sphere is drawn above the x-y plane, not at
		// the origin
		//gl.glTranslated(0, 0, 0);
		gl.glScalef(this.scale_x,this.scale_y,this.scale_z);
		//this.glut().glutSolidSphere(this.radius(), 36, 18);
		this.glu().gluSphere(glu().gluNewQuadric(), this.radius(), 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

}

/**
 * Circular.java - a circular object
 */
/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA2
 * @due 10/15/2019
 */
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

/**
 * A circular object.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class Circular {
	/** The OpenGL utility toolkit object to use to draw this object. */
	private final GLUT glut;
	/** The GLU is for texture mapping*/
	private final GLU glu;
	/** The radius of this object. */
	private final double radius;

	public Circular(final double radius, final GLUT glut) {
		this.radius = radius;
		this.glut = glut;
		this.glu = null;
	}
	/**
	 * Instantiates this object with the specified radius.
	 * 
	 * @param radius The radius of this object.
	 */
	public Circular(final double radius,final GLU glu) {
		this.radius = radius;
		this.glut = null;
		this.glu = glu;
	}

	/**
	 * Gets the OpenGL utility toolkit object.
	 * 
	 * @return The OpenGL utility toolkit object.
	 */
	protected GLUT glut() {
		return this.glut;
	}

	protected GLU glu() {
		return this.glu;
	}

	/**
	 * Gets the radius of this object.
	 * 
	 * @return The radius of this object.
	 */
	protected double radius() {
		return this.radius;
	}
}

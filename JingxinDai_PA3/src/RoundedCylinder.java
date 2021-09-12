/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA2
 * @due 10/15/2019
 * @brief A solid cylinder with a rounded top with texture mapping function
 */
/**
 * A solid cylinder with a rounded top.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
import javax.media.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.Texture;
import javax.media.opengl.GLException;
import java.io.IOException;

public class RoundedCylinder extends Circular implements Displayable {
	/**
	 * The default number of slices to use when drawing the cylinder and the sphere.
	 */
	public static final int DEFAULT_SLICES = 36;
	/**
	 * The default number of stacks to use when drawing the cylinder and the sphere.
	 */
	public static final int DEFAULT_STACKS = 28;

	/**
	 * The OpenGL handle to the display list which contains all the components which
	 * comprise this cylinder.
	 */
	private int callListHandle;
	/** The height of this cylinder. */
	private final double height;

	/**
	 * Instantiates this object with the specified radius and height of the
	 * cylinder, and the GLUT object to use for drawing the cylinder and the sphere
	 * at the top.
	 * 
	 * @param radius The radius of this cylinder.
	 * @param height The height of this cylinder.
	 * @param glut   The OpenGL utility toolkit object to use to draw the cylinder
	 *               and the sphere at the top.
	 */
	public RoundedCylinder(final double radius, final double height, final GLUT glut) {
		super(radius, glut);
		this.height = height;
	}
	
	public RoundedCylinder(final double radius, final double height, final GLU glu) {
		super(radius, glu);
		this.height = height;
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @param gl {@inheritDoc}
	 * @see edu.bu.cs.cs480.Displayable#draw(javax.media.opengl.GL)
	 */
	@Override
	public void draw(final GL2 gl) {
		gl.glCallList(this.callListHandle);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param gl {@inheritDoc}
	 * @see edu.bu.cs.cs480.Displayable#initialize(javax.media.opengl.GL)
	 */
	@Override
	public void initialize(final GL2 gl) {
		
		Texture texture = null;
		try {
	         texture = TextureIO.newTexture(getClass().getClassLoader().getResource("texture2.jpg"), false, ".jpg");
	    } catch (GLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		this.callListHandle = gl.glGenLists(1);

		gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
		
		GLUquadric gluQuadric = glu().gluNewQuadric();
		glu().gluQuadricTexture(gluQuadric, true);
	    glu().gluQuadricNormals(gluQuadric, GLU.GLU_SMOOTH);
	    
	    texture.enable(gl);
	    texture.bind(gl);
		//this.glut().glutSolidCylinder(this.radius(), this.height, DEFAULT_SLICES, DEFAULT_STACKS);
		this.glu().gluCylinder(gluQuadric,this.radius(),this.radius(),this.height, DEFAULT_SLICES, DEFAULT_STACKS);
		gl.glPushMatrix();
		gl.glTranslated(0, 0, this.height);
		//this.glut().glutSolidSphere(this.radius(), DEFAULT_SLICES, DEFAULT_STACKS);
		this.glu().gluSphere(gluQuadric, this.radius(), DEFAULT_SLICES, DEFAULT_STACKS);
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(0, 0, 0);
		
		this.glu().gluSphere(gluQuadric, this.radius(), DEFAULT_SLICES, DEFAULT_STACKS);
		
		gl.glPopMatrix();
		texture.disable(gl);
		gl.glEndList();
	}
}

/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA2
 * @due 10/15/2019
 * @brief A solid Ellipsoid with texture mapping function
 */
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Ellipsoid extends Circular implements Displayable {
	
	private int callListHandle;
	
	public int get_handle() {
		return this.callListHandle;
	}
	
	public Ellipsoid(double radius, GLUT glut) {
		super(radius, glut);
	}
	public Ellipsoid(double radius, GLU glu) {
		super(radius,  glu);
	} 

	@Override
	public void draw(GL2 gl) {
		gl.glCallList(this.callListHandle);
		
	}

	@Override
	public void initialize(GL2 gl) {
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
		texture.enable(gl);
	    texture.bind(gl);
		gl.glPushMatrix();
		GLUquadric gluQuadric = glu().gluNewQuadric();
		glu().gluQuadricTexture(gluQuadric, true);
	    glu().gluQuadricNormals(gluQuadric, GLU.GLU_SMOOTH);
		// gl.glTranslated(0, -1, 0);
		//this.glut().glutSolidSphere(this.radius(), 36, 18);
		this.glu().gluSphere(gluQuadric, this.radius(), 36, 18);
		gl.glPopMatrix();
		texture.disable(gl);
		gl.glEndList();
	}
	
}

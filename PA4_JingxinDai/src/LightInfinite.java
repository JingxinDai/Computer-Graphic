//****************************************************************************
//       Infinite light source class
//****************************************************************************
// History :
//   Nov 6, 2014 Created by Stan Sclaroff
//
import java.util.Random;

public class LightInfinite extends Light
{
	private Random rnd=new Random();

	
	public LightInfinite(ColorType _c, Point3D _direction)
	{
		color = new ColorType(_c);
		direction = new Point3D(_direction);
		position = new Point3D(0, 0, 0); // Not used in this class
	}
	
	// apply this light source to the vertex / normal, given material
	// return resulting color value
	// v: viewing vector
	// n: face normal
	public ColorType applyLight(Material mat, Point3D v, Point3D n){
		ColorType res = new ColorType();
		
		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		double dot = direction.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse )
			{
				res.r = (float)(dot*mat.kd.r*color.r);
				res.g = (float)(dot*mat.kd.g*color.g);
				res.b = (float)(dot*mat.kd.b*color.b);
			}
			// specular component
			if(mat.specular)
			{
				Point3D r = direction.reflect(n);
				dot = r.dotProduct(v);
				if(dot>0.0)
				{
					res.r += (float)(color.r*mat.ks.r*Math.pow(dot,mat.ns));
					res.g += (float)(color.g*mat.ks.g*Math.pow(dot,mat.ns));
					res.b += (float)(color.b*mat.ks.b*Math.pow(dot,mat.ns));
				}
			}
			// clamp so that allowable maximum illumination level is not exceeded
			res.clamp();
		}
		return(res);
	}
	
	public void applyLight(ColorType c, Material mat, Point3D v, Point3D n, boolean diffuseKey, boolean specularKey){
		
		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		double dot = direction.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse && diffuseKey)
			{
				c.r += (float)(dot*mat.kd.r*color.r);
				c.g += (float)(dot*mat.kd.g*color.g);
				c.b += (float)(dot*mat.kd.b*color.b);
			}
			// specular component
			if(mat.specular && specularKey)
			{
				Point3D r = direction.reflect(n);
				dot = r.dotProduct(v);
				if(dot>0.0)
				{
					c.r += (float)(color.r*mat.ks.r*Math.pow(dot,mat.ns));
					c.g += (float)(color.g*mat.ks.g*Math.pow(dot,mat.ns));
					c.b += (float)(color.b*mat.ks.b*Math.pow(dot,mat.ns));
				}
			}
			// clamp so that allowable maximum illumination level is not exceeded
			c.clamp();
		}

	}

}

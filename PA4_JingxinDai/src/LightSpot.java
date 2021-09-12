/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The point light class, with  radial and angle attenuation
 * */
import java.util.Random;

public class LightSpot extends Light{
	
	private Random rnd=new Random();
	private int theta =30;
	private float cos_theta = (float) Math.cos(((float)theta/360)*2*Math.PI);
	private float r_a0 = 0.000002f;
	private float r_a1 = 0.000001f;
	private float r_a2 = 0.000002f;
	
	private float a_l = 2f;
	
	public LightSpot(ColorType _c,  Point3D _direction,Point3D _position)
	{
		color = new ColorType(_c);
		direction = _direction;
		position = _position; 
	}
	
	// apply this light source to the vertex / normal, given material
	// return resulting color value
	// v: viewing vector
	// n: face normal
	public ColorType applyLight(Material mat, Point3D v, Point3D n, Point3D Ps){
		ColorType res = new ColorType();
		
		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		Point3D v_obj  = this.position.minus(Ps).normalize(); 
		double cos_a = direction.normalize().dotProduct(v_obj);
		//System.out.println(cos_a);
		
		float f_aa = cos_a >= cos_theta? (float) Math.pow(cos_a,a_l) : 0;
		
		float distance = (float) this.position.minus(Ps).magnitude();
		float f_ra = 1/(r_a0+ r_a1 * distance + r_a2*distance*distance);
//		f_aa = 1;
//		f_ra = 1;
		
		
		double dot = direction.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse)
			{
				res.r = (float)(dot*mat.kd.r*color.r)*f_ra*f_aa;
				res.g = (float)(dot*mat.kd.g*color.g)*f_ra*f_aa;
				res.b = (float)(dot*mat.kd.b*color.b)*f_ra*f_aa;
			}
			// specular component
			if(mat.specular)
			{
				Point3D r = direction.reflect(n);
				dot = r.dotProduct(v);
				if(dot>0.0)
				{
					res.r += (float)(color.r*mat.ks.r*Math.pow(dot,mat.ns))*f_ra*f_aa;
					res.g += (float)(color.g*mat.ks.g*Math.pow(dot,mat.ns))*f_ra*f_aa;
					res.b += (float)(color.b*mat.ks.b*Math.pow(dot,mat.ns))*f_ra*f_aa;
				}
			}
			
			// clamp so that allowable maximum illumination level is not exceeded
			res.clamp();
		}
		return(res);
	}
	
	public void applyLight(ColorType c,Material mat, Point3D v, Point3D n, Point3D Ps, boolean diffuseKey, boolean specularKey){

		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		Point3D v_obj  = this.position.minus(Ps).normalize(); 
		double cos_a = direction.dotProduct(v_obj);
		
		
		float f_aa = cos_a >= cos_theta? (float) Math.pow(cos_a,a_l) : 0;
		f_aa = 1;
		
		float distance = (float) this.position.minus(Ps).magnitude();
		float f_ra = 1/(r_a0+ r_a1 * distance + r_a2*distance*distance);
		f_ra = 1;
		double dot = direction.dotProduct(n);
		if(dot>0.0)
		{
			// diffuse component
			if(mat.diffuse && diffuseKey)
			{
				c.r += (float)(dot*mat.kd.r*color.r)*f_ra*f_aa;
				c.g += (float)(dot*mat.kd.g*color.g)*f_ra*f_aa;
				c.b += (float)(dot*mat.kd.b*color.b)*f_ra*f_aa;
			}
			// specular component
			if(mat.specular && specularKey)
			{
				Point3D r = direction.reflect(n);
				dot = r.dotProduct(v);
				if(dot>0.0)
				{
					c.r += (float)(color.r*mat.ks.r*Math.pow(dot,mat.ns))*f_ra*f_aa;
					c.g += (float)(color.g*mat.ks.g*Math.pow(dot,mat.ns))*f_ra*f_aa;
					c.b += (float)(color.b*mat.ks.b*Math.pow(dot,mat.ns))*f_ra*f_aa;
				}
			}
			
			// clamp so that allowable maximum illumination level is not exceeded
			c.clamp();
		}

	}
}

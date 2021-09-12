/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The ambient light class
 * */

import java.util.Random;

public class LightAmbient extends Light{
	private Random rnd=new Random();
	
	public LightAmbient(ColorType _c)
	{
		color = new ColorType(_c);
		//direction = new Point3D(_direction);
		position = new Point3D(0, 0, 0); // Not used in this class
	}
	
	// apply this light source to the vertex / normal, given material
	// return resulting color value
	// v: viewing vector
	// n: face normal
	public ColorType applyLight(Material mat){
		ColorType res = new ColorType();
		
		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		
		
			// diffuse component
		if(mat.diffuse)
		{
			res.r = (float)(mat.kd.r*color.r);
			res.g = (float)(mat.kd.g*color.g);
			res.b = (float)(mat.kd.b*color.b);
		}
		//  component
		if(mat.ambient)
		{
			
				
			res.r += (float)(mat.ka.r*color.r);
			res.g += (float)(mat.ka.g*color.g);
			res.b += (float)(mat.ka.b*color.b);
	
		}
			
			// clamp so that allowable maximum illumination level is not exceeded
		res.clamp();
		
		return(res);
	}
	public void applyLight(ColorType c, Material mat, boolean diffuseKey, boolean ambientKey){
		
		// ****************Implement Code here*******************//
		// dot product between light direction and normal
		// light must be facing in the positive direction
		// dot <= 0.0 implies this light is facing away (not toward) this point
		// therefore, light only contributes if dot > 0.0 
		
		
			// diffuse component
		if(mat.diffuse && diffuseKey)
		{
			c.r += (float)(mat.kd.r*color.r);
			c.g += (float)(mat.kd.g*color.g);
			c.b += (float)(mat.kd.b*color.b);
		}
		//  component
		if(mat.ambient && ambientKey)
		{
			
				
			c.r += (float)(mat.ka.r*color.r);
			c.g += (float)(mat.ka.g*color.g);
			c.b += (float)(mat.ka.b*color.b);
	
		}
			
			// clamp so that allowable maximum illumination level is not exceeded
		c.clamp();
		
	}
}
 
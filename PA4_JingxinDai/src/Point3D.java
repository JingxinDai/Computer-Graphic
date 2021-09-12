//****************************************************************************
//       3D vector class for example program
//****************************************************************************
// History :
//   Nov 6, 2014 Created by Stan Sclaroff
//

public class Point3D
{
	public float x, y, z;
	public static final float ROUNDOFF_THRESHOLD = 0.0001f;
	
	public Point3D(float _x, float _y, float _z)
	{
		x=_x;
		y=_y;
		z=_z;
	}
	
	public Point3D(Point3D _v)
	{
		x=_v.x;
		y=_v.y;
		z=_v.z;
	}
	
	public Point3D()
	{
		x=y=z=(float)0.0;
	}
	
	public void set(float _x, float _y, float _z)
	{
		x=_x;
		y=_y;
		z=_z;
	}
	
	// compute the cross-product this (x) v and return result in out
	public void crossProduct(Point3D v, Point3D out)
	{
		Point3D temp = new Point3D();
		temp.x = this.y*v.z-this.z*v.y;
		temp.y = this.z*v.x-this.x*v.z;
		temp.z = this.x*v.y-this.y*v.x;
		
		out.x = temp.x;
		out.y = temp.y;
		out.z = temp.z;
	}
	
	// compute the cross-product this (x) v and return result
	public Point3D crossProduct(Point3D v)
	{
		Point3D out = new Point3D();
		out.x = this.y*v.z-this.z*v.y;
		out.y = this.z*v.x-this.x*v.z;
		out.z = this.x*v.y-this.y*v.x;
		return(out);
	}
	
	// compute dot product of v and this vector
	public float dotProduct(Point3D v)
	{
		return(v.x*this.x+v.y*this.y+v.z*this.z);
	}
			
	// subtract vector v from this vector and return result in out
	public void minus(Point3D v, Point3D out)
	{
		out.x = this.x-v.x;
		out.y = this.y-v.y;
		out.z = this.z-v.z;
	}
		
	// subtract vector v from this vector and return result
	public Point3D minus(Point3D v)
	{
		Point3D out = new Point3D();
		out.x = this.x-v.x;
		out.y = this.y-v.y;
		out.z = this.z-v.z;
		return(out);
	}
	
	// scale this vector by s and return result
	public Point3D scale(float s)
	{
		Point3D out = new Point3D();
		out.x = this.x*s;
		out.y = this.y*s;
		out.z = this.z*s;
		return(out);
	}
	
	// add the vector v to this vector and return result 
	public Point3D plus(Point3D v)
	{
		Point3D out = new Point3D();
		out.x = this.x+v.x;
		out.y = this.y+v.y;
		out.z = this.z+v.z;
		return(out);
	}
	
	// compute the length / magnitude
	public double magnitude()
	{
		double mag = Math.sqrt(dotProduct(this));
		return(mag);
	}
	
	// produce unit vector
	public Point3D normalize()
	{
		double mag = this.magnitude();
		if(mag>ROUNDOFF_THRESHOLD)
		{
			this.x /= mag;
			this.y /= mag;
			this.z /= mag;
		}
		return this;
		// should probably throw an error exception here for zero magnitude 
	}
	
	// compute the reflection of this vector around vector n
	public Point3D reflect(Point3D n)
	{

		float dot = 2*this.dotProduct(n);
		Point3D out = n.scale(dot);
		out = out.minus(this);
		
		return(out);
	}
	
	public static Point3D normVectorInterpolation(float x1, Point3D n1, float x2, Point3D n2,float x) {
		float n_x = 0, n_y = 0, n_z =0;
//		float dx = Math.abs(x1-x2);
//		
//		
//		float dx1 = Math.abs(x-x1);
//		float dx2 = Math.abs(x-x2);
//		
//		
//		if(x==x1){
//			n_x = n1.x;
//			n_y = n1.y;
//			n_z = n1.z;
//		}
//		else if(x==x2){
//			n_x = n2.x;
//			n_y = n2.y;
//			n_z = n2.z;
//		}
//		else{
//			n_x = ((float)dx2/dx)*n1.x+((float)dx1/dx)*n2.x;
//			n_y = ((float)dx2/dx)*n1.y+((float)dx1/dx)*n2.y;
//			n_z = ((float)dx2/dx)*n1.z+((float)dx1/dx)*n2.z;
//		}
		
		n_x = x2 == x1? n1.x : ((x-x1)*(n2.x-n1.x)/(x2-x1)+n1.x);
		n_y = x2 == x1? n1.y : ((x-x1)*(n2.y-n1.y)/(x2-x1)+n1.y);
		n_z = x2 == x1? n1.z : ((x-x1)*(n2.z-n1.z)/(x2-x1)+n1.z);
		Point3D n = new Point3D(n_x,n_y,n_z);
		return n;
	}
	
}
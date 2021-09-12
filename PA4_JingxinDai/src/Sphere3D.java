//****************************************************************************
//      Sphere class
//****************************************************************************
// History :
//   Nov 6, 2014 Created by Stan Sclaroff
//

public class Sphere3D extends Object3D
{

	public float r;

	
	public Sphere3D(Point3D center, float _r, int _stacks, int _slices, Material mat) {
		super(center,_stacks,_slices,mat);
		this.r = _r;
		initMesh();
	}
	

	
	public void set_center(float _x, float _y, float _z)
	{
		center.x=_x;
		center.y=_y;
		center.z=_z;
		fillMesh();  // update the triangle mesh
	}
	
	public void set_radius(float _r)
	{
		r = _r;
		fillMesh(); // update the triangle mesh
	}
	
	public void set_stacks(int _stacks)
	{
		stacks = _stacks;
		initMesh(); // resized the mesh, must re-initialize
	}
	
	public void set_slices(int _slices)
	{
		slices = _slices;
		initMesh(); // resized the mesh, must re-initialize
	}
	
	public int get_n()
	{
		return slices;
	}
	
	public int get_m()
	{
		return stacks;
	}

	protected void initMesh()
	{
		mesh = new Mesh3D(stacks,slices);
		fillMesh();  // set the mesh vertices and normals
	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	protected void fillMesh()
	{
		// ****************Implement Code here*******************//
		int i, j;
		float theta, phi;
		float d_theta = (float)(2*Math.PI)/(float)(slices-1);
		float d_phi = (float)(Math.PI)/(float)(stacks-1);
		float cos_theta, sin_theta;
		float cos_phi, sin_phi;
		
		for(i=0, phi=(float)(-0.5*Math.PI); i<stacks; i++, phi+=d_phi) {
			cos_phi = (float)Math.cos(phi);
			sin_phi = (float)Math.sin(phi);
			
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				
				mesh.v[i][j].x = center.x+r*cos_phi*cos_theta;
				mesh.v[i][j].y = center.y+r*cos_phi*sin_theta;
				mesh.v[i][j].z = center.z+r*sin_phi;
				
				mesh.n[i][j].x = cos_phi * cos_theta;
				mesh.n[i][j].y = cos_phi * sin_theta;
				mesh.n[i][j].z = sin_phi;
				
			}
		}
	}
	
	protected void fillMesh_bump() {
		int i, j;
		float theta, phi;
		float d_theta = (float)(2*Math.PI)/(float)(slices-1);
		float d_phi = (float)(Math.PI)/(float)(stacks-1);
		float cos_theta, sin_theta;
		float cos_phi, sin_phi;
		
		for(i=0, phi=(float)(-0.5*Math.PI); i<stacks; i++, phi+=d_phi) {
			cos_phi = (float)Math.cos(phi);
			sin_phi = (float)Math.sin(phi);
			
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				
				
				
				mesh.n[i][j].x = cos_phi * cos_theta;
				mesh.n[i][j].y = cos_phi * sin_theta;
				mesh.n[i][j].z = sin_phi;
				
				mesh.v[i][j].x = center.x+r*cos_phi*cos_theta + cos_phi * cos_theta * B(theta,phi);
				mesh.v[i][j].y = center.y+r*cos_phi*sin_theta + cos_phi * sin_theta * B(theta,phi);
				mesh.v[i][j].z = center.z+r*sin_phi + sin_phi * B(theta,phi);
				
				
				Point3D Bu_N_Pv =  mesh.n[i][j].crossProduct(new Point3D(-cos_theta*sin_phi*r, -sin_theta*sin_phi*r, cos_phi*r)).scale(B_u(theta));
				Point3D Bv_Pu_N = new Point3D(-sin_theta*cos_phi*r, cos_theta *cos_phi *r ,0).crossProduct(mesh.n[i][j]).scale(B_v(phi));
				
				mesh.n[i][j].x = cos_phi * cos_theta + Bu_N_Pv.x + Bv_Pu_N.x;
				mesh.n[i][j].y = cos_phi * sin_theta + Bu_N_Pv.y + Bv_Pu_N.y;
				mesh.n[i][j].z = sin_phi + Bu_N_Pv.z + Bv_Pu_N.z;
				
			}
		}
	}
	
	public static float B(float u, float v) {
		return (float) (Math.sin(u)+Math.cos(v));
	}
	
	public static float B_u(float u) {
		return  (float) Math.cos(u);
	}
	public static float B_v(float v) {
		return (float) -Math.sin(v);
	}
}
/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The Cylinder3D class(also can modulate Box3D).
 */

public class Cylinder3D extends Object3D
{
//	private Point3D center;
	private float r;
	private float h;
//	private int stacks,slices;
//	public Mesh3D mesh;
	
	public Cylinder3D(Point3D center, float _r, float _h, int _stacks, int _slices,Material mat) {
		super(center,_stacks,  _slices,  mat);
		this.r = _r;
		this.h = _h;
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
	
	public void set_height(float _h) {
		h = _h;
		fillMesh();
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
	@Override
	public int get_n()
	{
		return slices;
	}
	@Override
	public int get_m()
	{
		return stacks*3+3;
	}
	
	public float get_h() {
		return h;
	}
	
	public Point3D get_center() {
		return center;
	}
	

	protected void initMesh()
	{
		mesh = new Mesh3D(stacks*3+3,slices);
		fillMesh();  // set the mesh vertices and normals
	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	protected void fillMesh()
	{
		// ****************Implement Code here*******************//
		int i, j;
		float theta, _z;
		float d_theta = (float)(2*Math.PI)/(float)(slices-1);
		float d_z = (float)h/stacks;
		float cos_theta, sin_theta;

		//buttom surface
		float d_r = (float)r/stacks;
		float top_r,bottom_r; 
				
		for(i=0, bottom_r = 0.0f; i <=stacks; i++,bottom_r+=d_r) {	
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
						cos_theta = (float)Math.cos(theta);
						sin_theta = (float)Math.sin(theta);
						
						mesh.v[i][j].x = center.x+ bottom_r * cos_theta;
						mesh.v[i][j].y = center.y+ bottom_r * sin_theta; 
						mesh.v[i][j].z = center.z - 0.5f*h;
						
						mesh.n[i][j].x =  0;
						mesh.n[i][j].y =  0;
						mesh.n[i][j].z = -1;
				}
		}
		for(i=stacks+1, _z = (float)(-0.5f*h); i<=2*stacks+1; i++, _z+=d_z) {
			
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				
				mesh.v[i][j].x = center.x+r * cos_theta;
				mesh.v[i][j].y = center.y+r * sin_theta; 
				mesh.v[i][j].z = center.z + _z;
				
				mesh.n[i][j].x =  cos_theta;
				mesh.n[i][j].y =  sin_theta;
				mesh.n[i][j].z = 0;
			}
		}
		
		//top surface
		
		for(i= 2*stacks+2, top_r = r ; i < 3*stacks+3; i++, top_r-=d_r) {		
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				
				mesh.v[i][j].x = center.x+ top_r * cos_theta;
				mesh.v[i][j].y = center.y+ top_r * sin_theta; 
				mesh.v[i][j].z = center.z + 0.5f*h;
				
				mesh.n[i][j].x =  0;
				mesh.n[i][j].y =  0;
				mesh.n[i][j].z = 1;
			}
		}
		
		
	}
}
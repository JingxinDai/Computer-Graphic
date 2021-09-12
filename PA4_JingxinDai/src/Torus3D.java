/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The Torus3D class
 * */


public class Torus3D extends Object3D
{
//	private Point3D center;
	private float r;
	private float R;
//	private int stacks,slices;
//	public Mesh3D mesh;
	
	public Torus3D(Point3D center,float _R, float _r, int _stacks, int _slices, Material mat) {
		super(center,_stacks,_slices,mat);
		this.R = _R;
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
//	@Override
//	public int get_n()
//	{
//		return slices;
//	}
//	@Override
//	public int get_m()
//	{
//		return stacks;
//	}
//
//	private void initMesh()
//	{
//		mesh = new Mesh3D(stacks,slices);
//		fillMesh();  // set the mesh vertices and normals
//	}
		
	// fill the triangle mesh vertices and normals
	// using the current parameters for the sphere
	protected void fillMesh()
	{
		// ****************Implement Code here*******************//
		int i, j;
		float theta, phi;
		float d_theta = (float)(2*Math.PI)/(float)(slices-1);
		float d_phi = (float)(2*Math.PI)/(float)(stacks-1);
		float cos_theta, sin_theta;
		float cos_phi, sin_phi;
		
		for(i=0, phi=(float)(-Math.PI); i<stacks; i++, phi+=d_phi) {
			cos_phi = (float)Math.cos(phi);
			sin_phi = (float)Math.sin(phi);
			
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				
				mesh.v[i][j].x = center.x + (R + r*cos_phi)*cos_theta;
				mesh.v[i][j].y = center.y + (R + r*cos_phi)*sin_theta;
				mesh.v[i][j].z = center.z+r*sin_phi;
				
				mesh.n[i][j].x = cos_phi * cos_theta;
				mesh.n[i][j].y = cos_phi * sin_theta;
				mesh.n[i][j].z = sin_phi;
			}
		}
	}
}
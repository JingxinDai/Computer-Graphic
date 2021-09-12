/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The super Ellipsoid object
 */
public class SuperEllipsoid3D extends Object3D{
	private float r_x;
	private float r_y;
	private float r_z;
	private float n;
	
	
	public SuperEllipsoid3D(Point3D center, float r_x, float r_y, float r_z,float n, int _stacks, int _slices, Material mat) {
		super(center,_stacks,_slices,mat);
		this.r_x = r_x;
		this.r_y = r_y;
		this.r_z = r_z;
		this.n = n;
		initMesh();
	}
	
	public void set_center(float _x, float _y, float _z)
	{
		center.x=_x;
		center.y=_y;
		center.z=_z;
		fillMesh();  // update the triangle mesh
	}
	
	public void set_radius(float r_x, float r_y, float r_z)
	{
		this.r_x = r_x;
		this.r_y = r_y;
		this.r_z = r_z;
		fillMesh(); // update the triangle mesh
	}
	
	public void set_n(float n) {
		this.n = n;
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
			int sign_cos_phi = cos_phi > 0 ? 1 : -1;
			int sign_sin_phi = sin_phi > 0 ? 1 : -1;
			
			for(j=0, theta=(float)(-Math.PI); j<slices; j++, theta+=d_theta) {
				cos_theta = (float)Math.cos(theta);
				sin_theta = (float)Math.sin(theta);
				int sign_cos_theta = cos_theta > 0 ? 1 : -1;
				int sign_sin_theta = sin_theta > 0 ? 1 : -1;
				
				mesh.v[i][j].x = (float) (center.x+ r_x * Math.pow(Math.abs(cos_phi),(float)2/n) * Math.pow(Math.abs(cos_theta),(float)2/n) * sign_cos_phi * sign_cos_theta);
				mesh.v[i][j].y = (float) (center.y+ r_y * Math.pow(Math.abs(cos_phi),(float)2/n) * Math.pow(Math.abs(sin_theta),(float)2/n) * sign_cos_phi * sign_sin_theta);
				mesh.v[i][j].z = (float) (center.z+ r_z * Math.pow(Math.abs(sin_phi),(float)2/n) * sign_sin_phi);
				
				mesh.n[i][j].x = (float) (Math.pow(Math.abs(cos_phi),(float)2/n) * Math.pow(Math.abs(cos_theta),(float)2/n) * sign_cos_phi * sign_cos_theta);
				mesh.n[i][j].y = (float) (Math.pow(Math.abs(cos_phi),(float)2/n) * Math.pow(Math.abs(sin_theta),(float)2/n) * sign_cos_phi * sign_sin_theta);
				mesh.n[i][j].z = (float) (Math.pow(Math.abs(sin_phi),(float)2/n) * sign_sin_phi);
			}
		}
	}
}

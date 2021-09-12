/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The abstract Object3D class, which is the super class of all 3D object
 */
public abstract class  Object3D
{
	public Mesh3D mesh;
	protected Point3D center;
	protected int stacks,slices;
	public Material mat;
	
	public Object3D(Point3D center,int stacks, int slices, Material mat) {
		this.center = center;
		this.stacks = stacks;
		this.slices = slices;
		this.mat = mat;
		initMesh();
	}
	
	public Point3D get_center() {
		return center;
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
	abstract protected void  fillMesh() ;
	

}

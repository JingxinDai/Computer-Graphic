/**
 * Point3D.java - a three-dimensional point with double values
 * 
 * History:
 * 11 Oct 2019
 *  - add normalize() method
 *  - add distance() method
 *  - add isParallel() method
 *  - add findVertical() method
 *  - add distance() method
 *  - add equals() method
 *  
 *  Jingxin Dai<jingxin@bu.edu>
 *  
 * 10 October 2019
 * 
 * - Add norm() method
 * 
 * - Add more constructors for convenience
 * 
 * (Zezhou Sun <szz314159@gmail.com>)
 * 
 * 18 February 2011
 * 
 * - made members private and added accessors
 * 
 * - added documentation
 * 
 * - added toString() method
 * 
 * (Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>)
 * 
 * 30 January 2008
 * 
 * - created
 * 
 * (Tai-Peng Tian <tiantp@gmail.com>)
 */

/**
 * A three-dimensional point with double values.
 * 
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @author Jingxin Dai<jingxin@bu.edu>
 * @since Spring 2008
 */
public class Point3D {
	/** The origin, (0, 0, 0). */
	public static final Point3D ORIGIN = new Point3D(0, 0, 0);
	/** The x component of this point. */
	private final double x;
	/** The y component of this point. */
	private final double y;
	/** The z component of this point. */
	private final double z;
	
	private final double diff_err = 0.00001;

	/**
	 * Instantiates this point with the three specified components.
	 * 
	 * @param x The x component of this point.
	 * @param y The y component of this point.
	 * @param z The z component of this point.
	 */
	public Point3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(final float x, final float y, final float z) {
		this.x = (double)x;
		this.y = (double)y;
		this.z = (double)z;
	}

	public Point3D(final Point3D p) {
		this.x = p.x();
		this.y = p.y();
		this.z = p.z();
	}

	/**
	 * Returns the String representation of this object.
	 * 
	 * @return The String representation of this object.
	 */
	@Override
	public String toString() {
		return "Point[" + this.x + ", " + this.y + ", " + this.z + "]";
	}

	/**
	 * Gets the x component of this point.
	 * 
	 * @return The x component of this point.
	 */
	public double x() {
		return this.x;
	}

	/**
	 * Gets the y component of this point.
	 * 
	 * @return The y component of this point.
	 */
	public double y() {
		return this.y;
	}

	/**
	 * Gets the z component of this point.
	 * 
	 * @return The z component of this point.
	 */
	public double z() {
		return this.z;
	}

	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	public Point3D normalize() {
		return new Point3D(this.x/norm(), this.y/norm(), this.z/norm());
	}
	public double dotProduct(Point3D b) {
		return this.x*b.x()+this.y*b.y()+this.z*b.z();
	}
	
	public Point3D crossProduct(Point3D b) {
		return new Point3D(this.y*b.z()-this.z*b.y(), 
						   this.z*b.x()-this.x*b.z(), 
						   this.x*b.y()-this.y*b.x());
	}
	
	public boolean equals(Point3D b) {
		return (Math.abs(this.x-b.x)<diff_err && Math.abs(this.y -b.y)<diff_err && Math.abs(this.z- b.z())<diff_err);
		
	}
	
	public Point3D minus(Point3D b) {
		return new Point3D(this.x-b.x, this.y-b.y, this.z - b.z);
	}
	
	public Point3D deltaGaussion() {
		double exp = Math.exp(-this.dotProduct(this));
		return new Point3D(this.x * exp, this.y * exp, this.z * exp);
	}
	
	public boolean isParallel(Point3D b) {
		boolean res = false;
//		if(this.x / b.x == this.y/b.y && this.x/b.x == this.z / b.z   ) {
//			res = true;
//		}
		Point3D a_norm = this.normalize();
		Point3D b_norm = b.normalize();
		if(Math.abs(Math.abs(a_norm.x*b_norm.x) + Math.abs(a_norm.y*b_norm.y) + Math.abs(a_norm.z*b_norm.z)-1) <= diff_err) {
			res = true;
		}
		return res;
	}
	public Point3D findVerticalVertex() {
		if(this.x == 0) {
			return new Point3D(1,0,0);
		}
		else if(this.y == 0){
			return new Point3D(0,1,0);
		}
		else if(this.z == 0) {
			return new Point3D(0,0,1);
		}
		return new Point3D(-this.y,x,0);
	}
	
	public double distance(Point3D b) {
		return Math.sqrt((this.x-b.x)*(this.x-b.x)+(this.y-b.y)*(this.y-b.y)+(this.z-b.z)*(this.z-b.z));
	}
}

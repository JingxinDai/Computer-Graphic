/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA3
 * @due 11/13/2019
 * @brief The Bird class I create, which can eat MyButterfly object
 * 	      it has potential function
 */

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;


public class MyBird extends Component implements Animate{
	
	private int bird_object;
	private double scale=1;
	private double wing_delta_angle = 2;
	//private float rWing_delta_angle;
	//private Point3D speed = new Point3D(0.01,0,0);
	double vx, vy, vz;
	private Random rand = new Random();
	
	private Quaternion orientation = new Quaternion();
	
	/** The OpenGL utility toolkit object. */
	private final GLUT glut = new GLUT();
	private final GLU glu = new GLU();
	
	private final Component left_wing;
	private final Component right_wing;
	private final Component body;
	public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
	public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
	
	public static final double BODY_RADIUS = 0.2;
	public static final double BODY_LENGTH = 0.3;
	public static final double WING_RADIUS = 0.4;
	public static final double WING_THICK = 0.05;
	
	private Point3D original_orientation;
	private Point3D target_orientation;
	
	public MyBird(final Point3D position, final String name) {
		super(position, name);
		
		Point3D random_speed = new Point3D(Math.random(),Math.random(),Math.random());
		Point3D norm_speed = random_speed.normalize();
		this.vx = norm_speed.x()*0.015;
		this.vy = norm_speed.y()*0.015;
		this.vz = norm_speed.z()*0.015;
		original_orientation = new Point3D(vx,vy,vz);
		
		this.body = new Component(new Point3D(0,0,-0.5*BODY_LENGTH*scale), 
				new Palm(BODY_RADIUS*scale,0.5f,0.5f,1.0f, this.glu),
				"bird body");
		this.right_wing = new Component(new Point3D(0,0,0),
								new Palm(WING_RADIUS*scale, 1.0f, 0.3f, 0.1f,this.glu),
								"bird left wing");
		this.left_wing = new Component(new Point3D(0,0,0),
								new Palm(WING_RADIUS*scale, 1.0f, 0.3f, 0.1f,this.glu),
								"bird right wing");
		addChild(this.body);
		this.body.addChild(this.left_wing);
		this.body.addChild(this.right_wing);
		
		
		
	
		
		this.left_wing.setYPositiveExtent(45);
	    this.left_wing.setYNegativeExtent(-45);
	    
	    this.right_wing.setYPositiveExtent(45);
	    this.right_wing.setYNegativeExtent(-45);
	    
	    this.body.setColor(INACTIVE_COLOR);
	    this.left_wing.setColor(INACTIVE_COLOR);
	    this.right_wing.setColor(INACTIVE_COLOR);
		
	    
	}
	

	
	public void animationUpdate(final GL2 gl) {
		wingLoop();
		face2Direction();
		movePosition();
	}
	
	public void wingLoop() {
		
		double x_angle = this.left_wing.xAngle();
		double y_angle = this.left_wing.yAngle();
		double z_angle = this.left_wing.zAngle();
		if(y_angle >= 45 || y_angle <= -45) {
			wing_delta_angle = -wing_delta_angle;
		}
		

//		this.left_wing.setPosition(
//				new Point3D(-Math.abs(Math.cos(left_wing.zAngle()+wing_delta_angle) * WING_RADIUS *scale),
//						0, 0));		
		this.left_wing.setAngles(90, left_wing.yAngle()+wing_delta_angle, 0);
		this.right_wing.setAngles(90, right_wing.yAngle()-wing_delta_angle, 0);
		
		this.left_wing.setPosition(
				new Point3D(-Math.abs(Math.cos(Math.toRadians(left_wing.yAngle()+wing_delta_angle)) * (WING_RADIUS) *scale),
						-Math.sin(Math.toRadians(left_wing.yAngle()+wing_delta_angle)) * (WING_RADIUS) *scale, 0));
		this.right_wing.setPosition(
				new Point3D(Math.abs(Math.cos(Math.toRadians(right_wing.yAngle()+wing_delta_angle)) * (WING_RADIUS) *scale),
						Math.sin(Math.toRadians(right_wing.yAngle()+wing_delta_angle)) * (WING_RADIUS) *scale, 0));
		//System.out.printf("(%d,%d,%d)\n", x_angle, y_angle, z_angle);
		
	}
	
	public void potentialFunction(ArrayList<Component> vivarium) {
		Point3D currentP = this.position();
		double dx=0,dy=0,dz=0;
		//double predator_weight = 0.5;
		double food_weight = 0.5;
		double wall_weight = 0.05;
		for (Component example : vivarium) {
			Point3D otherP = example.position();
			if(example == this) continue;
			if(example instanceof MyButterfly) {
				Point3D attraction = example.position().minus( this.position());
				Point3D delta_gaussion_att = attraction.deltaGaussion();
				dx += delta_gaussion_att.x() * food_weight;
				dy += delta_gaussion_att.y() * food_weight;
				dz += delta_gaussion_att.z() * food_weight;
			}
			
		}
		double repulsive_x1 = this.position().x()+2;
		double repulsive_x1_gaus = repulsive_x1*Math.exp(- repulsive_x1 * repulsive_x1);
		dx += repulsive_x1_gaus*wall_weight;
		double repulsive_x2 = this.position().x()-2;
		double repulsive_x2_gaus = repulsive_x2*Math.exp(- repulsive_x2 * repulsive_x2);
		dx += repulsive_x2_gaus * wall_weight;
		
		double repulsive_y1 = this.position().y()+2;
		double repulsive_y1_gaus = repulsive_y1*Math.exp(- repulsive_y1 * repulsive_y1);
		dy += repulsive_y1_gaus*wall_weight;
		double repulsive_y2 = this.position().x()-2;
		double repulsive_y2_gaus = repulsive_y2*Math.exp(- repulsive_y2 * repulsive_y2);
		dy += repulsive_y2_gaus * wall_weight;
		
		double repulsive_z1 = this.position().z()+2;
		double repulsive_z1_gaus = repulsive_z1*Math.exp(- repulsive_z1 * repulsive_z1);
		dz += repulsive_z1_gaus * wall_weight;
		double repulsive_z2 = this.position().z()-2;
		double repulsive_z2_gaus = repulsive_z2*Math.exp(- repulsive_z2 * repulsive_z2);
		dz += repulsive_z2_gaus * wall_weight;
		
		Point3D new_speed = new Point3D(vx+dx,vy+dy,vz+dz);
		Point3D new_speed_norm = new_speed.normalize();
		this.vx = new_speed_norm.x()*0.015;
		this.vy = new_speed_norm.y()*0.015;
		this.vz = new_speed_norm.z()*0.015;
		
	}
	
	
	public void movePosition() {
		Point3D presentP = position();
		if(presentP.x() + scale*this.WING_RADIUS>= 2 || presentP.x() - scale*this.WING_RADIUS<= -2) {
			  vx = vx > 0 ? -0.02:0.02;
		  }
		  if(presentP.y() + scale*this.WING_RADIUS >= 2 || presentP.y() - scale*this.WING_RADIUS<= -2) {
			  vy = vy > 0 ? -0.02:0.02; 
		  }
		  if(presentP.z() + scale*this.WING_RADIUS >= 2 || presentP.z() - scale*this.WING_RADIUS <= -2) {
			  vz = vz > 0 ? -0.02:0.02;
		  }
		  
		  double new_x = presentP.x()+vx;
		  double new_y = presentP.y()+vy;
		  double new_z = presentP.z()+vz;
		  
		  if(new_x + scale*this.WING_RADIUS > 2) {
			 new_x = 2-scale*this.WING_RADIUS-0.1;
		  }
		  if(new_x + scale*this.WING_RADIUS < -2) {
			 new_x = -2+scale*this.WING_RADIUS+0.1;
		  }
		  if(new_y  + scale*this.WING_RADIUS > 2) {
			  new_y = 2-scale*this.WING_RADIUS-0.1;
		  }
		  if(new_y + scale*this.WING_RADIUS < -2) {
			  new_y = -2+scale*this.WING_RADIUS+0.1;
		  }
		  if(new_z  + scale*this.WING_RADIUS > 2) {
			  new_z = 2-scale*this.WING_RADIUS-0.1;
		  }
		  if(new_z + scale*this.WING_RADIUS < -2) {
			  new_z = -2+scale*this.WING_RADIUS+0.1;
		  }
		
		setPosition(new Point3D(new_x, new_y, new_z));
		
	}
	
	public void face2Direction() {
		this.target_orientation = new Point3D(vx,vy,vz);
		  
		Point3D norm_ori = original_orientation.normalize();
		Point3D norm_tar = target_orientation.normalize();
		
		if(norm_ori.isParallel(norm_tar) && (!norm_ori.equals(norm_tar))) {
			
			//rotate 180, rotate axis (-norm_tar.y(),norm_tar.x(),0)
			Point3D rotation_matrix = norm_ori.findVerticalVertex();
			Point3D norm_rotate_matrix = rotation_matrix.normalize();
			Quaternion q = new Quaternion((float)(Math.cos(Math.toRadians(90))),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.x()),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.y()),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.z()) );
			this.orientation = q.multiply(this.orientation);
			this.orientation.normalize();
			this.preMatrix = this.orientation.to_matrix();
			this.original_orientation = this.target_orientation;
		}
		else if (!norm_ori.equals(norm_tar) ) {
			
			
			Point3D rotation_matrix = norm_ori.crossProduct(norm_tar);
			Point3D norm_rotate_matrix = rotation_matrix.normalize();
			
			double dotProduct = norm_ori.dotProduct(norm_tar);
			if ( dotProduct > 1) {
				dotProduct = 1;
			}
			double rotation_angle = Math.acos(dotProduct);
			
			
			//Quaternion q = Q(rotation_axis, rotation_angle);
			Quaternion q = new Quaternion((float)(Math.cos(rotation_angle/2)),
										(float)(Math.sin(rotation_angle/2)*norm_rotate_matrix.x()),
										(float)(Math.sin(rotation_angle/2)*norm_rotate_matrix.y()),
										(float)(Math.sin(rotation_angle/2)*norm_rotate_matrix.z()) );
			this.orientation = q.multiply(this.orientation);
			this.orientation.normalize();
			this.preMatrix = this.orientation.to_matrix();
			this.original_orientation = this.target_orientation;
  		}
	}
	
	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		// TODO Auto-generated method stub
		
	}
}
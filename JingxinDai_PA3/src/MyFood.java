/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA3
 * @due 11/13/2019
 * @brief The Food class I create,it can be eaten by MyButterfly and MySpider
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

public class MyFood extends Component implements Animate {
	
	private int food_object;
	public boolean isEaten = false;
	private float scale=1;
	//private float rWing_delta_angle;
	private Point3D speed = new Point3D(0,-0.01,0);
	
	double vx=0, vy=-0.01, vz=0;
	private Random rand = new Random();
	/** The OpenGL utility toolkit object. */
	private final GLUT glut = new GLUT();
	private final GLU glu = new GLU();
	
	private final Component food;
	
	/** The set of all components. */
	//private final List<Component> components;
	/** The set of components which are currently selected for rotation. */
	//private final Set<Component> selectedComponents = new HashSet<Component>(18);
	
	/** The color for components which are selected for rotation. */
	public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  	/** The color for components which are not selected for rotation. */
	public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
	
	public static final double FOOD_RADIUS = 0.03;
	
	public MyFood(final Point3D position, final String name) {
		
		
		super(position, name);
		
		this.food = new Component(new Point3D(0,0,0), new Ellipsoid(
		        FOOD_RADIUS*scale, this.glu), "food");
		
		this.addChild(this.food);
		this.food.setColor(ACTIVE_COLOR);
	}
	/*
	 * 
	 */
	public void animationUpdate(final GL2 gl) {
		movePosition();
	}
	
	public void movePosition() {
		Point3D presentP = position();
		if(presentP.x() >= 2 || presentP.x() <= -2) {
			  vx = -vx;
		  }
		  if( presentP.y() <= -2+FOOD_RADIUS*scale) {
			  vy = 0; 
		  }
		  if(presentP.z() >= 2 || presentP.z() <= -2) {
			  vz = -vz;
		  }
		
		setPosition(new Point3D(presentP.x()+vx, presentP.y()+vy, presentP.z()+vz));
	}
	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		// TODO Auto-generated method stub
		
	}
	
	public void collisionButterfly(ArrayList<Component> vivarium) {
		Point3D currentP = this.position();
		double distance_limit = 0.15;
		for (Component example : vivarium) {
			Point3D otherP = example.position();
			if(example == this) continue;
			if(example instanceof MyButterfly) {
				if(currentP.distance(otherP)<0.3) {
						
					this.isEaten = true;
				}
					
			}
			if(example instanceof MySpider) {
				if(currentP.distance(otherP)<0.4) {
						
					this.isEaten = true;
				}
					
			}
		}
	}
	
	
	}
/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA3
 * @due 11/13/2019
 * @brief The Spider class I create.it has potential function, can eat MyFood object.
 * 		 it loop through 5 test case gesture. 
 */

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class MySpider extends Component implements Animate, Selection {
	/** The OpenGL utility toolkit object. */
	private final GLUT glut = new GLUT();
	private final GLU glu = new GLU();
	
	private final TestCasesSpider testCases = new TestCasesSpider();
	int time_counter = 0;
	
	private double vx,vz;
	private final static double vy=0;
	private Point3D original_orientation;
	private Point3D target_orientation;
	private Quaternion orientation = new Quaternion();
	
	private final Component head;
	private final Component butt;
	/** The limb on the head to be modeled. */
	private final Limb[] limbs;
	/** The Eyes on the head*/
	private final Eye[] eyes;
	/** The set of all components. */
	private final List<Component> components;
	/** The set of components which are currently selected for rotation. */
	private final Set<Component> selectedComponents = new HashSet<Component>(18);
	
	/** The color for components which are selected for rotation. */
	public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  	/** The color for components which are not selected for rotation. */
	public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
	
	public static final FloatColor EYE_COLOR =  FloatColor.WHITE;
	public static final FloatColor PUPIL_COLOR =  FloatColor.BLACK;
	
	public static final double scale = 0.5;
	

	/** The radius of the components which comprise the arm. */
	public static final double HEAD_RADIUS = 0.35*scale;
	/** The radius of the components which comprise the arm. */
	public static final double BUTT_RADIUS = 0.5*scale;
	/** The height of the distal joint on each of the fingers. */
	public static final double DISTAL_JOINT_HEIGHT = 0.35*scale;
	/** The radius of each joint which comprises the finger. */
	public static final double LIMB_RADIUS = 0.04*scale;
	/** The height of the middle joint on each of the fingers. */
	public static final double MIDDLE_JOINT_HEIGHT = 0.20*scale;
	/** The height of the palm joint on each of the fingers. */
	public static final double NEAR_JOINT_HEIGHT = 0.20*scale;
	
	public static final double EYE_RADIUS = 0.09*scale;
	public static final double PUPIL_RADIUS = 0.04*scale;
	
	
	public static final String FIRST_LEFT_LIMB_NEAR_NAME = "limb 1 left near";
	public static final String FIRST_LEFT_LIMB_MIDDLE_NAME = "limb 1 left middle";
  	public static final String FIRST_LEFT_LIMB_DISTAL_NAME = "limb 1 left distal";
  	public static final String FIRST_RIGHT_LIMB_NEAR_NAME = "limb 1 right near";
	public static final String FIRST_RIGHT_LIMB_MIDDLE_NAME = "limb 1 right middle";
  	public static final String FIRST_RIGHT_LIMB_DISTAL_NAME = "limb 1 right distal";
  	
  	public static final String SECOND_LEFT_LIMB_NEAR_NAME = "limb 2 left near";
	public static final String SECOND_LEFT_LIMB_MIDDLE_NAME = "limb 2 left middle";
  	public static final String SECOND_LEFT_LIMB_DISTAL_NAME = "limb 2 left distal";
  	public static final String SECOND_RIGHT_LIMB_NEAR_NAME = "limb 2 right near";
	public static final String SECOND_RIGHT_LIMB_MIDDLE_NAME = "limb 2 right middle";
  	public static final String SECOND_RIGHT_LIMB_DISTAL_NAME = "limb 2 right distal";
  	
  	public static final String THIRD_LEFT_LIMB_NEAR_NAME = "limb 3 left near";
	public static final String THIRD_LEFT_LIMB_MIDDLE_NAME = "limb 3 left middle";
  	public static final String THIRD_LEFT_LIMB_DISTAL_NAME = "limb 3 left distal";
  	public static final String THIRD_RIGHT_LIMB_NEAR_NAME = "limb 3 right near";
	public static final String THIRD_RIGHT_LIMB_MIDDLE_NAME = "limb 3 right middle";
  	public static final String THIRD_RIGHT_LIMB_DISTAL_NAME = "limb 3 right distal";
  	
  	public static final String FOURTH_LEFT_LIMB_NEAR_NAME = "limb 4 left near";
	public static final String FOURTH_LEFT_LIMB_MIDDLE_NAME = "limb 4 left middle";
  	public static final String FOURTH_LEFT_LIMB_DISTAL_NAME = "limb 4 left distal";
  	public static final String FOURTH_RIGHT_LIMB_NEAR_NAME = "limb 4 right near";
	public static final String FOURTH_RIGHT_LIMB_MIDDLE_NAME = "limb 4 right middle";
  	public static final String FOURTH_RIGHT_LIMB_DISTAL_NAME = "limb 4 right distal";
  	
  	public static final String HEAD_NAME = "head";
  	public static final String BUTT_NAME = "butt";
  	
  	public static final String RIGHT_EYEBALL_NAME = "right eyeball";
  	public static final String LEFT_EYEBALL_NAME = "left eyeball";
  	public static final String RIGHT_PUPIL_NAME = "right pupil";
  	public static final String LEFT_PUPIL_NAME = "left pupil";
  	
  	
  	private Component mapNum2Component(int componentNum) {
  		switch(componentNum) {
  			case  0: return this.head;
			case  1: return this.butt;
			case  2: return this.limbs[0].nearJoint();  //1Fore Limb Left
			case  3: return this.limbs[0].middleJoint();
			case  4: return this.limbs[0].distalJoint();
			case  5: return this.limbs[1].nearJoint();  //1Fore Limb Right
			case  6: return this.limbs[1].middleJoint();
			case  7: return this.limbs[1].distalJoint();
			case  8: return this.limbs[2].nearJoint();  //2 Limb L
			case  9: return this.limbs[2].middleJoint();
			case 10: return this.limbs[2].distalJoint();
			case 11: return this.limbs[3].nearJoint();  //2 Limb R
			case 12: return this.limbs[3].middleJoint();
			case 13: return this.limbs[3].distalJoint();
			case 14: return this.limbs[4].nearJoint();  //3 Limb L
			case 15: return this.limbs[4].middleJoint();
			case 16: return this.limbs[4].distalJoint();
			case 17: return this.limbs[5].nearJoint();  //3 Limb R
			case 18: return this.limbs[5].middleJoint();
			case 19: return this.limbs[5].distalJoint();
			case 20: return this.limbs[6].nearJoint();  //4 Limb L
			case 21: return this.limbs[6].middleJoint();
			case 22: return this.limbs[6].distalJoint();
			case 23: return this.limbs[7].nearJoint();  //4 Limb R
			case 24: return this.limbs[7].middleJoint();
			case 25: return this.limbs[7].distalJoint();
			case 26: return this.eyes[0].eyeball();// eyeball L
			case 27: return this.eyes[0].pupil();
			case 28: return this.eyes[1].eyeball();//eyeball R
			case 29: return this.eyes[1].pupil();
			default: throw new IllegalArgumentException("componentNum over index"); 
  		}
  	}
  	
  	private Component mapName2Component(String componentName) {
  		switch(componentName) {
	  		case  HEAD_NAME: return this.head;
			case  BUTT_NAME: return this.butt;
			case  FIRST_LEFT_LIMB_NEAR_NAME: return this.limbs[0].nearJoint();
			case  FIRST_LEFT_LIMB_MIDDLE_NAME: return this.limbs[0].middleJoint();
			case  FIRST_LEFT_LIMB_DISTAL_NAME: return this.limbs[0].distalJoint();
			case  FIRST_RIGHT_LIMB_NEAR_NAME: return this.limbs[1].nearJoint();
			case  FIRST_RIGHT_LIMB_MIDDLE_NAME: return this.limbs[1].middleJoint();
			case  FIRST_RIGHT_LIMB_DISTAL_NAME: return this.limbs[1].distalJoint();
			case  SECOND_LEFT_LIMB_NEAR_NAME: return this.limbs[2].nearJoint();
			case  SECOND_LEFT_LIMB_MIDDLE_NAME: return this.limbs[2].middleJoint();
			case  SECOND_LEFT_LIMB_DISTAL_NAME: return this.limbs[2].distalJoint();
			case  SECOND_RIGHT_LIMB_NEAR_NAME: return this.limbs[3].nearJoint();
			case  SECOND_RIGHT_LIMB_MIDDLE_NAME: return this.limbs[3].middleJoint();
			case  SECOND_RIGHT_LIMB_DISTAL_NAME: return this.limbs[3].distalJoint();
			case  THIRD_LEFT_LIMB_NEAR_NAME: return this.limbs[4].nearJoint();
			case  THIRD_LEFT_LIMB_MIDDLE_NAME: return this.limbs[4].middleJoint();
			case  THIRD_LEFT_LIMB_DISTAL_NAME: return this.limbs[4].distalJoint();
			case  THIRD_RIGHT_LIMB_NEAR_NAME: return this.limbs[5].nearJoint();
			case  THIRD_RIGHT_LIMB_MIDDLE_NAME: return this.limbs[5].middleJoint();
			case  THIRD_RIGHT_LIMB_DISTAL_NAME: return this.limbs[5].distalJoint();
			case  FOURTH_LEFT_LIMB_NEAR_NAME: return this.limbs[6].nearJoint();
			case  FOURTH_LEFT_LIMB_MIDDLE_NAME: return this.limbs[6].middleJoint();
			case  FOURTH_LEFT_LIMB_DISTAL_NAME: return this.limbs[6].distalJoint();
			case  FOURTH_RIGHT_LIMB_NEAR_NAME: return this.limbs[7].nearJoint();
			case  FOURTH_RIGHT_LIMB_MIDDLE_NAME: return this.limbs[7].middleJoint();
			case  FOURTH_RIGHT_LIMB_DISTAL_NAME: return this.limbs[7].distalJoint();
			case  LEFT_EYEBALL_NAME: return this.eyes[0].eyeball();
			case  LEFT_PUPIL_NAME: return this.eyes[0].pupil();
			case  RIGHT_EYEBALL_NAME: return this.eyes[1].eyeball();
			case  RIGHT_PUPIL_NAME:  return this.eyes[1].pupil();
			default: throw new IllegalArgumentException("componentName doesn't exist");
  		}
  	} 	
  	
  	public void setModelStates(final ArrayList<Configuration> config_list) {
  		for (int i = 0; i < config_list.size(); i++) {
  			if ( 0 <= i && i <= 29) {
  				mapNum2Component(i).setAngles(config_list.get(i));
  			}
  		}
  	}
  	
  	public void setModelStates(final Map<String, Configuration> state) {
  		for (Map.Entry<String, Configuration> entry: state.entrySet()) {
  			this.mapName2Component(entry.getKey()).setAngles(entry.getValue());
  		}
  		
  	}
  	
  	/**
     * Prints the joints on the specified PrintStream.
     * 
     * @param printStream
     *          The stream on which to print each of the components.
     */
    public void printJoints(final PrintStream printStream) {
      for (final Component component : this.components) {
        printStream.println(component);
      }
    }

  	
  	public void toggleSelection(int selectionNum) {
  		if ( 0 <= selectionNum && selectionNum <= 29) {
  			Component component = mapNum2Component(selectionNum);
  			if ( this.selectedComponents.contains(component) ) {
  				this.selectedComponents.remove(component);
  				component.setColor(INACTIVE_COLOR);
  			}
  			else {
  		      this.selectedComponents.add(component);
  		      component.setColor(ACTIVE_COLOR);
  		    }
		}
  	}
  	
  	public void changeSelected(Configuration config) {
  		for(Component c: this.selectedComponents) {
  			if(c == components.get(5) ||c == components.get(11)|| c == components.get(17) || c == components.get(23)) {
  				double x = config.xAngle();
  				config.setXAngle(-x);
  				c.changeConfiguration(config);
  				config.setXAngle(x);
  			} else {
  				c.changeConfiguration(config);
  			}
  		}
  	}
  	
  	public Component getEyeball(int i) {
  		return this.eyes[i].eyeball();
  	}
  	
  	public MySpider(final Point3D position, final String name) {
		
		
		// myspider itself as a top level component, need initialization
		super(position, name);
		Point3D random_speed = new Point3D(Math.random(),0,Math.random());
		Point3D norm_speed = random_speed.normalize();
		this.vx = 0.02;
		this.vz = 0;
		
		original_orientation = new Point3D(1,0,0);
		
		 final Component distal1 = new Component(new Point3D(0, 0,
			        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
			        DISTAL_JOINT_HEIGHT, this.glu), FIRST_LEFT_LIMB_DISTAL_NAME);
			    final Component distal2 = new Component(new Point3D(0, 0,
			        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
			        DISTAL_JOINT_HEIGHT, this.glu), FIRST_RIGHT_LIMB_DISTAL_NAME);
			    final Component distal3 = new Component(new Point3D(0, 0,
			        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
			        DISTAL_JOINT_HEIGHT, this.glu), SECOND_LEFT_LIMB_DISTAL_NAME);
			    final Component distal4 = new Component(new Point3D(0, 0,
			        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
			        DISTAL_JOINT_HEIGHT, this.glu), SECOND_RIGHT_LIMB_DISTAL_NAME);
			    final Component distal5 = new Component(new Point3D(0, 0,
			        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
			        DISTAL_JOINT_HEIGHT, this.glu), THIRD_LEFT_LIMB_DISTAL_NAME);
			    final Component distal6 = new Component(new Point3D(0, 0,
				    MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    DISTAL_JOINT_HEIGHT, this.glu), THIRD_RIGHT_LIMB_DISTAL_NAME);
			    final Component distal7 = new Component(new Point3D(0, 0,
				    MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    DISTAL_JOINT_HEIGHT, this.glu), FOURTH_LEFT_LIMB_DISTAL_NAME);
				final Component distal8 = new Component(new Point3D(0, 0,
					MIDDLE_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
					DISTAL_JOINT_HEIGHT, this.glu), FOURTH_RIGHT_LIMB_DISTAL_NAME);


			    // all the middle joints
				final Component middle1 = new Component(new Point3D(0, 0,
					NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
					MIDDLE_JOINT_HEIGHT, this.glu), FIRST_LEFT_LIMB_MIDDLE_NAME);
				final Component middle2 = new Component(new Point3D(0, 0,
				    NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    MIDDLE_JOINT_HEIGHT, this.glu), FIRST_RIGHT_LIMB_MIDDLE_NAME);
				final Component middle3 = new Component(new Point3D(0, 0,
				    NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    MIDDLE_JOINT_HEIGHT, this.glu), SECOND_LEFT_LIMB_MIDDLE_NAME);
				final Component middle4 = new Component(new Point3D(0, 0,
				    NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    MIDDLE_JOINT_HEIGHT, this.glu), SECOND_RIGHT_LIMB_MIDDLE_NAME);
				final Component middle5 = new Component(new Point3D(0, 0,
				    NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
				    MIDDLE_JOINT_HEIGHT, this.glu), THIRD_LEFT_LIMB_MIDDLE_NAME);
				final Component middle6 = new Component(new Point3D(0, 0,
					NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
					MIDDLE_JOINT_HEIGHT, this.glu), THIRD_RIGHT_LIMB_MIDDLE_NAME);
				final Component middle7 = new Component(new Point3D(0, 0,
					NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
					MIDDLE_JOINT_HEIGHT, this.glu), FOURTH_LEFT_LIMB_MIDDLE_NAME);
				final Component middle8 = new Component(new Point3D(0, 0,
					NEAR_JOINT_HEIGHT), new RoundedCylinder(LIMB_RADIUS,
					MIDDLE_JOINT_HEIGHT, this.glu), FOURTH_RIGHT_LIMB_MIDDLE_NAME);

			    // all the near joints, displaced by various amounts from the palm
				final Component near1 = new Component(new Point3D(-0.10,0,HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu),
						FIRST_LEFT_LIMB_NEAR_NAME);
				final Component near2 = new Component(new Point3D(-0.10,0,-HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu), 
						FIRST_RIGHT_LIMB_NEAR_NAME);
				final Component near3 = new Component(new Point3D(-0.02,0,HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu), 
						SECOND_LEFT_LIMB_NEAR_NAME);
				final Component near4 = new Component(new Point3D(-0.02,0,-HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu),
						SECOND_RIGHT_LIMB_NEAR_NAME);
				final Component near5 = new Component(new Point3D(0.05, 0,HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu),
						THIRD_LEFT_LIMB_NEAR_NAME);
				final Component near6 = new Component(new Point3D(0.05, 0,-HEAD_RADIUS),
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu), 
						THIRD_RIGHT_LIMB_NEAR_NAME);
				final Component near7 = new Component(new Point3D(0.1,0, HEAD_RADIUS), 
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu), 
						FOURTH_LEFT_LIMB_NEAR_NAME);
				final Component near8 = new Component(new Point3D(0.1,0,-HEAD_RADIUS),
						new RoundedCylinder(LIMB_RADIUS,NEAR_JOINT_HEIGHT, this.glu), 
						FOURTH_RIGHT_LIMB_NEAR_NAME);
	
		
	    // put together the fingers for easier selection by keyboard input later on
	    this.limbs = new Limb[] { new Limb(near1, middle1, distal1),
	        new Limb(near2, middle2, distal2),
	        new Limb(near3, middle3, distal3),
	        new Limb(near4, middle4, distal4),
	        new Limb(near5, middle5, distal5),
	        new Limb(near6, middle6, distal6),
	        new Limb(near7, middle7, distal7),
	        new Limb(near8, middle8, distal8)};
	
	    final Component eyeball1 = new Component(new Point3D(HEAD_RADIUS,0,0.15),
	    		new Ellipsoid(EYE_RADIUS,this.glu),LEFT_EYEBALL_NAME);
	    final Component eyeball2 = new Component(new Point3D(HEAD_RADIUS,0,-0.15),
	    		new Ellipsoid(EYE_RADIUS,this.glu),RIGHT_EYEBALL_NAME);
	    
	    final Component pupil1 = new Component(new Point3D(EYE_RADIUS,0,0),
	    		new Ellipsoid(PUPIL_RADIUS,this.glu),LEFT_PUPIL_NAME);
	    final Component pupil2 = new Component(new Point3D(EYE_RADIUS,0, 0),
	    		new Ellipsoid(PUPIL_RADIUS,this.glu),RIGHT_PUPIL_NAME);
	    
	    this.eyes = new Eye[] {new Eye(eyeball1,pupil1),
	    		new Eye(eyeball2,pupil2)
	    };
	    
	    eyeball1.setColor(EYE_COLOR);
	    eyeball2.setColor(EYE_COLOR);
	    pupil1.setColor(PUPIL_COLOR);
	    pupil2.setColor(PUPIL_COLOR);
	    //eyeball1.rotate(Axis.X, 90);
	    //eyeball2.rotate(Axis.X,-90);
	    
	    // the hand, which models the wrist joint
	    /*
	    this.head = new Component(new Point3D(0, 0, 0), new Ellipsoid(
	        HEAD_RADIUS, this.glut), HEAD_NAME);

	    // the butt, which models the elbow joint
	    this.butt = new Component(new Point3D(BUTT_RADIUS+HEAD_RADIUS-0.2,0,0),
	        (Displayable) new Ellipsoid(BUTT_RADIUS, this.glut),
	        BUTT_NAME);
	      */
	    
	    this.head = new Component(new Point3D(0, 0, 0), new Ellipsoid(
		        HEAD_RADIUS, this.glu), HEAD_NAME);

		    // the butt, which models the elbow joint
	    this.butt = new Component(new Point3D(-BUTT_RADIUS-HEAD_RADIUS+0.2,0,0),
		        (Displayable) new Ellipsoid(BUTT_RADIUS, this.glu),
		        BUTT_NAME);
	    
	    
	    

	    this.addChild(this.head);
	    // the funny bone's connected to the...forearm
	    this.head.addChild(this.butt);
	   
	    this.head.addChild(eyeball1);
	    this.head.addChild(eyeball2);
	    
	    eyeball1.addChild(pupil1);
	    eyeball2.addChild(pupil2);
	    
	    // the wrist bone's connected to the...fingers
	    this.head.addChildren(near1, near2, near3, near4, near5, near6, near7, near8);
	    near1.addChild(middle1);
	    near2.addChild(middle2);
	    near3.addChild(middle3);
	    near4.addChild(middle4);
	    near5.addChild(middle5);
	    near6.addChild(middle6);
	    near7.addChild(middle7);
	    near8.addChild(middle8);
	    middle1.addChild(distal1);
	    middle2.addChild(distal2);
	    middle3.addChild(distal3);
	    middle4.addChild(distal4);
	    middle5.addChild(distal5);
	    middle6.addChild(distal6);
	    middle7.addChild(distal7);
	    middle8.addChild(distal8);
	    
	   
	    near1.rotate(Axis.Y,-20); 
	    //near1.rotate(Axis.X,-10);
	    middle1.rotate(Axis.X,20);
	    distal1.rotate(Axis.X,40);
	    
	    near2.rotate(Axis.Y, 200);
	    //near2.rotate(Axis.X,-10);
	    middle2.rotate(Axis.X,20);
	    distal2.rotate(Axis.X,40);
	    
	    near3.rotate(Axis.Y,-10);
	    //near3.rotate(Axis.Y,-10);
	    middle3.rotate(Axis.X,20);
	    distal3.rotate(Axis.X,40);
	    
	    near4.rotate(Axis.Y, 190);
	    //near4.rotate(Axis.X,-10);
	    middle4.rotate(Axis.X,20);
	    distal4.rotate(Axis.X,40);
	    
	    near5.rotate(Axis.Y,10);
	    //near5.rotate(Axis.X,-10); 
	    middle5.rotate(Axis.X,20);
	    distal5.rotate(Axis.X,40);
	    
	    near6.rotate(Axis.Y,170);
	    //near6.rotate(Axis.X,-10); 
	    middle6.rotate(Axis.X,20);
	    distal6.rotate(Axis.X,40);   
	    
	    near7.rotate(Axis.Y,20);
	    //near7.rotate(Axis.X,-10);
	    middle7.rotate(Axis.X,20);
	    distal7.rotate(Axis.X,40);
	    
	    near8.rotate(Axis.Y,160);
	    //near8.rotate(Axis.X,-10);
	    middle8.rotate(Axis.X,20);
	    distal8.rotate(Axis.X,40);

	    
	    // set rotation limits for the butt
	    this.butt.setXPositiveExtent(30);
	    this.butt.setXNegativeExtent(-30);
	    this.butt.setYPositiveExtent(30);
	    this.butt.setYNegativeExtent(-30);
	    this.butt.setZPositiveExtent(30);
	    this.butt.setZNegativeExtent(-30);

	    // set rotation limits for the head
	    this.head.setXPositiveExtent(20);
	    this.head.setXNegativeExtent(-20);
	    this.head.setYPositiveExtent(20);
	    this.head.setYNegativeExtent(-20);
	    this.head.setZPositiveExtent(20);
	    this.head.setZNegativeExtent(-20);

	    // set rotation limits for the palm joints of the fingers
	    for (final Component nearJoint : Arrays.asList(near1, near2, near3, near4,
	    		near5, near6, near7, near8)) {
	      nearJoint.setXPositiveExtent(30);
	      nearJoint.setXNegativeExtent(-30);
	      nearJoint.setZPositiveExtent(15);
	      nearJoint.setZNegativeExtent(-15);
	    }
	    near1.setYPositiveExtent(0);
	    near1.setYNegativeExtent(-40);
	    
	    near2.setYPositiveExtent(220);
	    near2.setYNegativeExtent(180);
	    
	    near3.setYPositiveExtent(10);
	    near3.setYNegativeExtent(-30);
	    
	    near4.setYPositiveExtent(210);
	    near4.setYNegativeExtent(170);
	    
	    near5.setYPositiveExtent(30);
	    near5.setYNegativeExtent(-10);
	    
	    near6.setYPositiveExtent(190);
	    near6.setYNegativeExtent(150);
	    
	    near7.setYPositiveExtent(40);
	    near7.setYNegativeExtent(0);
	    
	    near8.setYPositiveExtent(180);
	    near8.setYNegativeExtent(140);

	    // and set the rotation limits for the palm joint of the thumb
	    
	   

	    // set rotation limits for the middle joints of the finger
	    for (final Component middleJoint : Arrays.asList(middle1, middle2,
	        middle3, middle4, middle5, middle6, middle7, middle8)) {
	      middleJoint.setXPositiveExtent(110);
	      middleJoint.setXNegativeExtent(20);
	      middleJoint.setYPositiveExtent(0);
	      middleJoint.setYNegativeExtent(0);
	      middleJoint.setZPositiveExtent(0);
	      middleJoint.setZNegativeExtent(0);
	    }

	    // set rotation limits for the distal joints of the finger
	    for (final Component distalJoint : Arrays.asList(distal1, distal2,
	        distal3, distal4, distal5, distal6, distal7,distal8)) {
	      distalJoint.setXPositiveExtent(80);
	      distalJoint.setXNegativeExtent(40);
	      distalJoint.setYPositiveExtent(0);
	      distalJoint.setYNegativeExtent(0);
	      distalJoint.setZPositiveExtent(0);
	      distalJoint.setZNegativeExtent(0);
	    }
	    
	    for(final Component distalJoint: Arrays.asList(eyeball1,eyeball2)) {
	    	distalJoint.setXPositiveExtent(180);
		    distalJoint.setXNegativeExtent(-180);
		    distalJoint.setYPositiveExtent(80);
		    distalJoint.setYNegativeExtent(-80);
		    distalJoint.setZPositiveExtent(80);
		    distalJoint.setZNegativeExtent(-80);
	    }

	    // create the list of all the components for debugging purposes
	    this.components = Arrays.asList(this.head, this.butt,
	    	near1, middle1, distal1, near2, middle2, distal2,
	    	near3, middle3, distal3, near4, middle4, distal4, 
	    	near5, middle5, distal5, near6, middle6, distal6, 
	    	near7, middle7, distal7, near8, middle8, distal8, 
	        eyeball1, pupil1, eyeball2, pupil2
	        );
	}
	
	
	public class Eye{
		private Component eyeball;
		private Component pupil;
		private List<Component> eyeParts;
		public Eye(final Component eyeball, final Component pupil) {
		      this.eyeball = eyeball;
		      this.pupil = pupil;

		      this.eyeParts = Collections.unmodifiableList(Arrays.asList(this.eyeball,this.pupil));
		}
		
		List<Component> joints() {
		      return this.eyeParts;
		}
		Component eyeball() {
			return this.eyeball;
		}
		Component pupil() {
			return this.pupil;
		}
	}
	
	private class Limb {
	    /** The distal joint of this finger. */
	    private  Component distalJoint;
	    /** The list of all the joints in this finger. */
	    private  List<Component> joints;
	    /** The middle joint of this finger. */
	    private Component middleJoint;
	    /** The palm joint of this finger. */
	    private Component nearJoint;

	    /**
	     * Instantiates this finger with the three specified joints.
	     * 
	     * @param palmJoint
	     *          The palm joint of this finger.
	     * @param middleJoint
	     *          The middle joint of this finger.
	     * @param distalJoint
	     *          The distal joint of this finger.
	     * @return 
	     */
	    public Limb(final Component nearJoint, final Component middleJoint,
	        final Component distalJoint) {
	      this.nearJoint = nearJoint;
	      this.middleJoint = middleJoint;
	      this.distalJoint = distalJoint;

	      this.joints = Collections.unmodifiableList(Arrays.asList(this.nearJoint,
	          this.middleJoint, this.distalJoint));
	    }

	    /**
	     * Gets the distal joint of this finger.
	     * 
	     * @return The distal joint of this finger.
	     */
	    Component distalJoint() {
	      return this.distalJoint;
	    }

	    /**
	     * Gets an unmodifiable view of the list of the joints of this finger.
	     * 
	     * @return An unmodifiable view of the list of the joints of this finger.
	     */
	    List<Component> joints() {
	      return this.joints;
	    }

	    /**
	     * Gets the middle joint of this finger.
	     * 
	     * @return The middle joint of this finger.
	     */
	    Component middleJoint() {
	      return this.middleJoint;
	    }

	    /**
	     * Gets the palm joint of this finger.
	     * 
	     * @return The palm joint of this finger.
	     */
	    Component nearJoint() {
	      return this.nearJoint;
	    }
	}
	/*
	 * 
	 */
	public void animationUpdate(final GL2 gl) {
		if(this.time_counter == 200) this.time_counter=0;
		if(this.time_counter % 20 == 0) {
			this.setModelStates(this.testCases.next());
		}
		
		face2Direction();
		this.time_counter++;
		movePosition();
		
	}

	
	public void movePosition() {
		Point3D presentP = position();
		if(presentP.x()+HEAD_RADIUS >= 2 || presentP.x()-HEAD_RADIUS <= -2) {
			  vx = -vx;
		  }
		  
		  if(presentP.z()+HEAD_RADIUS  >= 2 || presentP.z()-HEAD_RADIUS <= -2) {
			  vz = -vz;
		  }
		
		setPosition(new Point3D(presentP.x()+vx, presentP.y(), presentP.z()+vz));
	}
	
	
	public void face2Direction() {
		this.target_orientation = new Point3D(vx,vy,vz);
		  
		Point3D norm_ori = original_orientation.normalize();
		Point3D norm_tar = target_orientation.normalize();
		
		//System.out.println(original_orientation);
		//System.out.println(target_orientation);
		
		if(norm_ori.isParallel(norm_tar) && (!norm_ori.equals(norm_tar))) {
			
			//rotate 180, rotate axis (-norm_tar.y(),norm_tar.x(),0)
			Point3D rotation_matrix = norm_ori.findVerticalVertex();
			Point3D norm_rotate_matrix = rotation_matrix.normalize();
			//System.out.println("parallel"+norm_rotate_matrix);
			Quaternion q = new Quaternion((float)(Math.cos(Math.toRadians(90))),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.x()),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.y()),
					(float)(Math.sin(Math.toRadians(90))*norm_rotate_matrix.z()) );
			this.orientation = q.multiply(this.orientation);
			this.orientation.normalize();
			this.preMatrix = this.orientation.to_matrix();
			this.original_orientation = this.target_orientation;
			//System.out.println("180du");
		}
		else if (!norm_ori.equals(norm_tar) ) {
			
			
			Point3D rotation_matrix = norm_ori.crossProduct(norm_tar);
			Point3D norm_rotate_matrix = rotation_matrix.normalize();
			//System.out.println("rotate matrix "+norm_rotate_matrix);
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
	
	
	
	
	public void potentialFunction(ArrayList<Component> vivarium) {
		Point3D currentP = this.position();
		double dx=0,dy=0,dz=0;
		double predator_weight = 0.5;
		double food_weight = 0.5;
		double wall_weight =0.02;
		for (Component example : vivarium) {
			
			if(example == this) continue;
			
			if(example instanceof MyFood) {
				Point3D foodP = example.position();
				Point3D food_xz = new Point3D(foodP.x(), 0, foodP.z());
				Point3D attraction = food_xz.minus( this.position());
				Point3D delta_gaussion_att = attraction.deltaGaussion();
				dx += delta_gaussion_att.x() * food_weight;
				//dy += delta_gaussion_att.y() * food_weight;
				dz += delta_gaussion_att.z() * food_weight;
				break;
			}
		}
		double repulsive_x1 = this.position().x()+2;
		double repulsive_x1_gaus = repulsive_x1*Math.exp(- repulsive_x1 * repulsive_x1);
		dx += repulsive_x1_gaus*wall_weight;
		double repulsive_x2 = this.position().x()-2;
		double repulsive_x2_gaus = repulsive_x2*Math.exp(- repulsive_x2 * repulsive_x2);
		dx += repulsive_x2_gaus * wall_weight;
		
		
		double repulsive_z1 = this.position().z()+2;
		double repulsive_z1_gaus = repulsive_z1*Math.exp(- repulsive_z1 * repulsive_z1);
		dz += repulsive_z1_gaus * wall_weight;
		double repulsive_z2 = this.position().z()-2;
		double repulsive_z2_gaus = repulsive_z2*Math.exp(- repulsive_z2 * repulsive_z2);
		dz += repulsive_z2_gaus * wall_weight;
		
		Point3D new_speed = new Point3D(vx+dx,0,vz+dz);
		Point3D new_speed_norm = new_speed.normalize();
		this.vx = new_speed_norm.x()*0.02;
		//this.vy = new_speed_norm.y()*0.02;
		this.vz = new_speed_norm.z()*0.02;
		//System.out.println(this.position());
	}
	
	
}


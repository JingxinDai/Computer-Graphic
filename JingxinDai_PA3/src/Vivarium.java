/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA3
 * @due 11/13/2019
 * @brief The Vivarium class, contains the tank and creatures.
 */

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import java.util.*;

public class Vivarium implements Displayable, Animate {
	private Tank tank;
	public ArrayList<Component> vivarium = new ArrayList<Component>();
	public ArrayList<Component> food = new ArrayList<Component>();
	public Component bird;
	//public ArrayList<Component> foods = new ArrayList<Component>();
	public Vivarium() {
		tank = new Tank(4.0f, 4.0f, 4.0f);

 		//vivarium.add(new MyBird(new Point3D(0,0,0),"my test bird"));
		vivarium.add(new MyButterfly(new Point3D(0.9,0.9,0.9),"my test butterfly"));
		vivarium.add(new MyButterfly(new Point3D(1,1,1),"my test butterfly"));
		vivarium.add(new MyButterfly(new Point3D(1.1,1.1,1.1),"my test butterfly"));
		//vivarium.add(new MyFood(new Point3D(0,2,0),"food"));
		vivarium.add(new MySpider(new Point3D(0,-2+0.45*0.5,0), "my test spider"));
	}
	
	public void addFood(GL2 gl,boolean addFood_key) {
		
		if(addFood_key) {	
			MyFood food = new MyFood(new Point3D(-1.5+3*Math.random(),2,-1.5+3*Math.random()),"food");
			food.initialize(gl);
			vivarium.add(food); // new MyFood(new Point3D(0,2,0),"food"));
		}
	}
	
	public void addBird(GL2 gl,boolean addBird_key) {
		
		if(addBird_key) {	
			MyBird bird = new MyBird(new Point3D(0,0,0),"my bird");
			bird.initialize(gl);
			vivarium.add(bird); // new MyFood(new Point3D(0,2,0),"food"));
		}
	}
	
	public void addButterfly(GL2 gl,boolean addButterfly_key) {
		
		if(addButterfly_key) {	
			MyButterfly butterfly = new MyButterfly(new Point3D(-1.5+3*Math.random(),-1.5+3*Math.random(),-1.5+3*Math.random()),"food");
			butterfly.initialize(gl);
			vivarium.add(butterfly); // new MyFood(new Point3D(0,2,0),"food"));
		}
	}
	
	public void initialize(GL2 gl) {
		tank.initialize(gl);
		for (Component object : vivarium) {
			object.initialize(gl);
		}
	}

	public void update(GL2 gl) {
		tank.update(gl);
		Iterator iterator = vivarium.iterator();
		while(iterator.hasNext()) {
			Component object = (Component) iterator.next();
			object.update(gl);
			if(object instanceof MyFood) {
				if(((MyFood) object).isEaten == true) {
					iterator.remove();
					// vivarium.remove(object);
				}
			}
			if(object instanceof MyButterfly) {
				if(((MyButterfly) object).isEaten == true) {
					iterator.remove();
					// vivarium.remove(object);
				}
			}
		}
	}

	public void draw(GL2 gl) {
		tank.draw(gl);
		for (Component object : vivarium) {
			object.draw(gl);
		}
	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		// assign configurations in config_list to all Components in here
	}

	@Override
	public void animationUpdate(GL2 gl) {
		for (Component example : vivarium) {
			if (example instanceof Animate) {
				((Animate) example).animationUpdate(gl);
					
			}
			if(example instanceof MyButterfly) {
				//((MyButterfly) example).groupBehavior(vivarium,2);
				((MyButterfly) example).potentialFunction(vivarium);
				//((MyButterfly) example).collisionButterfly(vivarium);
				((MyButterfly) example).collisionPredator(vivarium);
			}
			else if(example instanceof MyBird) {
				((MyBird) example).potentialFunction(vivarium);
			}
			else if(example instanceof MyFood) {
				((MyFood) example).collisionButterfly(vivarium);
			}
			else if(example instanceof MySpider) {
				((MySpider) example).potentialFunction(vivarium);
			}

			
		}
	}
}

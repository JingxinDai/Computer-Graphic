/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA2
 * @due 10/15/2019
 * @brief test case for display spider poses
 */
import java.util.HashMap;
import java.util.Map;

public class TestCasesSpider extends CyclicIterator<Map<String, Configuration>> {
	public static String FIRST_LEFT_LIMB_NEAR_NAME = "limb 1 left near";
	public static String FIRST_LEFT_LIMB_MIDDLE_NAME = "limb 1 left middle";
  	public static String FIRST_LEFT_LIMB_DISTAL_NAME = "limb 1 left distal";
  	public static String FIRST_RIGHT_LIMB_NEAR_NAME = "limb 1 right near";
	public static String FIRST_RIGHT_LIMB_MIDDLE_NAME = "limb 1 right middle";
  	public static String FIRST_RIGHT_LIMB_DISTAL_NAME = "limb 1 right distal";
  	
  	public static String SECOND_LEFT_LIMB_NEAR_NAME = "limb 2 left near";
	public static String SECOND_LEFT_LIMB_MIDDLE_NAME = "limb 2 left middle";
  	public static String SECOND_LEFT_LIMB_DISTAL_NAME = "limb 2 left distal";
  	public static String SECOND_RIGHT_LIMB_NEAR_NAME = "limb 2 right near";
	public static String SECOND_RIGHT_LIMB_MIDDLE_NAME = "limb 2 right middle";
  	public static String SECOND_RIGHT_LIMB_DISTAL_NAME = "limb 2 right distal";
  	
  	public static String THIRD_LEFT_LIMB_NEAR_NAME = "limb 3 left near";
	public static String THIRD_LEFT_LIMB_MIDDLE_NAME = "limb 3 left middle";
  	public static String THIRD_LEFT_LIMB_DISTAL_NAME = "limb 3 left distal";
  	public static String THIRD_RIGHT_LIMB_NEAR_NAME = "limb 3 right near";
	public static String THIRD_RIGHT_LIMB_MIDDLE_NAME = "limb 3 right middle";
  	public static String THIRD_RIGHT_LIMB_DISTAL_NAME = "limb 3 right distal";
  	
  	public static String FOURTH_LEFT_LIMB_NEAR_NAME = "limb 4 left near";
	public static String FOURTH_LEFT_LIMB_MIDDLE_NAME = "limb 4 left middle";
  	public static String FOURTH_LEFT_LIMB_DISTAL_NAME = "limb 4 left distal";
  	public static String FOURTH_RIGHT_LIMB_NEAR_NAME = "limb 4 right near";
	public static String FOURTH_RIGHT_LIMB_MIDDLE_NAME = "limb 4 right middle";
  	public static String FOURTH_RIGHT_LIMB_DISTAL_NAME = "limb 4 right distal";
  	
  	public static String HEAD_NAME = "head";
  	public static String BUTT_NAME = "butt";
  	
  	public static String RIGHT_EYEBALL_NAME = "right eyeball";
  	public static String LEFT_EYEBALL_NAME = "left eyeball";
  	public static String RIGHT_PUPIL_NAME = "right pupil";
  	public static String LEFT_PUPIL_NAME = "left pupil";
  	
  	Map<String, Configuration> stop() {
		return this.stop;
	}

	private final Map<String, Configuration> stop;

	@SuppressWarnings("unchecked")
	TestCasesSpider () {
		this.stop = new HashMap<String, Configuration>();
		final Map<String, Configuration> pose1 = new HashMap<String, Configuration>();
		final Map<String, Configuration> pose2 = new HashMap<String, Configuration>();
		final Map<String, Configuration> pose3 = new HashMap<String, Configuration>();
		final Map<String, Configuration> pose4 = new HashMap<String, Configuration>();
		final Map<String, Configuration> pose5 = new HashMap<String, Configuration>();
		
		super.add(stop, pose1, pose2, pose3, pose4, pose5);
		//--------------------------stop-----------------------------------
		stop.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		stop.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
				
		stop.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0, -20, 0));
		stop.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		stop.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0, 200, 0));
		stop.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
				
		stop.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,-10,0));
		stop.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		stop.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,190,0));
		stop.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
				
		stop.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,10,0));
		stop.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		stop.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,170,0));
		stop.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
				
		stop.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,20,0));
		stop.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		stop.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,160,0));
		stop.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		stop.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
				
		stop.put(LEFT_EYEBALL_NAME, new BaseConfiguration(0,0,0));
		stop.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		stop.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(0,0,0));
		stop.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		//--------------------------POSE1-----------------------------------
		pose1.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		pose1.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
		
		pose1.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(30, -20, 0));
		pose1.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(66,0,0));
		pose1.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
		pose1.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-30, 200, 0));
		pose1.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(66,0,0));
		pose1.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
		
		pose1.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,-10,0));
		pose1.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose1.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,190,0));
		pose1.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose1.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,10,0));
		pose1.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose1.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,170,0));
		pose1.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose1.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,20,0));
		pose1.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose1.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0,160,0));
		pose1.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose1.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose1.put(LEFT_EYEBALL_NAME, new BaseConfiguration(0,8,10));
		pose1.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		pose1.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(0,8,10));
		pose1.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		
		//--------------------------POSE2-----------------------------------
		pose2.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		pose2.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
		
		pose2.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0, -20, 15));
		pose2.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose2.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(0, 200, 0));
		pose2.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose2.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,-10,15));
		pose2.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose2.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,190,15));
		pose2.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose2.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,10,15));
		pose2.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose2.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,170,15));
		pose2.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose2.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,20,0));
		pose2.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose2.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,160,0));
		pose2.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose2.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		
		pose2.put(LEFT_EYEBALL_NAME, new BaseConfiguration(0,8,-55));
		pose2.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		pose2.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(0,8,-55));
		pose2.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		
		//--------------------------POSE3-----------------------------------
		pose3.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		pose3.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
				
		pose3.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30, -20, 15));
		pose3.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30, 200, 0));
		pose3.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
				
		pose3.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,-10,15));
		pose3.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
		pose3.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,190,15));
		pose3.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
				
		pose3.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,10,15));
		pose3.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
		pose3.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,170,15));
		pose3.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
				
		pose3.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(-30,20,0));
		pose3.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(30,160,0));
		pose3.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(30,0,0));
		pose3.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(30,0,0));
				
		pose3.put(LEFT_EYEBALL_NAME, new BaseConfiguration(0,20,20));
		pose3.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		pose3.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(0,-20,-20));
		pose3.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		
		//--------------------------POSE4-----------------------------------
		pose4.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		pose4.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
				
		pose4.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0, -40, 0));
		pose4.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose4.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose4.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-22, 220, 0));
		pose4.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(72,0,0));
		pose4.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
				
		pose4.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,-30,0));
		pose4.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose4.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose4.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-22,210,0));
		pose4.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(72,0,0));
		pose4.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
				
		pose4.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,28,0));
		pose4.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose4.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose4.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-22,190,0));
		pose4.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(72,0,0));
		pose4.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
				
		pose4.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(0,40,-11));
		pose4.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(20,0,0));
		pose4.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(40,0,0));
		pose4.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-22,180,-11));
		pose4.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(72,0,0));
		pose4.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(80,0,0));
				
		pose4.put(LEFT_EYEBALL_NAME, new BaseConfiguration(0,-8,9));
		pose4.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		pose4.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(0,-8,9));
		pose4.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		
		//--------------------------POSE5-----------------------------------
		pose5.put(HEAD_NAME, new BaseConfiguration(0, 0, 0));
		pose5.put(BUTT_NAME, new BaseConfiguration(0, 0, 0));
						
		pose5.put(FIRST_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(8, -20, 0));
		pose5.put(FIRST_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(FIRST_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
		pose5.put(FIRST_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-8, 200, 0));
		pose5.put(FIRST_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(FIRST_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
						
		pose5.put(SECOND_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(8,-10,0));
		pose5.put(SECOND_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(SECOND_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
		pose5.put(SECOND_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-8,190,0));
		pose5.put(SECOND_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(SECOND_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
						
		pose5.put(THIRD_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(8,10,0));
		pose5.put(THIRD_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(THIRD_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
		pose5.put(THIRD_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-8,170,0));
		pose5.put(THIRD_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(THIRD_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
						
		pose5.put(FOURTH_LEFT_LIMB_NEAR_NAME, new BaseConfiguration(8,20,0));
		pose5.put(FOURTH_LEFT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(FOURTH_LEFT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
		pose5.put(FOURTH_RIGHT_LIMB_NEAR_NAME, new BaseConfiguration(-8,160,0));
		pose5.put(FOURTH_RIGHT_LIMB_MIDDLE_NAME, new BaseConfiguration(88,0,0));
		pose5.put(FOURTH_RIGHT_LIMB_DISTAL_NAME, new BaseConfiguration(58,0,0));
						
		pose5.put(LEFT_EYEBALL_NAME, new BaseConfiguration(-20,-20,-20));
		pose5.put(LEFT_PUPIL_NAME, new BaseConfiguration(0,0,0));
		pose5.put(RIGHT_EYEBALL_NAME, new BaseConfiguration(20,20,20));
		pose5.put(RIGHT_PUPIL_NAME, new BaseConfiguration(0,0,0));
	}
}

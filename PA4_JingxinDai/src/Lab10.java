/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * */
//****************************************************************************
//       Example Main Program for CS480 PA1
//****************************************************************************
// Description: 
//   
//   This is a template program for the sketching tool.  
//
//     LEFTMOUSE: draw line segments 
//     RIGHTMOUSE: draw triangles 
//
//     The following keys control the program:
//
//		Q,q: quit 
//		C,c: clear polygon (set vertex count=0)
//		R,r: randomly change the color
//		S,s: toggle the smooth shading for triangle 
//			 (no smooth shading by default)
//		T,t: show testing examples
//		>:	 increase the step number for examples
//		<:   decrease the step number for examples
//
//****************************************************************************
// History :
//   Aug 2004 Created by Jianming Zhang based on the C
//   code by Stan Sclaroff
//   Nov 2014 modified to include test cases
//   Nov 5, 2019 Updated by Zezhou Sun
//   Dec 10,2019 Updated by Jingxin Dai
//


import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.*; 
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
//import java.io.File;
//import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import javax.imageio.ImageIO;
//import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl


public class Lab10 extends JFrame
	implements GLEventListener, KeyListener, MouseListener, MouseMotionListener
{
	
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_WINDOW_WIDTH=512;
	private final int DEFAULT_WINDOW_HEIGHT=512;
	private final float DEFAULT_LINE_WIDTH=1.0f;

	private GLCapabilities capabilities;
	private GLCanvas canvas;
	private FPSAnimator animator;

	final private int numTestCase;
	private int testCase;
	private BufferedImage buff;
	@SuppressWarnings("unused")
	private ColorType color;
	private Random rng;
	
	 // specular exponent for materials
	private int ns=5; 
	
	private ArrayList<Point2D> lineSegs;
	private ArrayList<Point2D> triangles;
	private boolean doSmoothShading;
	private int Nsteps;
	
	private boolean sphereKey;
	private boolean ellipsoidKey;
	private boolean cylinderKey;
	private boolean cubeKey;
	private boolean torusKey;
	
	private boolean flatRenderKey = true;
	private boolean gouraudRenderKey = false;
	private boolean phongRenderKey = false;
	
	private boolean lightAmbientKey = true;
	private boolean lightSpecularKey = true;
	private boolean lightDiffuseKey = true;
	private boolean lightInfiniteKey=false;
	private boolean lightPointKey;
	private boolean lightSpotKey;
	
	private LightInfinite lt = new LightInfinite(new ColorType(1.0f, 1.0f, 1.0f), new Point3D(1.0f, 1.0f, 1.0f));
	private LightAmbient  la = new LightAmbient(new ColorType(0.2f,0.2f,0.2f));
	private LightPoint lp = new LightPoint(new ColorType(0,0,1.0f),new Point3D(900,900,900));
	private LightSpot ls = new LightSpot(new ColorType(0,1.0f,0),new Point3D(1,0,0),new Point3D(500,300,300));

	/** The quaternion which controls the rotation of the world. */
    private Quaternion viewing_quaternion = new Quaternion();
    private Point3D viewing_center = new Point3D((float)(DEFAULT_WINDOW_WIDTH/2),(float)(DEFAULT_WINDOW_HEIGHT/2),(float)0.0);
    /** The last x and y coordinates of the mouse press. */
    private int last_x = 0, last_y = 0;
    /** Whether the world is being rotated. */
    private boolean rotate_world = false;
    private int x_move;
    private int y_move;
    private int z_move;
    
    /** Random colors **/
    private ColorType[] colorMap = new ColorType[100];
    private Random rand = new Random();
    
    private BufferedImage top_environment;
    private BufferedImage bottom_environment;
    private BufferedImage left_environment;
    private BufferedImage right_environment;
    private BufferedImage front_environment;
    private BufferedImage back_environment;
    
    
	public Lab10()
	{
	    capabilities = new GLCapabilities(null);
	    capabilities.setDoubleBuffered(true);  // Enable Double buffering

	    canvas  = new GLCanvas(capabilities);
	    canvas.addGLEventListener(this);
	    canvas.addMouseListener(this);
	    canvas.addMouseMotionListener(this);
	    canvas.addKeyListener(this);
	    canvas.setAutoSwapBufferMode(true); // true by default. Just to be explicit
	    canvas.setFocusable(true);
	    getContentPane().add(canvas);
	    
	    
	    animator = new FPSAnimator(canvas, 60); // drive the display loop @ 60 FPS

	    numTestCase = 4;
	    testCase = 0;
	    Nsteps = 12;

	    setTitle("CS480/680 Lab 11");
	    setSize( DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    setResizable(false);
	    
	    rng = new Random();
	    color = new ColorType(1.0f,0.0f,0.0f);
	    lineSegs = new ArrayList<Point2D>();
	    triangles = new ArrayList<Point2D>();
	    doSmoothShading = false;

	    for (int i=0; i<100; i++) {
	    	this.colorMap[i] = new ColorType(i*0.005f+0.5f, i*-0.005f+1f, i*0.0025f+0.75f);
	    }
	    
	    try
	    {
	    	top_environment = ImageIO.read(new File("src/top.jpg"));
	    	bottom_environment = ImageIO.read(new File("src/bottom.jpg"));
	    	front_environment = ImageIO.read(new File("src/front.jpg"));
	    	back_environment = ImageIO.read(new File("src/back.jpg"));
	    	right_environment = ImageIO.read(new File("src/right.jpg"));
	    	left_environment = ImageIO.read(new File("src/left.jpg"));
	    	
	    } catch (IOException e)
	    {
	    	System.out.println("Error: reading texture image.");
	    	e.printStackTrace();
	    }
	}

	public void run()
	{
		animator.start();
	}

	public static void main( String[] args )
	{
	    Lab10 P = new Lab10();
	    P.run();
	}

	//*********************************************** 
	//  GLEventListener Interfaces
	//*********************************************** 
	public void init( GLAutoDrawable drawable) 
	{
	    GL gl = drawable.getGL();
	    gl.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f);
	    gl.glLineWidth( DEFAULT_LINE_WIDTH );
	    Dimension sz = this.getContentPane().getSize();
	    SketchBase sketchBase = new SketchBase(sz.width,sz.height);
	    buff = new BufferedImage(sz.width,sz.height,BufferedImage.TYPE_3BYTE_BGR);
	    clearPixelBuffer();
	}

	// Redisplaying graphics
	public void display(GLAutoDrawable drawable)
	{
	    GL2 gl = drawable.getGL().getGL2();
	    WritableRaster wr = buff.getRaster();
	    DataBufferByte dbb = (DataBufferByte) wr.getDataBuffer();
	    byte[] data = dbb.getData();

	    gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
	    gl.glDrawPixels (buff.getWidth(), buff.getHeight(),
                GL2.GL_BGR, GL2.GL_UNSIGNED_BYTE,
                ByteBuffer.wrap(data));
        drawTestCase();
	}

	// Window size change
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		// deliberately left blank
	}
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
	      boolean deviceChanged)
	{
		// deliberately left blank
	}
	
	void clearPixelBuffer()
	{
		lineSegs.clear();
    	triangles.clear();
		Graphics2D g = buff.createGraphics();
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
	    g.dispose();
	}
	
	// drawTest
	void drawTestCase()
	{  
		/* clear the window and vertex state */
		clearPixelBuffer();
		SketchBase.resetDbuff();
	  
		//System.out.printf("Test case = %d\n",testCase);
		float radius = (float)50.0;
		
		Material mat = new Material(new ColorType(0, 0, 0), new ColorType(1.0f, 0, 0), new ColorType(1.0f, 1.0f, 1.0f), 1);
		Material mat2 = new Material(new ColorType(0.2f, 0.2f, 0.2f), new ColorType(0, 0.5f, 0.5f), new ColorType(1.0f, 1.0f, 1.0f), 2);
		Material mat3 = new Material(new ColorType(0.4f, 0.1f, 0.3f), new ColorType(0.5, 0.5f, 0f), new ColorType(1.0f, 1.0f, 1.0f), 3);
		
		Sphere3D sphere2 = new Sphere3D(new Point3D(0f+x_move, 0f+y_move, 0.0f+z_move), (float)radius, Nsteps, Nsteps, mat);
		Sphere3D sphere = new Sphere3D(new Point3D(128.0f+x_move, 128.0f+y_move, 0.0f+z_move), (float)1.5*radius, Nsteps, Nsteps, mat);
        Ellipsoid3D ellipsoid = new Ellipsoid3D(new Point3D(30.0f+x_move, 30.0f+y_move, 0.0f+z_move), 0.5f, 1.0f, 2.0f, (float)radius, Nsteps, Nsteps,mat);
        Cylinder3D cylinder = new Cylinder3D(new Point3D(200f+x_move, 200f+y_move, 0.0f+z_move), (float)radius,(float) 2*radius, Nsteps, Nsteps, mat); 
        Cylinder3D cube = new Cylinder3D(new Point3D (300+x_move,300+y_move, 0.0f+z_move), (float)radius,(float) 2*radius, 5, 5,mat); 
        Torus3D torus = new Torus3D(new Point3D(300+x_move, 300+y_move, 0+z_move), (float)1.5*radius,(float) 0.5*radius, Nsteps, Nsteps,mat);
        
        SuperEllipsoid3D se1 = new SuperEllipsoid3D(new Point3D(100+x_move, 200+y_move, 0+z_move),(float)0.8*radius,(float)1.0*radius,(float)1.3*radius,0.5f, Nsteps, Nsteps,mat);
		SuperEllipsoid3D se2 =  new SuperEllipsoid3D(new Point3D(400+x_move, 400+y_move, 0+z_move),(float)0.8*radius,(float)1.0*radius,(float)1.3*radius,3f, Nsteps, Nsteps,mat3);
        BumpSphere3D bSphere = new BumpSphere3D(new Point3D(450f+x_move, 450f+y_move, 0f+z_move), (float)1.5*radius, Nsteps, Nsteps, mat2);
//		LightInfinite lt = new LightInfinite(new ColorType(1.0f, 1.0f, 1.0f), new Point3D(1.0f, 1.0f, 1.0f));
//		LightAmbient  la = new LightAmbient(new ColorType(1.0f,1.0f,1.0f));
//		LightPoint lp = new LightPoint(new ColorType(0,0,1.0f),new Point3D(900,900,900));
//		LightSpot ls = new LightSpot(new ColorType(0,1.0f,0),new Point3D(1,0,0),new Point3D(500,300,300));
//        
        
        

		switch (testCase){
		case 0:
			//phongShadeTest(sphere2);
			if(flatRenderKey == true) {
				shadeTest(true,sphere);
				shadeTest(true,se1);
				shadeTest(true,bSphere);
				shadeTest(true,cube);
				//shadeTest(true,torus);
			}
			else if(this.gouraudRenderKey == true) {
				gouraudShadeTest(true,sphere);
				gouraudShadeTest(true,se1);
				gouraudShadeTest(true,bSphere);
				gouraudShadeTest(true,cube);
				//gouraudShadeTest(true,torus);
			}
			else if(this.phongRenderKey == true) {
				phongShadeTest(sphere,false);
				phongShadeTest(se1,false);
				phongShadeTest(bSphere,false);
				phongShadeTest(cube,false);
				//phongShadeTest(torus);
			}
//			shadeTest(true,sphere); /* smooth shaded, sphere and torus */
//			shadeTest(true,cylinder);
			//gouraudShadeTest(true);
			break;
		case 1:
			if(flatRenderKey == true) {
				shadeTest(true,se1);
				shadeTest(true,torus);
				shadeTest(true,sphere);
				shadeTest(true,se2);
			}
			else if(this.gouraudRenderKey == true) {
				gouraudShadeTest(true,se1);
				gouraudShadeTest(true,torus);
				gouraudShadeTest(true,sphere);
				gouraudShadeTest(true,se2);
			}
			else if(this.phongRenderKey == true) {
				phongShadeTest(se1,false);
				phongShadeTest(torus,false);
				phongShadeTest(sphere,false);
				phongShadeTest(se2,false);
			}
			break;
		case 2:
			if(flatRenderKey == true) {
				shadeTest(true,cylinder);
				shadeTest(true,torus);
				shadeTest(true,sphere);
				shadeTest(true,se2);
			}
			else if(this.gouraudRenderKey == true) {
				gouraudShadeTest(true,cylinder);
				gouraudShadeTest(true,torus);
				gouraudShadeTest(true,sphere);
				gouraudShadeTest(true,se2);
			}
			else if(this.phongRenderKey == true) {
				phongShadeTest(cylinder,false);
				phongShadeTest(torus,false);
				phongShadeTest(sphere,false);
				phongShadeTest(se2,false);
			}
			break;
		case 3:

			phongShadeTest(sphere,true);
			//phongShadeTest(cylinder,true);
			break;
//		case 4:
//			phongShadeTest(bSphere);
//			phongShadeTest(sphere);
//			phongShadeTest(cylinder);
//			break;
			
			
		}	
	}


	//*********************************************** 
	//          KeyListener Interfaces
	//*********************************************** 
	public void keyTyped(KeyEvent key)
	{
	//      Q,q: quit 
	//      C,c: clear polygon (set vertex count=0)
	//		R,r: randomly change the color
	//		S,s: toggle the smooth shading
	//		T,t: show testing examples (toggles between smooth shading and flat shading test cases)
	//		>:	 increase the step number for examples
	//		<:   decrease the step number for examples
	//     +,-:  increase or decrease spectral exponent
		
	//		F:	flat surface rendering
	//      G: Gouraud rendering 
	//      P: Phong rendering
		
	//	    1: specular light
	//      2: diffuse light 
	//      3: ambient light
	//      
	//      4: light Infinite
	//		5: point light
	//      6:spot light
		
	//      {: camera move up
	//      }: camera move down
	//      [: camera move left
	//      ]: camera move down

	    switch ( key.getKeyChar() ) 
	    {
	    case 'Q' :
	    case 'q' : 
	    	new Thread()
	    	{
	          	public void run() { animator.stop(); }
	        }.start();
	        System.exit(0);
	        break;
	    case 'R' :
	    case 'r' :
	    	color = new ColorType(rng.nextFloat(),rng.nextFloat(),
	    			rng.nextFloat());
	    	break;
	    case 'C' :
	    case 'c' :
	    	clearPixelBuffer();
	    	break;
	    case 'S' :
	    case 's' :
	    	doSmoothShading = !doSmoothShading; // This is a placeholder (implemented in 'T')
	    	break;
	    case 'T' :
	    case 't' : 
	    	testCase = (testCase+1)%numTestCase;
	    	drawTestCase();
	        break; 
	    case '<':  
	        Nsteps = Nsteps < 4 ? Nsteps: Nsteps / 2;
	        System.out.printf( "Nsteps = %d \n", Nsteps);
	        drawTestCase();
	        break;
	    case '>':
	        Nsteps = Nsteps > 190 ? Nsteps: Nsteps * 2;
	        System.out.printf( "Nsteps = %d \n", Nsteps);
	        drawTestCase();
	        break;
	    case '+':
	    	ns++;
	        drawTestCase();
	    	break;
	    case '-':
	    	if(ns>0)
	    		ns--;
	        drawTestCase();
	    	break;
	    case '1':
	    case '!':
	    	lightAmbientKey = !lightAmbientKey;
	    	break;
	    case '2':
	    case '@':
	    	lightSpecularKey = !lightSpecularKey;
	    	break;
	    case '3':
	    case '#':
	    	lightDiffuseKey = !lightDiffuseKey;
	    	break;
	    case '4':
	    case '$':
	    	lightInfiniteKey = !lightInfiniteKey;
	    	break;
	    case '5':
	    case '%':
	    	lightPointKey = !lightPointKey;
	    	break;
	    case '6':
	    case '^': 
	    	lightSpotKey = !lightSpotKey;
	    	break;
	    case 'F':
	    case 'f':
	    	this.flatRenderKey = true;
	    	this.gouraudRenderKey = false;
	    	this.phongRenderKey = false;
	    	break;
	    case 'G':
	    case 'g':
	    	this.flatRenderKey = false;
	    	this.gouraudRenderKey = true;
	    	this.phongRenderKey = false;
	    	break;
	    case 'P':
	    case 'p':
	    	this.flatRenderKey = false;
	    	this.gouraudRenderKey = false;
	    	this.phongRenderKey = true;
	    	break;
	    
	    case '{':
	    	y_move -= 5;
	    	break;
	    case '}':
	    	y_move += 5;
	    	break;
	    case '[':
	    	x_move -= 5;
	    	break;
	    case ']':
	    	x_move += 5;
	    	break;
	    case ':':
	    	z_move += 5;
	    	break;
	    case ';':
	    	z_move -= 5;
	    	break;
	    
	    
	    default :
	        break;
	       
	    }
	}

	public void keyPressed(KeyEvent key)
	{
	    switch (key.getKeyCode()) 
	    {
	    case KeyEvent.VK_ESCAPE:
	    	new Thread()
	        {
	    		public void run()
	    		{
	    			animator.stop();
	    		}
	        }.start();
	        System.exit(0);
	        break;
	      default:
	        break;
	    }
	}

	public void keyReleased(KeyEvent key)
	{
		// deliberately left blank
	}

	//************************************************** 
	// MouseListener and MouseMotionListener Interfaces
	//************************************************** 
	public void mouseClicked(MouseEvent mouse)
	{
		// deliberately left blank
	}
	  public void mousePressed(MouseEvent mouse)
	  {
	    int button = mouse.getButton();
	    if ( button == MouseEvent.BUTTON1 )
	    {
	      last_x = mouse.getX();
	      last_y = mouse.getY();
	      rotate_world = true;
	    }
	  }

	  public void mouseReleased(MouseEvent mouse)
	  {
	    int button = mouse.getButton();
	    if ( button == MouseEvent.BUTTON1 )
	    {
	      rotate_world = false;
	    }
	  }

	public void mouseMoved( MouseEvent mouse)
	{
		// Deliberately left blank
	}

	/**
	   * Updates the rotation quaternion as the mouse is dragged.
	   * 
	   * @param mouse
	   *          The mouse drag event object.
	   */
	  public void mouseDragged(final MouseEvent mouse) {
	    if (this.rotate_world) {
	      // get the current position of the mouse
	      final int x = mouse.getX();
	      final int y = mouse.getY();

	      // get the change in position from the previous one
	      final int dx = x - this.last_x;
	      final int dy = y - this.last_y;

	      // create a unit vector in the direction of the vector (dy, dx, 0)
	      final float magnitude = (float)Math.sqrt(dx * dx + dy * dy);
	      if(magnitude > 0.0001)
	      {
	    	  // define axis perpendicular to (dx,-dy,0)
	    	  // use -y because origin is in upper lefthand corner of the window
	    	  final float[] axis = new float[] { -(float) (dy / magnitude),
	    			  (float) (dx / magnitude), 0 };

	    	  // calculate appropriate quaternion
	    	  final float viewing_delta = 3.1415927f / 360.0f * magnitude;
	    	  final float s = (float) Math.sin(0.5f * viewing_delta);
	    	  final float c = (float) Math.cos(0.5f * viewing_delta);
	    	  final Quaternion Q = new Quaternion(c, s * axis[0], s * axis[1], s * axis[2]);
	    	  this.viewing_quaternion = Q.multiply(this.viewing_quaternion);

	    	  // normalize to counteract acccumulating round-off error
	    	  this.viewing_quaternion.normalize();

	    	  // save x, y as last x, y
	    	  this.last_x = x;
	    	  this.last_y = y;
	          drawTestCase();
	      }
	    }

	  }
	  
	public void mouseEntered( MouseEvent mouse)
	{
		// Deliberately left blank
	}

	public void mouseExited( MouseEvent mouse)
	{
		// Deliberately left blank
	} 


	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
	
	//************************************************** 
	// Test Cases
	// Nov 9, 2014 Stan Sclaroff -- removed line and triangle test cases
	//************************************************** 

	void shadeTest(boolean doSmooth, Object3D object){
		// the simple example scene includes one sphere and one torus
		float radius = (float)50.0;

        
        // view vector is defined along z axis
        // this example assumes simple othorgraphic projection
        // view vector is used in 
        //   (a) calculating specular lighting contribution
        //   (b) backface culling / backface rejection
        Point3D view_vector = new Point3D((float)0.0,(float)0.0,(float)1.0);
        
        // normal to the plane of a triangle
        // to be used in backface culling / backface rejection
        Point3D triangle_normal = new Point3D();
        
        // a triangle mesh
        Mesh3D mesh;
            
		int i, j, n, m;
		
		// temporary variables for triangle 3D vertices and 3D normals
		Point3D v0,v1, v2, n0, n1, n2;
		
		// projected triangle, with vertex colors
		Point2D[] tri = {new Point2D(), new Point2D(), new Point2D()};


		mesh=object.mesh;
		n=object.get_n();
		m=object.get_m();
		
//		System.out.printf("stacks %d, slices %d \n",n,m );
//		System.out.printf("[%f, %f, %f]\n", mesh.n[6][6].x, mesh.n[6][6].y, mesh.n[6][6].z);
			

		
		// rotate the surface's 3D mesh using quaternion
		mesh.rotateMesh(viewing_quaternion, viewing_center);
				
		// draw triangles for the current surface, using vertex colors
		for(i=0; i < m-1; ++i)
	    {
			for(j=0; j < n-1; ++j)
			{
				v2 = mesh.v[i][j];
				v1 = mesh.v[i][j+1];
				v0 = mesh.v[i+1][j+1];
				
				
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{	
					// flat shading: use the normal to the triangle itself
					n2 = n1 = n0 =  triangle_normal;
					tri[0].c.set_rgb_float(0, 0, 0);
					tri[1].c.set_rgb_float(0, 0, 0);
					tri[2].c.set_rgb_float(0, 0, 0);
					
					if(lightInfiniteKey) {
						 lt.applyLight(tri[0].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[1].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[2].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
					}
					if(lightAmbientKey) {
						la.applyLight(tri[0].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[1].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[2].c,object.mat, lightDiffuseKey, lightAmbientKey);
					}
					if(lightPointKey) {
						lp.applyLight(tri[0].c, object.mat, view_vector, triangle_normal, v0, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[1].c, object.mat, view_vector, triangle_normal, v1, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[2].c, object.mat, view_vector, triangle_normal, v2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightSpotKey) {
						ls.applyLight(tri[0].c, object.mat, view_vector, triangle_normal, v0, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[1].c, object.mat, view_vector, triangle_normal, v1, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[2].c, object.mat, view_vector, triangle_normal, v2, lightDiffuseKey, lightSpecularKey);
					}
					

					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;

					//SketchBase.drawTriangle(buff,tri[0],tri[1],tri[2],doSmooth); 
					SketchBase.drawTriangle(buff,tri[0],v0.z,tri[1],v1.z,tri[2],v2.z,doSmooth); 
				}
				
				v2 = mesh.v[i][j];
				v1 = mesh.v[i+1][j+1];
				v0 = mesh.v[i+1][j];
				
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{	
					// flat shading: use the normal to the triangle itself
					n2 = n1 = n0 =  triangle_normal;
					tri[0].c.set_rgb_float(0, 0, 0);
					tri[1].c.set_rgb_float(0, 0, 0);
					tri[2].c.set_rgb_float(0, 0, 0);
					if(lightInfiniteKey) {
						 lt.applyLight(tri[0].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[1].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[2].c,object.mat, view_vector, triangle_normal, lightDiffuseKey, lightSpecularKey);
					}
					if(lightAmbientKey) {
						la.applyLight(tri[0].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[1].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[2].c,object.mat, lightDiffuseKey, lightAmbientKey);
					}
					if(lightPointKey) {
						lp.applyLight(tri[0].c, object.mat, view_vector, triangle_normal, v0, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[1].c, object.mat, view_vector, triangle_normal, v1, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[2].c, object.mat, view_vector, triangle_normal, v2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightSpotKey) {
						ls.applyLight(tri[0].c, object.mat, view_vector, triangle_normal, v0, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[1].c, object.mat, view_vector, triangle_normal, v1, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[2].c, object.mat, view_vector, triangle_normal, v2, lightDiffuseKey, lightSpecularKey);
					}
		
					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;
					
					//SketchBase.drawTriangle(buff,tri[0],tri[1],tri[2],doSmooth);
					SketchBase.drawTriangle(buff,tri[0],v0.z,tri[1],v1.z,tri[2],v2.z,doSmooth); 
				}
			}	
	    }
	}
	
	void gouraudShadeTest(boolean doSmooth,Object3D object){
		// the simple example scene includes one sphere and one torus

        // view vector is defined along z axis
        // this example assumes simple othorgraphic projection
        // view vector is used in 
        //   (a) calculating specular lighting contribution
        //   (b) backface culling / backface rejection
        Point3D view_vector = new Point3D((float)0.0,(float)0.0,(float)1.0);
        
        // normal to the plane of a triangle
        // to be used in backface culling / backface rejection
        Point3D triangle_normal = new Point3D();
        
        // a triangle mesh
        Mesh3D mesh;
            
		int i, j, n, m;
		
		// temporary variables for triangle 3D vertices and 3D normals
		Point3D v0,v1, v2, n0, n1, n2;
		
		// projected triangle, with vertex colors
		Point2D[] tri = {new Point2D(), new Point2D(), new Point2D()};


		mesh=object.mesh;
		n=object.get_n();
		m=object.get_m();
		
//		Material mat = new Material(new ColorType(0, 0, 0), new ColorType(1.0f, 0, 0), new ColorType(1.0f, 1.0f, 1.0f), 1);

		
		// rotate the surface's 3D mesh using quaternion
		mesh.rotateMesh(viewing_quaternion, viewing_center);
				
		// draw triangles for the current surface, using vertex colors
		for(i=0; i < m-1; ++i)
	    {
			for(j=0; j < n-1; ++j)
			{
				v2 = mesh.v[i][j];
				v1 = mesh.v[i][j+1];
				v0 = mesh.v[i+1][j+1];
				
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{	
					// flat shading: use the normal to the triangle itself
					n2 = mesh.n[i][j];
					n1 = mesh.n[i][j+1];
					n0 = mesh.n[i+1][j+1];
					
					tri[0].c.set_rgb_float(0, 0, 0);
					tri[1].c.set_rgb_float(0, 0, 0);
					tri[2].c.set_rgb_float(0, 0, 0);
					
					if(lightInfiniteKey) {
						 lt.applyLight(tri[0].c, object.mat, view_vector, n0, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[1].c, object.mat, view_vector, n1, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[2].c, object.mat, view_vector, n2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightAmbientKey) {
						la.applyLight(tri[0].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[1].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[2].c,object.mat, lightDiffuseKey, lightAmbientKey);
					}
					if(lightPointKey) {
						lp.applyLight(tri[0].c, object.mat, view_vector, n0, v0, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[1].c, object.mat, view_vector, n1, v1, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[2].c, object.mat, view_vector, n2, v2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightSpotKey) {
						ls.applyLight(tri[0].c, object.mat, view_vector, n0, v0, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[1].c, object.mat, view_vector, n1, v1, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[2].c, object.mat, view_vector, n2, v2, lightDiffuseKey, lightSpecularKey);
					}
					

					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;

//					SketchBase.drawTriangle(buff,tri[0],tri[1],tri[2],doSmooth); 
					SketchBase.drawTriangle(buff,tri[0],v0.z,tri[1],v1.z,tri[2],v2.z,doSmooth);
					
				}
				
				v2 = mesh.v[i][j];
				v1 = mesh.v[i+1][j+1];
				v0 = mesh.v[i+1][j];
				
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{	
					// flat shading: use the normal to the triangle itself
					n2 = mesh.n[i][j];
					n1 = mesh.n[i+1][j+1];
					n0 = mesh.n[i+1][j];
					
					tri[0].c.set_rgb_float(0, 0, 0);
					tri[1].c.set_rgb_float(0, 0, 0);
					tri[2].c.set_rgb_float(0, 0, 0);
					if(lightInfiniteKey) {
						 lt.applyLight(tri[0].c,object.mat, view_vector, n0, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[1].c,object.mat, view_vector, n1, lightDiffuseKey, lightSpecularKey);
						 lt.applyLight(tri[2].c,object.mat, view_vector, n2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightAmbientKey) {
						la.applyLight(tri[0].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[1].c,object.mat, lightDiffuseKey, lightAmbientKey);
						la.applyLight(tri[2].c,object.mat, lightDiffuseKey, lightAmbientKey);
					}
					if(lightPointKey) {
						lp.applyLight(tri[0].c, object.mat, view_vector, n0, v0, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[1].c, object.mat, view_vector, n1, v1, lightDiffuseKey, lightSpecularKey);
						lp.applyLight(tri[2].c, object.mat, view_vector, n2, v2, lightDiffuseKey, lightSpecularKey);
					}
					if(lightSpotKey) {
						ls.applyLight(tri[0].c, object.mat, view_vector, n0, v0, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[1].c, object.mat, view_vector, n1, v1, lightDiffuseKey, lightSpecularKey);
						ls.applyLight(tri[2].c, object.mat, view_vector, n2, v2, lightDiffuseKey, lightSpecularKey);
					}
		
					tri[0].x = (int)v0.x;
					tri[0].y = (int)v0.y;
					tri[1].x = (int)v1.x;
					tri[1].y = (int)v1.y;
					tri[2].x = (int)v2.x;
					tri[2].y = (int)v2.y;
					
					//SketchBase.drawTriangle(buff,tri[0],tri[1],tri[2],doSmooth);
					SketchBase.drawTriangle(buff,tri[0],v0.z,tri[1],v1.z,tri[2],v2.z,doSmooth);
				}
			}	
	    }
	}
	
	void phongShadeTest(Object3D object,boolean environmentKey){
		// the simple example scene includes one sphere and one torus
        
        // view vector is defined along z axis
        // this example assumes simple othorgraphic projection
        // view vector is used in 
        //   (a) calculating specular lighting contribution
        //   (b) backface culling / backface rejection
        Point3D view_vector = new Point3D((float)0.0,(float)0.0,(float)1.0);
        
        // normal to the plane of a triangle
        // to be used in backface culling / backface rejection
        Point3D triangle_normal = new Point3D();
        
        // a triangle mesh
        Mesh3D mesh;
            
		int i, j, n, m;
		
		// temporary variables for triangle 3D vertices and 3D normals
		Point3D v0,v1, v2, n0, n1, n2;
		
		// projected triangle, with vertex colors
		Point2D[] tri = {new Point2D(), new Point2D(), new Point2D()};


		mesh=object.mesh;
		n=object.get_n();
		m=object.get_m();
			
//		Material mat = new Material(new ColorType(0, 0, 0), new ColorType(1.0f, 0, 0), new ColorType(1.0f, 1.0f, 1.0f), 1);

		
		// rotate the surface's 3D mesh using quaternion
		mesh.rotateMesh(viewing_quaternion, viewing_center);
				
		// draw triangles for the current surface, using vertex colors
		for(i=0; i < m-1; ++i)
	    {
			for(j=0; j < n-1; ++j)
			{
				v2 = mesh.v[i][j];
				v1 = mesh.v[i][j+1];
				v0 = mesh.v[i+1][j+1];
				
				triangle_normal = computeTriangleNormal(v0,v1,v2);

				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{	
				n2 = mesh.n[i][j];
				n1 = mesh.n[i][j+1];
				n0 = mesh.n[i+1][j+1];
				
				tri[0].x = (int)v0.x;
				tri[0].y = (int)v0.y;
				tri[1].x = (int)v1.x;
				tri[1].y = (int)v1.y;
				tri[2].x = (int)v2.x;
				tri[2].y = (int)v2.y;

				if(environmentKey == false) {
					drawTriangle(view_vector, object.mat,tri[0], tri[1], tri[2], v0.z, v1.z, v2.z, n0, n1, n2);
				}
				else {
					drawTriangle_environment(view_vector, object.mat,tri[0], tri[1], tri[2], v0.z, v1.z, v2.z, n0, n1, n2);
				}
				}
				
				v2 = mesh.v[i][j];
				v1 = mesh.v[i+1][j+1];
				v0 = mesh.v[i+1][j];
				triangle_normal = computeTriangleNormal(v0,v1,v2);
				
				if(view_vector.dotProduct(triangle_normal) > 0.0)  // front-facing triangle?
				{
				n2 = mesh.n[i][j];
				n1 = mesh.n[i+1][j+1];
				n0 = mesh.n[i+1][j];
				tri[0].x = (int)v0.x;
				tri[0].y = (int)v0.y;
				tri[1].x = (int)v1.x;
				tri[1].y = (int)v1.y;
				tri[2].x = (int)v2.x;
				tri[2].y = (int)v2.y;
				if(environmentKey == false) {
					drawTriangle(view_vector, object.mat,tri[0], tri[1], tri[2], v0.z, v1.z, v2.z, n0, n1, n2);
				}else {
					drawTriangle_environment(view_vector, object.mat,tri[0], tri[1], tri[2], v0.z, v1.z, v2.z, n0, n1, n2);
				}
				}
			}	
	    }
	}
	
	public void drawTriangle(Point3D view_vector,Material mat,Point2D p1, Point2D p2, Point2D p3, float z1, float z2, float z3, Point3D n1, Point3D n2, Point3D n3)
	{

		// replace the following line with your implementation
		int y_top = Math.max(Math.max(p1.y, p2.y),p3.y);
		int y_down = Math.min(Math.min(p1.y, p2.y),p3.y);
		int y_mid=0,x_top=0,x_down=0,x_mid=0;
		//ColorType c_top,c_mid,c_down;
		Point2D pt_top=new Point2D();
		Point2D pt_mid=new Point2D();
		Point2D	pt_down=new Point2D();
		
		Point3D n_top = new Point3D();
		Point3D n_mid = new Point3D();
		Point3D n_down = new Point3D();
		
		float z_top = 0;
		float z_mid= 0;
		float z_down =0;
		

		if(y_top == p1.y && y_down == p2.y){
			y_mid = p3.y; x_mid=p3.x; 
			x_top = p1.x; x_down = p2.x;
			//System.out.println("y1>y3>y2");
			
			pt_top=p1; pt_mid = p3; pt_down=p2;
			n_top = n1; n_mid = n3; n_down = n2;
			z_top = z1; z_mid = z3; z_down = z2;	
		}
		else if(y_top == p1.y && y_down == p3.y){
			y_mid = p2.y; x_mid = p2.x;
			x_top = p1.x; x_down = p3.x;
			//System.out.println("y1>y2>y3");	
			
			pt_top = p1; pt_mid = p2;pt_down=p3;
			n_top = n1; n_mid = n2; n_down = n3;
			z_top = z1; z_mid = z2; z_down = z3;
			
		}
		else if(y_top == p2.y && y_down == p3.y){
			y_mid = p1.y; x_mid = p1.x;
			x_top = p2.x; x_down = p3.x;
			//System.out.println("y2>y1>y3");	
			
			pt_top=p2; pt_mid = p1; pt_down=p3;
			n_top = n2; n_mid = n1; n_down = n3;
			z_top = z2; z_mid = z1; z_down = z3;
		}
		else if(y_top == p2.y && y_down == p1.y){
			y_mid = p3.y; x_mid = p3.x;
			x_top = p2.x; x_down = p1.x;
			//System.out.println("y2>y3>y1");	
			
			pt_top = p2; pt_mid = p3; pt_down=p1;
			n_top = n2; n_mid = n3; n_down = n1;
			z_top = z2; z_mid = z3; z_down = z1;
		}
		else if(y_top == p3.y && y_down == p1.y){
			y_mid = p2.y; x_mid = p2.x;
			x_top = p3.x; x_down = p1.x;
			//System.out.println("y3>y2>y1");	
			
			pt_top=p3; pt_mid = p2; pt_down=p1;
			n_top = n3; n_mid = n2; n_down = n1;
			z_top = z3; z_mid = z2; z_down = z1;
		}
		else if(y_top == p3.y && y_down == p2.y){
			y_mid = p1.y; x_mid = p1.x;
			x_top = p3.x; x_down = p2.x;
			//System.out.println("y3>y1>y2");	
			
			pt_top=p3; pt_mid = p1; pt_down=p2;
			n_top = n3; n_mid = n1; n_down = n2;
			z_top = z3; z_mid = z1; z_down = z2;
		}
//		System.out.printf("\n\n");
//		System.out.printf("p_top(%d, %d, %f)    n_top( %f, %f, %f)\n",x_top, y_top, z_top, n_top.x, n_top.y, n_top.z);
//		System.out.printf("p_mid(%d, %d, %f)    n_mid( %f, %f, %f)\n",x_mid, y_mid, z_mid, n_mid.x, n_mid.y, n_mid.z);
//		System.out.printf("p_down(%d, %d, %f)   n_down( %f, %f, %f)\n",x_down, y_down, z_down, z_mid, n_down.x, n_down.y, n_down.z);
	
		
			//upper triangle
			for(int y=y_top; y>=y_mid; y--){
				
				int x_start = Point2D.lineInterpolation(y_top, x_top, y_mid, x_mid, y);
				int x_end = Point2D.lineInterpolation(y_top, x_top,y_down,x_down, y);
				float z_start = Point2D.lineInterpolation(y_top, z_top, y_mid, z_mid, y);
				float z_end = Point2D.lineInterpolation(y_top, z_top, y_down, z_down, y);
				Point3D n_start = Point3D.normVectorInterpolation(y_top,n_top,y_mid,n_mid,y);
				Point3D n_end = Point3D.normVectorInterpolation(y_top,n_top,y_down,n_down,y);
				
//				System.out.printf("Upper p_start(%d, %d, %f)    n_start( %f, %f, %f)\n",x_start, y, z_start, n_start.x, n_start.y, n_start.z);
//				System.out.printf("Upper p_end(%d, %d, %f)      n_end( %f, %f, %f)\n",x_end, y, z_end, n_end.x, n_end.y, n_end.z);
				
				int x_step = x_end > x_start? 1:-1;
				
				for(int x= x_start; x!=x_end+x_step; x+=x_step){
//					System.out.printf("upper tri scan(%d,%d)\n",x,y);
					

					Point3D n = Point3D.normVectorInterpolation(x_start,n_start,x_end,n_end,x);
					float z = Point2D.lineInterpolation(x_start, z_start, x_end, z_end, x);
					
//					if(view_vector.dotProduct(n) > 0.0)  // front-facing triangle?
//					{	
						
						Point2D p = new Point2D(x,y,new ColorType(0,0,0));
						pointApplyLights(p, mat,  view_vector,n, new Point3D(x,y,z));
						SketchBase.drawPoint(buff,p,z);
//					}
					
					
				}

			
			}
			//down triangle
			for(int y=y_mid;y>=y_down;y--){
				
				int x_start =Point2D.lineInterpolation(y_down, x_down, y_mid,x_mid, y);
				int x_end = Point2D.lineInterpolation(y_down,x_down, y_top,x_top, y);
				float z_start = Point2D.lineInterpolation(y_down, z_down, y_mid, z_mid, y);
				float z_end = Point2D.lineInterpolation(y_down, z_down, y_top, z_top, y);
				Point3D n_start = Point3D.normVectorInterpolation(y_down,n_down,y_mid,n_mid,y);
				Point3D n_end = Point3D.normVectorInterpolation(y_down,n_down,y_top,n_top,y);
				
//				System.out.printf("Down p_start(%d, %d, %f)    n_start( %f, %f, %f)\n",x_start, y, z_start, n_start.x, n_start.y, n_start.z);
//				System.out.printf("Down p_end(%d, %d, %f)      n_end( %f, %f, %f)\n",x_end, y, z_end, n_end.x, n_end.y, n_end.z);
				
				int x_step = x_end > x_start? 1:-1;
				
				for(int x= x_start; x!=x_end+x_step; x+=x_step){
//					System.out.printf("down tri scan(%d,%d)\n",x,y);
					Point3D n = Point3D.normVectorInterpolation(x_start,n_start,x_end,n_end,x);
					float z = Point2D.lineInterpolation(x_start, z_start, x_end, z_end, x);
					
//					if(view_vector.dotProduct(n) > 0.0)  // front-facing triangle?
//					{	
						
						Point2D p = new Point2D(x,y,new ColorType(0,0,0));
						pointApplyLights(p, mat,  view_vector,n, new Point3D(x,y,z));
						SketchBase.drawPoint(buff,p,z);
						  
//					}
					
				}

				
			}

	}
	
	public void drawTriangle_environment(Point3D view_vector,Material mat,Point2D p1, Point2D p2, Point2D p3, float z1, float z2, float z3, Point3D n1, Point3D n2, Point3D n3)
	{

		// replace the following line with your implementation
		int y_top = Math.max(Math.max(p1.y, p2.y),p3.y);
		int y_down = Math.min(Math.min(p1.y, p2.y),p3.y);
		int y_mid=0,x_top=0,x_down=0,x_mid=0;
		//ColorType c_top,c_mid,c_down;
//		Point2D pt_top=new Point2D();
//		Point2D pt_mid=new Point2D();
//		Point2D	pt_down=new Point2D();
		
		Point3D n_top = new Point3D();
		Point3D n_mid = new Point3D();
		Point3D n_down = new Point3D();
		
		float z_top = 0;
		float z_mid= 0;
		float z_down =0;
		

		if(y_top == p1.y && y_down == p2.y){
			y_mid = p3.y; x_mid=p3.x; 
			x_top = p1.x; x_down = p2.x;
			//System.out.println("y1>y3>y2");
			
			// pt_top=p1; pt_mid = p3; pt_down=p2;
			n_top = n1; n_mid = n3; n_down = n2;
			z_top = z1; z_mid = z3; z_down = z2;	
		}
		else if(y_top == p1.y && y_down == p3.y){
			y_mid = p2.y; x_mid = p2.x;
			x_top = p1.x; x_down = p3.x;
			//System.out.println("y1>y2>y3");	
			
			// pt_top = p1; pt_mid = p2;pt_down=p3;
			n_top = n1; n_mid = n2; n_down = n3;
			z_top = z1; z_mid = z2; z_down = z3;
			
		}
		else if(y_top == p2.y && y_down == p3.y){
			y_mid = p1.y; x_mid = p1.x;
			x_top = p2.x; x_down = p3.x;
			//System.out.println("y2>y1>y3");	
			
			// pt_top=p2; pt_mid = p1; pt_down=p3;
			n_top = n2; n_mid = n1; n_down = n3;
			z_top = z2; z_mid = z1; z_down = z3;
		}
		else if(y_top == p2.y && y_down == p1.y){
			y_mid = p3.y; x_mid = p3.x;
			x_top = p2.x; x_down = p1.x;
			//System.out.println("y2>y3>y1");	
			
			// pt_top = p2; pt_mid = p3; pt_down=p1;
			n_top = n2; n_mid = n3; n_down = n1;
			z_top = z2; z_mid = z3; z_down = z1;
		}
		else if(y_top == p3.y && y_down == p1.y){
			y_mid = p2.y; x_mid = p2.x;
			x_top = p3.x; x_down = p1.x;
			//System.out.println("y3>y2>y1");	
			
			// pt_top=p3; pt_mid = p2; pt_down=p1;
			n_top = n3; n_mid = n2; n_down = n1;
			z_top = z3; z_mid = z2; z_down = z1;
		}
		else if(y_top == p3.y && y_down == p2.y){
			y_mid = p1.y; x_mid = p1.x;
			x_top = p3.x; x_down = p2.x;
			//System.out.println("y3>y1>y2");	
			
			// pt_top=p3; pt_mid = p1; pt_down=p2;
			n_top = n3; n_mid = n1; n_down = n2;
			z_top = z3; z_mid = z1; z_down = z2;
		}
//		System.out.printf("\n\n");
//		System.out.printf("p_top(%d, %d, %f)    n_top( %f, %f, %f)\n",x_top, y_top, z_top, n_top.x, n_top.y, n_top.z);
//		System.out.printf("p_mid(%d, %d, %f)    n_mid( %f, %f, %f)\n",x_mid, y_mid, z_mid, n_mid.x, n_mid.y, n_mid.z);
//		System.out.printf("p_down(%d, %d, %f)   n_down( %f, %f, %f)\n",x_down, y_down, z_down, z_mid, n_down.x, n_down.y, n_down.z);
	
		
			//upper triangle
			for(int y=y_top; y>=y_mid; y--){
				
				int x_start = Point2D.lineInterpolation(y_top, x_top, y_mid, x_mid, y);
				int x_end = Point2D.lineInterpolation(y_top, x_top,y_down,x_down, y);
				float z_start = Point2D.lineInterpolation(y_top, z_top, y_mid, z_mid, y);
				float z_end = Point2D.lineInterpolation(y_top, z_top, y_down, z_down, y);
				Point3D n_start = Point3D.normVectorInterpolation(y_top,n_top,y_mid,n_mid,y);
				Point3D n_end = Point3D.normVectorInterpolation(y_top,n_top,y_down,n_down,y);
				
//				System.out.printf("Upper p_start(%d, %d, %f)    n_start( %f, %f, %f)\n",x_start, y, z_start, n_start.x, n_start.y, n_start.z);
//				System.out.printf("Upper p_end(%d, %d, %f)      n_end( %f, %f, %f)\n",x_end, y, z_end, n_end.x, n_end.y, n_end.z);
				
				int x_step = x_end > x_start? 1:-1;
				
				for(int x= x_start; x!=x_end+x_step; x+=x_step){
//					System.out.printf("upper tri scan(%d,%d)\n",x,y);
					

					Point3D n = Point3D.normVectorInterpolation(x_start,n_start,x_end,n_end,x);
					float z = Point2D.lineInterpolation(x_start, z_start, x_end, z_end, x);
					Point3D r = view_vector.reflect(n);
					ColorType c = new ColorType();
					if(Math.abs(r.x)>Math.abs(r.y) && Math.abs(r.x)>Math.abs(r.z) && r.x >= 0) {//Right
						float y_ = 1024*(float)r.y/r.x;
						float z_ = 1024*(float)r.z/r.x;
						c = SketchBase.getTextureColor(-z_,y_,left_environment);
					}
					else if(Math.abs(r.x)>Math.abs(r.y) && Math.abs(r.x)>Math.abs(r.z) && r.x < 0) {//Left
						float y_ = 1024*(float)r.y/r.x;
						float z_ = 1024*(float)r.z/r.x;
						c = SketchBase.getTextureColor(-z_,-y_,right_environment);
					}
					else if(Math.abs(r.y)>Math.abs(r.x) && Math.abs(r.y)>Math.abs(r.z) && r.y >= 0) {//Top
						float x_ = 1024*(float)r.x/r.y;
						float z_ = 1024*(float)r.z/r.y;
						c = SketchBase.getTextureColor(-x_,z_,bottom_environment); 
						
					}
					else if(Math.abs(r.y)>Math.abs(r.x) && Math.abs(r.y)>Math.abs(r.z) && r.y < 0) {//Bottom
						float x_ = 1024*(float)r.x/r.y;
						float z_ = 1024*(float)r.z/r.y;
						c = SketchBase.getTextureColor(x_,z_ , top_environment);
						
					}
					else if(Math.abs(r.z)>Math.abs(r.x) && Math.abs(r.z)>Math.abs(r.y) && r.z >= 0) {//Front
						float x_ = 1024*(float)r.x/r.z;
						float y_ = 1024*(float)r.y/r.z;
						c = SketchBase.getTextureColor(x_,y_ , back_environment);	
					}
					else if(Math.abs(r.z)>Math.abs(r.x) && Math.abs(r.z)>Math.abs(r.y) && r.z < 0) {//Back
						float x_ = 1024*(float)r.x/r.z;
						float y_ = 1024*(float)r.y/r.z;
						c = SketchBase.getTextureColor(x_,-y_ , front_environment);
						
					}
					
					if(view_vector.dotProduct(n) > 0.0)  // front-facing triangle?
					{	
						Point2D p = new Point2D(x,y,c);
						//pointApplyLights(p, mat,  view_vector,n, new Point3D(x,y,z));
						SketchBase.drawPoint(buff,p,z);
					}
					
					
				}

			}
			//down triangle
			for(int y=y_mid;y>=y_down;y--){
				
				int x_start =Point2D.lineInterpolation(y_down, x_down, y_mid,x_mid, y);
				int x_end = Point2D.lineInterpolation(y_down,x_down, y_top,x_top, y);
				float z_start = Point2D.lineInterpolation(y_down, z_down, y_mid, z_mid, y);
				float z_end = Point2D.lineInterpolation(y_down, z_down, y_top, z_top, y);
				Point3D n_start = Point3D.normVectorInterpolation(y_down,n_down,y_mid,n_mid,y);
				Point3D n_end = Point3D.normVectorInterpolation(y_down,n_down,y_top,n_top,y);
				
//				System.out.printf("Down p_start(%d, %d, %f)    n_start( %f, %f, %f)\n",x_start, y, z_start, n_start.x, n_start.y, n_start.z);
//				System.out.printf("Down p_end(%d, %d, %f)      n_end( %f, %f, %f)\n",x_end, y, z_end, n_end.x, n_end.y, n_end.z);
				
				int x_step = x_end > x_start? 1:-1;
				
				for(int x= x_start; x!=x_end+x_step; x+=x_step){
					
//					System.out.printf("down tri scan(%d,%d)\n",x,y);
					Point3D n = Point3D.normVectorInterpolation(x_start,n_start,x_end,n_end,x);
					float z = Point2D.lineInterpolation(x_start, z_start, x_end, z_end, x);
					Point3D r = view_vector.reflect(n);
					
					ColorType c = new ColorType();
					if(Math.abs(r.x)>Math.abs(r.y) && Math.abs(r.x)>Math.abs(r.z) && r.x >= 0) {//Right
						float y_ = 1024*(float)r.y/r.x;
						float z_ = 1024*(float)r.z/r.x;
						c = SketchBase.getTextureColor(-z_,y_,left_environment);
					}
					else if(Math.abs(r.x)>Math.abs(r.y) && Math.abs(r.x)>Math.abs(r.z) && r.x < 0) {//Left
						float y_ = 1024*(float)r.y/r.x;
						float z_ = 1024*(float)r.z/r.x;
						c = SketchBase.getTextureColor(-z_,-y_,right_environment);
					}
					else if(Math.abs(r.y)>Math.abs(r.x) && Math.abs(r.y)>Math.abs(r.z) && r.y >= 0) {//Top
						float x_ = 1024*(float)r.x/r.y;
						float z_ = 1024*(float)r.z/r.y;
						c = SketchBase.getTextureColor(-x_,z_,bottom_environment); 
						
					}
					else if(Math.abs(r.y)>Math.abs(r.x) && Math.abs(r.y)>Math.abs(r.z) && r.y < 0) {//Bottom
						float x_ = 1024*(float)r.x/r.y;
						float z_ = 1024*(float)r.z/r.y;
						c = SketchBase.getTextureColor(x_,z_ , top_environment);
						
					}
					else if(Math.abs(r.z)>Math.abs(r.x) && Math.abs(r.z)>Math.abs(r.y) && r.z >= 0) {//Front
						float x_ = 1024*(float)r.x/r.z;
						float y_ = 1024*(float)r.y/r.z;
						c = SketchBase.getTextureColor(x_,y_ , back_environment);	
					}
					else if(Math.abs(r.z)>Math.abs(r.x) && Math.abs(r.z)>Math.abs(r.y) && r.z < 0) {//Back
						float x_ = 1024*(float)r.x/r.z;
						float y_ = 1024*(float)r.y/r.z;
						c = SketchBase.getTextureColor(x_,-y_ , front_environment);
						
					}
					
					if(view_vector.dotProduct(n) > 0.0)  // front-facing triangle?
					{	
						
						Point2D p = new Point2D(x,y,c);
						//pointApplyLights(p, mat,  view_vector,n, new Point3D(x,y,z));
						SketchBase.drawPoint(buff,p,z);
						  
					}
					
				}
				
			}

	}
	
	public void pointApplyLights(Point2D p, Material mat, Point3D view_vector,Point3D n, Point3D pos) {
		if(lightInfiniteKey) {
			 lt.applyLight(p.c,mat, view_vector, n, lightDiffuseKey, lightSpecularKey);
			 
		}
		if(lightAmbientKey) {
			la.applyLight(p.c,mat, lightDiffuseKey, lightAmbientKey);

		}
		if(lightPointKey) {
			lp.applyLight(p.c, mat, view_vector, n, pos, lightDiffuseKey, lightSpecularKey);
			
		}
		if(lightSpotKey) {
			ls.applyLight(p.c, mat, view_vector, n, pos, lightDiffuseKey, lightSpecularKey);	
		}
	}

	// helper method that computes the unit normal to the plane of the triangle
	// degenerate triangles yield normal that is numerically zero
	private Point3D computeTriangleNormal(Point3D v0, Point3D v1, Point3D v2)
	{
		Point3D e0 = v1.minus(v2);
		Point3D e1 = v0.minus(v2);
		Point3D norm = e0.crossProduct(e1);
		
		if(norm.magnitude()>0.000001)
			norm.normalize();
		else 	// detect degenerate triangle and set its normal to zero
			norm.set((float)0.0,(float)0.0,(float)0.0);

		return norm;
	}

}
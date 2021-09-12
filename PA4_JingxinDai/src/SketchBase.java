/**
 * @author Jingxin Dai <jingxin@bu.edu>
 * @class CS680
 * @assignment No. PA4
 * @due 12/10/2019
 * @brief The sketchbase with depth Buffer
 * *///****************************************************************************

// SketchBase.  
//****************************************************************************
// Comments : 
//   Subroutines to manage and draw points, lines an triangles
//
// History :
//   Aug 2014 Created by Jianming Zhang (jimmie33@gmail.com) based on code by
//   Stan Sclaroff (from CS480 '06 poly.c)

import java.awt.image.BufferedImage;
import java.util.*;

public class SketchBase 
{	private static float[][] d_buff;
	private static int width;
	private static int height;

	public SketchBase(int width, int height)
	{	this.width = width;
		this.height = height;
		d_buff = new float[width][height];
		for(int i = 0; i < width; i++) {
			for(int j =0; j < height; j++) {
				d_buff[i][j] = -Float.MAX_VALUE;	
			}
		}
		// deliberately left blank
	}
	
	public  static void resetDbuff() {
		for(int i = 0; i < width; i++) {
			for(int j =0; j < height; j++) {
				d_buff[i][j] = -Float.MAX_VALUE;	
			}
		}
	}
	/**********************************************************************
	 * Draws a point.
	 * This is achieved by changing the color of the buffer at the location
	 * corresponding to the point. 
	 * 
	 * @param buff
	 *          Buffer object.
	 * @param p
	 *          Point to be drawn.
	 */
	public static void drawPoint(BufferedImage buff, Point2D p)
	{
		if(p.x>=0 && p.x<buff.getWidth() && p.y>=0 && p.y < buff.getHeight())	
			buff.setRGB(p.x, buff.getHeight()-p.y-1, p.c.getRGB_int());	
	}
	
	public static void drawPoint(BufferedImage buff, Point2D p, float z)
	{
		if(p.x>=0 && p.x<buff.getWidth() && p.y>=0 && p.y < buff.getHeight() && z > d_buff[p.x][p.y]) {
			buff.setRGB(p.x, buff.getHeight()-p.y-1, p.c.getRGB_int());	
			d_buff[p.x][p.y] = z;
		}
	}
	
	/**********************************************************************
	 * Draws a line segment using Bresenham's algorithm, linearly 
	 * interpolating RGB color along line segment.
	 * This method only uses integer arithmetic.
	 * 
	 * @param buff
	 *          Buffer object.
	 * @param p1
	 *          First given endpoint of the line.
	 * @param p2
	 *          Second given endpoint of the line.
	 */
	public static void drawLine(BufferedImage buff, Point2D p1, Point2D p2)
	{
	    int x0=p1.x, y0=p1.y;
	    int xEnd=p2.x, yEnd=p2.y;
	    int dx = Math.abs(xEnd - x0),  dy = Math.abs(yEnd - y0);

	    if(dx==0 && dy==0)
	    {
	    	drawPoint(buff,p1);
	    	return;
	    }
	    
	    // if slope is greater than 1, then swap the role of x and y
	    boolean x_y_role_swapped = (dy > dx); 
	    if(x_y_role_swapped)
	    {
	    	x0=p1.y; 
	    	y0=p1.x;
	    	xEnd=p2.y; 
	    	yEnd=p2.x;
	    	dx = Math.abs(xEnd - x0);
	    	dy = Math.abs(yEnd - y0);
	    }
	    
	    // initialize the decision parameter and increments
	    int p = 2 * dy - dx;
	    int twoDy = 2 * dy,  twoDyMinusDx = 2 * (dy - dx);
	    int x=x0, y=y0;
	    
	    // set step increment to be positive or negative
	    int step_x = x0<xEnd ? 1 : -1;
	    int step_y = y0<yEnd ? 1 : -1;
	    
	    // deal with setup for color interpolation
	    // first get r,g,b integer values at the end points
	    int r0=p1.c.getR_int(), rEnd=p2.c.getR_int();
	    int g0=p1.c.getG_int(), gEnd=p2.c.getG_int();
	    int b0=p1.c.getB_int(), bEnd=p2.c.getB_int();
	    
	    // compute the change in r,g,b 
	    int dr=Math.abs(rEnd-r0), dg=Math.abs(gEnd-g0), db=Math.abs(bEnd-b0);
	    
	    // set step increment to be positive or negative 
	    int step_r = r0<rEnd ? 1 : -1;
	    int step_g = g0<gEnd ? 1 : -1;
	    int step_b = b0<bEnd ? 1 : -1;
	    
	    // compute whole step in each color that is taken each time through loop
	    int whole_step_r = step_r*(dr/dx);
	    int whole_step_g = step_g*(dg/dx);
	    int whole_step_b = step_b*(db/dx);
	    
	    // compute remainder, which will be corrected depending on decision parameter
	    dr=dr%dx;
	    dg=dg%dx; 
	    db=db%dx;
	    
	    // initialize decision parameters for red, green, and blue
	    int p_r = 2 * dr - dx;
	    int twoDr = 2 * dr,  twoDrMinusDx = 2 * (dr - dx);
	    int r=r0;
	    
	    int p_g = 2 * dg - dx;
	    int twoDg = 2 * dg,  twoDgMinusDx = 2 * (dg - dx);
	    int g=g0;
	    
	    int p_b = 2 * db - dx;
	    int twoDb = 2 * db,  twoDbMinusDx = 2 * (db - dx);
	    int b=b0;
	    
	    // draw start pixel
	    if(x_y_role_swapped)
	    {
	    	if(x>=0 && x<buff.getHeight() && y>=0 && y<buff.getWidth())
	    		buff.setRGB(y, buff.getHeight()-x-1, (r<<16) | (g<<8) | b);
	    }
	    else
	    {
	    	if(y>=0 && y<buff.getHeight() && x>=0 && x<buff.getWidth())
	    		buff.setRGB(x, buff.getHeight()-y-1, (r<<16) | (g<<8) | b);
	    }
	    
	    while (x != xEnd) 
	    {
	    	// increment x and y
	    	x+=step_x;
	    	if (p < 0)
	    		p += twoDy;
	    	else 
	    	{
	    		y+=step_y;
	    		p += twoDyMinusDx;
	    	}
		        
	    	// increment r by whole amount slope_r, and correct for accumulated error if needed
	    	r+=whole_step_r;
	    	if (p_r < 0)
	    		p_r += twoDr;
	    	else 
	    	{
	    		r+=step_r;
	    		p_r += twoDrMinusDx;
	    	}
		    
	    	// increment g by whole amount slope_b, and correct for accumulated error if needed  
	    	g+=whole_step_g;
	    	if (p_g < 0)
	    		p_g += twoDg;
	    	else 
	    	{
	    		g+=step_g;
	    		p_g += twoDgMinusDx;
	    	}
		    
	    	// increment b by whole amount slope_b, and correct for accumulated error if needed
	    	b+=whole_step_b;
	    	if (p_b < 0)
	    		p_b += twoDb;
	    	else 
	    	{
	    		b+=step_b;
	    		p_b += twoDbMinusDx;
	    	}
		    
	    	if(x_y_role_swapped)
	    	{
	    		if(x>=0 && x<buff.getHeight() && y>=0 && y<buff.getWidth())
	    			buff.setRGB(y, buff.getHeight()-x-1, (r<<16) | (g<<8) | b);
	    	}
	    	else
	    	{
	    		if(y>=0 && y<buff.getHeight() && x>=0 && x<buff.getWidth())
	    			buff.setRGB(x, buff.getHeight()-y-1, (r<<16) | (g<<8) | b);
	    	}
	    }
	}
	public static void drawLine(BufferedImage buff, Point2D p1, float z1, Point2D p2, float z2)
	{
	    int x0=p1.x, y0=p1.y;     float z0 = z1;
	    int xEnd=p2.x, yEnd=p2.y; float zEnd = z2;
	    int dx = Math.abs(xEnd - x0),  dy = Math.abs(yEnd - y0);
	    float dz = Math.abs(zEnd - z0);

	    if(dx==0 && dy==0)
	    {
	    	drawPoint(buff,p1,z0);
	    	return;
	    }
	    
	    // if slope is greater than 1, then swap the role of x and y
	    boolean x_y_role_swapped = (dy > dx); 
	    if(x_y_role_swapped)
	    {
	    	x0=p1.y; 
	    	y0=p1.x;
	    	xEnd=p2.y; 
	    	yEnd=p2.x;
	    	dx = Math.abs(xEnd - x0);
	    	dy = Math.abs(yEnd - y0);
	    }
	    
	    // initialize the decision parameter and increments
	    int p = 2 * dy - dx;
	    int twoDy = 2 * dy,  twoDyMinusDx = 2 * (dy - dx);
	    int x=x0, y=y0;
	    
	    // set step increment to be positive or negative
	    int step_x = x0<xEnd ? 1 : -1;
	    int step_y = y0<yEnd ? 1 : -1;
	    
	    // deal with setup for color interpolation
	    // first get r,g,b integer values at the end points
	    int r0=p1.c.getR_int(), rEnd=p2.c.getR_int();
	    int g0=p1.c.getG_int(), gEnd=p2.c.getG_int();
	    int b0=p1.c.getB_int(), bEnd=p2.c.getB_int();
	    
	    
	    // compute the change in r,g,b 
	    int dr=Math.abs(rEnd-r0), dg=Math.abs(gEnd-g0), db=Math.abs(bEnd-b0);
	    
	    
	    // set step increment to be positive or negative 
	    int step_r = r0<rEnd ? 1 : -1;
	    int step_g = g0<gEnd ? 1 : -1;
	    int step_b = b0<bEnd ? 1 : -1;
	    
	    int step_z = z0 < zEnd ? 1:-1;
	    // compute whole step in each color that is taken each time through loop
	    int whole_step_r = step_r*(dr/dx);
	    int whole_step_g = step_g*(dg/dx);
	    int whole_step_b = step_b*(db/dx);
	    float whole_step_z = z0 < zEnd ? dz/dx : -dz/dx;
	    
	    // compute remainder, which will be corrected depending on decision parameter
	    dr=dr%dx;
	    dg=dg%dx; 
	    db=db%dx;
	    dz=dz%dx;
	    
	    // initialize decision parameters for red, green, and blue
	    int p_r = 2 * dr - dx;
	    int twoDr = 2 * dr,  twoDrMinusDx = 2 * (dr - dx);
	    int r=r0;
	    
	    int p_g = 2 * dg - dx;
	    int twoDg = 2 * dg,  twoDgMinusDx = 2 * (dg - dx);
	    int g=g0;
	    
	    int p_b = 2 * db - dx;
	    int twoDb = 2 * db,  twoDbMinusDx = 2 * (db - dx);
	    int b=b0;
	    
	    float p_z = 2*dz -dx;
	    float twoDz = 2 * dz, twoDzMinusDd = 2 * (dz - dx);
	    float z = z0;
	    
	    
	    
	    // draw start pixel
	    if(x_y_role_swapped)
	    {
	    	if(x>=0 && x<buff.getHeight() && y>=0 && y<buff.getWidth() && z > d_buff[y][x]) {
	    		buff.setRGB(y, buff.getHeight()-x-1, (r<<16) | (g<<8) | b);
	    		d_buff[y][x] = z;
	    	}
	    		
	    }
	    else
	    {
	    	if(y>=0 && y<buff.getHeight() && x>=0 && x<buff.getWidth() && z > d_buff[x][y]) {
	    		buff.setRGB(x, buff.getHeight()-y-1, (r<<16) | (g<<8) | b);
	    		d_buff[x][y] = z;
	    	}
	    }
	    
	    while (x != xEnd) 
	    {
	    	// increment x and y
	    	x+=step_x;
	    	if (p < 0)
	    		p += twoDy;
	    	else 
	    	{
	    		y+=step_y;
	    		p += twoDyMinusDx;
	    	}
		        
	    	// increment r by whole amount slope_r, and correct for accumulated error if needed
	    	r+=whole_step_r;
	    	if (p_r < 0)
	    		p_r += twoDr;
	    	else 
	    	{
	    		r+=step_r;
	    		p_r += twoDrMinusDx;
	    	}
		    
	    	// increment g by whole amount slope_b, and correct for accumulated error if needed  
	    	g+=whole_step_g;
	    	if (p_g < 0)
	    		p_g += twoDg;
	    	else 
	    	{
	    		g+=step_g;
	    		p_g += twoDgMinusDx;
	    	}
		    
	    	// increment b by whole amount slope_b, and correct for accumulated error if needed
	    	b+=whole_step_b;
	    	if (p_b < 0)
	    		p_b += twoDb;
	    	else 
	    	{
	    		b+=step_b;
	    		p_b += twoDbMinusDx;
	    	}
	    	
	    	z += whole_step_z;
	    	if (p_z < 0)
	    		p_z += twoDz;
	    	else 
	    	{
	    		z += step_z;
	    		p_z += twoDzMinusDd;
	    	}
		    
	    	if(x_y_role_swapped)
	    	{
	    		if(x>=0 && x<buff.getHeight() && y>=0 && y<buff.getWidth() && z > d_buff[y][x]) {
	    			buff.setRGB(y, buff.getHeight()-x-1, (r<<16) | (g<<8) | b);
	    			d_buff[y][x] = z;
	    		}
	    		
	    	}
	    	else
	    	{
	    		if(y>=0 && y<buff.getHeight() && x>=0 && x<buff.getWidth() && z > d_buff[x][y]) {
	    			buff.setRGB(x, buff.getHeight()-y-1, (r<<16) | (g<<8) | b);
	    			d_buff[x][y] = z;
	    		}
	    	}
	    }
	}

	/**********************************************************************
	 * Draws a filled triangle. 
	 * The triangle may be filled using flat fill or smooth fill. 
	 * This routine fills columns of pixels within the left-hand part, 
	 * and then the right-hand part of the triangle.
	 *   
	 *	                         *
	 *	                        /|\
	 *	                       / | \
	 *	                      /  |  \
	 *	                     *---|---*
	 *	            left-hand       right-hand
	 *	              part             part
	 *
	 * @param buff
	 *          Buffer object.
	 * @param p1
	 *          First given vertex of the triangle.
	 * @param p2
	 *          Second given vertex of the triangle.
	 * @param p3
	 *          Third given vertex of the triangle.
	 * @param do_smooth
	 *          Flag indicating whether flat fill or smooth fill should be used.                   
	 */
	public static void drawTriangle(BufferedImage buff, Point2D p1, Point2D p2, Point2D p3, boolean do_smooth)
	{
	    // sort the triangle vertices by ascending x value
	    Point2D p[] = sortTriangleVerts(p1,p2,p3);
	    
	    int x; 
	    float y_a, y_b;
	    float dy_a, dy_b;
	    float dr_a=0, dg_a=0, db_a=0, dr_b=0, dg_b=0, db_b=0;
	    
	    Point2D side_a = new Point2D(p[0]), side_b = new Point2D(p[0]);
	    
	    if(!do_smooth)
	    {
	    	side_a.c = new ColorType(p1.c);
	    	side_b.c = new ColorType(p1.c);
	    }
	    
	    y_b = p[0].y;
	    dy_b = ((float)(p[2].y - p[0].y))/(p[2].x - p[0].x);
	    
	    if(do_smooth)
	    {
	    	// calculate slopes in r, g, b for segment b
	    	dr_b = ((float)(p[2].c.r - p[0].c.r))/(p[2].x - p[0].x);
	    	dg_b = ((float)(p[2].c.g - p[0].c.g))/(p[2].x - p[0].x);
	    	db_b = ((float)(p[2].c.b - p[0].c.b))/(p[2].x - p[0].x);
	    }
	    
	    // if there is a left-hand part to the triangle then fill it
	    if(p[0].x != p[1].x)
	    {
	    	y_a = p[0].y;
	    	dy_a = ((float)(p[1].y - p[0].y))/(p[1].x - p[0].x);
		    
	    	if(do_smooth)
	    	{
	    		// calculate slopes in r, g, b for segment a
	    		dr_a = ((float)(p[1].c.r - p[0].c.r))/(p[1].x - p[0].x);
	    		dg_a = ((float)(p[1].c.g - p[0].c.g))/(p[1].x - p[0].x);
	    		db_a = ((float)(p[1].c.b - p[0].c.b))/(p[1].x - p[0].x);
	    	}
		    
		    // loop over the columns for left-hand part of triangle
		    // filling from side a to side b of the span
		    for(x = p[0].x; x < p[1].x; ++x)
		    {
		    	drawLine(buff, side_a, side_b);

		    	++side_a.x;
		    	++side_b.x;
		    	y_a += dy_a;
		    	y_b += dy_b;
		    	side_a.y = (int)y_a;
		    	side_b.y = (int)y_b;
		    	if(do_smooth)
		    	{
		    		side_a.c.r +=dr_a;
		    		side_b.c.r +=dr_b;
		    		side_a.c.g +=dg_a;
		    		side_b.c.g +=dg_b;
		    		side_a.c.b +=db_a;
		    		side_b.c.b +=db_b;
		    	}
		    }
	    }
	    
	    // there is no right-hand part of triangle
	    if(p[1].x == p[2].x)
	    	return;
	    
	    // set up to fill the right-hand part of triangle 
	    // replace segment a
	    side_a = new Point2D(p[1]);
	    if(!do_smooth)
	    	side_a.c =new ColorType(p1.c);
	    
	    y_a = p[1].y;
	    dy_a = ((float)(p[2].y - p[1].y))/(p[2].x - p[1].x);
	    if(do_smooth)
	    {
	    	// calculate slopes in r, g, b for replacement for segment a
	    	dr_a = ((float)(p[2].c.r - p[1].c.r))/(p[2].x - p[1].x);
	    	dg_a = ((float)(p[2].c.g - p[1].c.g))/(p[2].x - p[1].x);
	    	db_a = ((float)(p[2].c.b - p[1].c.b))/(p[2].x - p[1].x);
	    }

	    // loop over the columns for right-hand part of triangle
	    // filling from side a to side b of the span
	    for(x = p[1].x; x <= p[2].x; ++x)
	    {
	    	drawLine(buff, side_a, side_b);
		    
	    	++side_a.x;
	    	++side_b.x;
	    	y_a += dy_a;
	    	y_b += dy_b;
	    	side_a.y = (int)y_a;
	    	side_b.y = (int)y_b;
	    	if(do_smooth)
	    	{
	    		side_a.c.r +=dr_a;
	    		side_b.c.r +=dr_b;
	    		side_a.c.g +=dg_a;
	    		side_b.c.g +=dg_b;
	    		side_a.c.b +=db_a;
	    		side_b.c.b +=db_b;
	    	}
	    }
	}
	public static void drawTriangle(BufferedImage buff, Point2D p1,float z1, Point2D p2,float z2, Point2D p3,float z3, boolean do_smooth)
	{
	    // sort the triangle vertices by ascending x value
		
	    Point2D p[] = sortTriangleVerts(p1,p2,p3);
	    float z[] = sortTriangleVerts_z(p1,p2,p3,z1,z2,z3);
	    
	    int x; 
	    float y_a, y_b;
	    float dy_a, dy_b;
	    float dr_a=0, dg_a=0, db_a=0, dr_b=0, dg_b=0, db_b=0;
	    
	    float z_a, z_b;
	    float dz_a = 0, dz_b = 0;
	    
	    Point2D side_a = new Point2D(p[0]), side_b = new Point2D(p[0]);
	    
	    
	    if(!do_smooth)
	    {
	    	side_a.c = new ColorType(p1.c);
	    	side_b.c = new ColorType(p1.c);
	    }
	    
	    y_b = p[0].y;
	    dy_b = ((float)(p[2].y - p[0].y))/(p[2].x - p[0].x);
	    
	    z_b = z[0];
	    dz_b = ((float)(z[2] - z[0]))/(p[2].x - p[0].x);
	    
	    if(do_smooth)
	    {
	    	// calculate slopes in r, g, b for segment b
	    	dr_b = ((float)(p[2].c.r - p[0].c.r))/(p[2].x - p[0].x);
	    	dg_b = ((float)(p[2].c.g - p[0].c.g))/(p[2].x - p[0].x);
	    	db_b = ((float)(p[2].c.b - p[0].c.b))/(p[2].x - p[0].x);
	    	
	    }
	    
	    // if there is a left-hand part to the triangle then fill it
	    if(p[0].x != p[1].x)
	    {
	    	y_a = p[0].y;
	    	dy_a = ((float)(p[1].y - p[0].y))/(p[1].x - p[0].x);
		    
	    	z_a = z[0];
	    	dz_a  = ((float)(z[1] - z[0]))/(p[1].x - p[0].x);
	    	
	    	if(do_smooth)
	    	{
	    		// calculate slopes in r, g, b for segment a
	    		dr_a = ((float)(p[1].c.r - p[0].c.r))/(p[1].x - p[0].x);
	    		dg_a = ((float)(p[1].c.g - p[0].c.g))/(p[1].x - p[0].x);
	    		db_a = ((float)(p[1].c.b - p[0].c.b))/(p[1].x - p[0].x);
	    	}
		    
		    // loop over the columns for left-hand part of triangle
		    // filling from side a to side b of the span
		    for(x = p[0].x; x < p[1].x; ++x)
		    {
		    	drawLine(buff, side_a,z_a, side_b,z_b);

		    	++side_a.x;
		    	++side_b.x;
		    	y_a += dy_a;
		    	y_b += dy_b;
		    	side_a.y = (int)y_a;
		    	side_b.y = (int)y_b;
		    	
		    	z_a += dz_a;
		    	z_b += dz_b;
		    	
		    	
		    	
		    	if(do_smooth)
		    	{
		    		side_a.c.r +=dr_a;
		    		side_b.c.r +=dr_b;
		    		side_a.c.g +=dg_a;
		    		side_b.c.g +=dg_b;
		    		side_a.c.b +=db_a;
		    		side_b.c.b +=db_b;
		    	}
		    }
	    }
	    
	    // there is no right-hand part of triangle
	    if(p[1].x == p[2].x)
	    	return;
	    
	    // set up to fill the right-hand part of triangle 
	    // replace segment a
	    side_a = new Point2D(p[1]);
	    if(!do_smooth)
	    	side_a.c =new ColorType(p1.c);
	    
	    y_a = p[1].y;
	    dy_a = ((float)(p[2].y - p[1].y))/(p[2].x - p[1].x);
	    
	    z_a = z[1];
	    dz_a = ((float)(z[2] - z[1]))/(p[2].x - p[1].x);
	    
	    if(do_smooth)
	    {
	    	// calculate slopes in r, g, b for replacement for segment a
	    	dr_a = ((float)(p[2].c.r - p[1].c.r))/(p[2].x - p[1].x);
	    	dg_a = ((float)(p[2].c.g - p[1].c.g))/(p[2].x - p[1].x);
	    	db_a = ((float)(p[2].c.b - p[1].c.b))/(p[2].x - p[1].x);
	    }

	    // loop over the columns for right-hand part of triangle
	    // filling from side a to side b of the span
	    for(x = p[1].x; x <= p[2].x; ++x)
	    {
	    	drawLine(buff, side_a,z_a, side_b,z_b);
		    
	    	++side_a.x;
	    	++side_b.x;
	    	y_a += dy_a;
	    	y_b += dy_b;
	    	side_a.y = (int)y_a;
	    	side_b.y = (int)y_b;
	    	
	    	z_a += dz_a;
	    	z_b += dz_b;
	    	if(do_smooth)
	    	{
	    		side_a.c.r +=dr_a;
	    		side_b.c.r +=dr_b;
	    		side_a.c.g +=dg_a;
	    		side_b.c.g +=dg_b;
	    		side_a.c.b +=db_a;
	    		side_b.c.b +=db_b;
	    	}
	    }
	}
	/**********************************************************************
	 * Helper function to bubble sort triangle vertices by ascending x value.
	 * 
	 * @param p1
	 *          First given vertex of the triangle.
	 * @param p2
	 *          Second given vertex of the triangle.
	 * @param p3
	 *          Third given vertex of the triangle.
	 * @return 
	 *          Array of 3 points, sorted by ascending x value.
	 */
	private static Point2D[] sortTriangleVerts(Point2D p1, Point2D p2, Point2D p3)
	{
	    Point2D pts[] = {p1, p2, p3};
	    Point2D tmp;
	    int j=0;
	    boolean swapped = true;
	         
	    while (swapped) 
	    {
	    	swapped = false;
	    	j++;
	    	for (int i = 0; i < 3 - j; i++) 
	    	{                                       
	    		if (pts[i].x > pts[i + 1].x) 
	    		{                          
	    			tmp = pts[i];
	    			pts[i] = pts[i + 1];
	    			pts[i + 1] = tmp;
	    			swapped = true;
	    		}
	    	}                
	    }
	    return(pts);
	}
	private static float[] sortTriangleVerts_z(Point2D p1, Point2D p2, Point2D p3, float z1, float z2, float z3)
	{
	    Point2D pts[] = {p1, p2, p3};
	    Point2D tmp;
	    
	    float zs[] = {z1,z2,z3};
	    float tmp_z;
	    
	    int j=0;
	    boolean swapped = true;
	         
	    while (swapped) 
	    {
	    	swapped = false;
	    	j++;
	    	for (int i = 0; i < 3 - j; i++) 
	    	{                                       
	    		if (pts[i].x > pts[i + 1].x) 
	    		{                          
	    			tmp = pts[i];
	    			tmp_z = zs[i];
	    			
	    			pts[i] = pts[i + 1];
	    			zs[i] = zs[i+1];
	    			
	    			pts[i + 1] = tmp;
	    			zs[i+1] = tmp_z;
	    			swapped = true;
	    		}
	    	}                
	    }
	    return(zs);
	}
	
	public static ColorType getTextureColor(float x,float y,BufferedImage texture){
		float w = texture.getWidth();
		float h = texture.getHeight();
		
		int u = (int) Math.floor(w*(float)(x+1024)/2048);
		int v = (int) Math.floor(h*(float)(y+1024)/2048);
//		System.out.printf("u,v[%d, %d]\n",u,v);
		int rgb_array = texture.getRGB(u, v);
		ColorType c = Array2Color(rgb_array);
		//System.out.printf("x(%d) x_left(%d) u(%f)\n",x,x_left,u);
		//System.out.printf("y(%d) y_down(%d) v(%f)\n",y,y_down,v);
		
//		int u_ceil = (int) Math.ceil(u);
//		int u_floor = (int) Math.floor(u);
//		int v_ceil = (int) Math.ceil(v);
//		int v_floor = (int) Math.floor(v);
		//System.out.println("get Texture Color");
		
		//System.out.printf("u_floor,u_ceil,v_floor,v_ceil:(%d,%d,%d,%d)\n",u_floor,u_ceil,v_floor,v_ceil);
//		int rgb_array1 = texture.getRGB(u_floor,v_ceil);
//		ColorType c1 = Array2Color(rgb_array1);
//		int rgb_array2 = texture.getRGB(u_floor,v_floor );
//		ColorType c2 = Array2Color(rgb_array2);
//		
//		int rgb_array3 = texture.getRGB(u_ceil,v_ceil);
//		ColorType c3 = Array2Color(rgb_array3);
//		int rgb_array4 = texture.getRGB(u_ceil,v_floor ); 
//		ColorType c4 = Array2Color(rgb_array4);
//		
//		//interpolate first time
//		ColorType c_inter_1 = colorInterpolation(v_ceil, c1, v_floor, c2, v);
//		ColorType c_inter_2 = colorInterpolation(v_ceil, c3, v_floor, c4, v);
//		//interpolate twice
//		ColorType c = colorInterpolation(u_floor, c_inter_1 , u_ceil, c_inter_2, u);
	
		return c;
	}
	
	public static ColorType colorInterpolation(int x1,ColorType c1,int x2, ColorType c2,float x){
		int dx = Math.abs(x1-x2);
		
		
		float dx1 = Math.abs(x-x1);
		float dx2 = Math.abs(x-x2);
		
		float r=0,g=0,b=0;
		if(x==x1){
			r = c1.r;
			g = c1.g;
			b = c1.b;
		}
		if(x==x2){
			r = c2.r;
			g = c2.g;
			b = c2.b;
		}else{
			r = ((float)dx2/dx)*c1.r+((float)dx1/dx)*c2.r;
			g = ((float)dx2/dx)*c1.g+((float)dx1/dx)*c2.g;
			b = ((float)dx2/dx)*c1.b+((float)dx1/dx)*c2.b;
		}
		
		ColorType c=new ColorType(r,g,b);
		return c;
	}
	
	private static ColorType Array2Color(int colorArray){
		
		float r = (float)((colorArray&0x00ff0000)>>16)/255.0f;
		float g =(float)((colorArray&0x0000ff00)>>8)/255.0f;
		float b = (float)(colorArray&0x000000ff)/255.0f;
		
		ColorType c = new ColorType(r,g,b);
		return c;
	}
	

}
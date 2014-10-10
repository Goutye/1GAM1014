package ogam1014.collide;

import java.awt.Point;

public abstract class Collide {
	
	public static final boolean AABB_AABB(Box a, Box b) {
		return b.x 			<= a.getXMax()
			&& b.getXMax() 	>= a.x
			&& b.y 			<= a.getYMax()
			&& b.getYMax()	>= a.y;
	}
	
	public static final boolean aabb(double x, double y, int w, int h,
									 double x2, double y2, int w2, int h2) {
		return x2 <= x + w 
			&& x2 + w2 >= x
			&& y2 <= y + h
			&& y2 + h2 >= y;
	}
	
	public static final boolean AABB_point(Box b, Point p) {
		return p.x >= b.x
			&& p.x <= b.getMaxX()
			&& p.y >= b.y
			&& p.y <= b.getMaxY();
	}
}

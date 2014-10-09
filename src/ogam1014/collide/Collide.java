package ogam1014.collide;

import java.awt.Point;

public abstract class Collide {
	
	public static final boolean AABB_AABB(Box a, Box b) {
		return b.x 			<= a.getXMax()
			&& b.getXMax() 	>= a.x
			&& b.y 			<= a.getYMax()
			&& b.getYMax()	>= a.y;
	}
	
	public static final boolean AABB_point(Box b, Point p) {
		return p.x >= b.x
			&& p.x <= b.getMaxX()
			&& p.y >= b.y
			&& p.y <= b.getMaxY();
	}
}

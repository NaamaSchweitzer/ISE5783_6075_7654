package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

	Point q0;
	Vector normal;
	
	public Plane(Point p1, Point p2, Point p3) {
		normal = null;
		q0 = p1;
	}
	
	public Plane(Point p,Vector v) {
		q0 = p;
		normal = v.normalize();
	}
	
	public Point getQ0() {
		return q0;
	}
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
}

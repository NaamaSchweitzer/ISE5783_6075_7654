package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

	private final Point q0;
	private final Vector normal; // The normal vector to the plane

	/**
	 * Constructs a plane from three points
	 *
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Plane(Point p1, Point p2, Point p3) {
		// Calculate the normal vector to the plane using the cross product of two
		// vectors
		normal = (p1.subtract(p2)).crossProduct(p1.subtract(p3)).normalize();
		q0 = p1; // Assign one of the points as the reference point on the plane
	}

	/**
	 * Constructs a plane from a point and a normal vector
	 *
	 * @param p
	 * @param v
	 */
	public Plane(Point p, Vector v) {
		normal = v.normalize(); // Normalize the normal vector
		q0 = p;
	}

	public Point getQ0() {
		return q0;
	}

	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point p) {
		return normal; // The normal vector to the plane is constant for all points on the plane
	}
}

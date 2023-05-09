package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

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

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		// get ray point and vector
		Point rayPoint = ray.getP0();
		Vector rayVector = ray.getDir();

		// check if the ray is parallel to the plane
		if (isZero(normal.dotProduct(rayVector))) // dotProduct = 0 => parallel
			return null;
		// check if the ray and the plane start at the same point
		if (ray.getP0().equals(q0))
			return null;
		try {

			double t = alignZero((normal.dotProduct(q0.subtract(rayPoint))) / (normal.dotProduct(rayVector)));
			// check if the ray starts on the plane
			if (isZero(t))
				return null;
			// check if the ray crosses the plane
			else if (t > 0)
				return List.of(ray.getPoint(t));
			// no intersection between the ray and the plane
			else
				return null;

		} catch (Exception ex) {
			// p.subtract(rayP) is vector zero, which means the ray point is equal to the
			// plane point (ray start on plane)
			return null;
		}
	}

}

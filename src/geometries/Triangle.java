package geometries;

import java.util.List;

import static primitives.Util.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Triangle extends Polygon {

	/**
	 * Constructs a triangle with given 3 points
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3); // Use the constructor of Polygon and set the size of points list to 3
	}

	/**
	 * The function returns intersections between a ray and a triangle. If there are
	 * no intersection points the function returns null
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = plane.findIntersections(ray);
		if (intersections == null)
			return null;

		Point rayPoint = ray.getP0();
		Vector rayVector = ray.getDir();

		Vector v1 = vertices.get(0).subtract(rayPoint); // vertex v1
		Vector v2 = vertices.get(1).subtract(rayPoint); // vertex v2
		Vector v3 = vertices.get(2).subtract(rayPoint); // vertex v3

		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		double vN1 = rayVector.dotProduct(n1); // rayVector*n1
		double vN2 = rayVector.dotProduct(n2);// rayVector*n2
		double vN3 = rayVector.dotProduct(n3);// rayVector*n3
		// check if all vNi are not zero
		if (isZero(vN1) || isZero(vN2) || isZero(vN3))
			return null;
		// check if all vNi have a same sign(-/+)
		if ((vN1 > 0 && vN2 > 0 && vN3 > 0) || (vN1 < 0 && vN2 < 0 && vN3 < 0)) {
			return intersections;
		} else {
			return null;
		}

	}

}

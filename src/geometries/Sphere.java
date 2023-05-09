package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	private final Point center; // The center point of the sphere

	/**
	 * @param radius
	 * @param center
	 */
	public Sphere(double radius, Point center) {
		super(radius);
		this.center = center;
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point p) {
		return p.subtract(center).normalize(); // Calculate and return the normal vector at the given point on the
												// sphere
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// point and vector of ray
		Point p0 = ray.getP0();// ray point
		Vector v = ray.getDir();// ray vector
		// check if ray point is the center
		if (p0.equals(center)) //
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(p0);// the vector between center and ray
		double tm = v.dotProduct(u); // the scale for the ray in order to get parallel to the sphere center
		double d = Math.sqrt(u.lengthSquared() - tm * tm);// get the distance between the ray and the sphere center
		// check if d is bigger then radius-the ray doesn't cross the sphere
		if (d > radius)
			return null;
		double th = Math.sqrt(radius * radius - d * d);// according pitagoras
		double t1 = tm + th;
		double t2 = tm - th;
		if (t1 > 0 && t2 > 0 && !isZero(ray.getPoint(t1).subtract(center).dotProduct(v))
				&& !isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) // if orthogonal -> no intersection
			return List.of(ray.getPoint(t1), ray.getPoint(t2));
		else if (t1 > 0 && !isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) // if only t1 is not orthogonal and
																						// positive
			return List.of(ray.getPoint(t1));
		else if (t2 > 0 && !isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) // if only t2 is not orthogonal and
																						// positive
			return List.of(ray.getPoint(t2));
		else
			return null;
	}

}

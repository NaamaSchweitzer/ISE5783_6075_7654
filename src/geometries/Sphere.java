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

	/**
	 * @param Ray ray - the ray that intersect the plane
	 * @return List<GeoPoint> - the list of intersection GeoPoints
	 */
	/*
	 * @Override public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) { //
	 * point and vector of ray Point p0 = ray.getP0();// ray point Vector v =
	 * ray.getDir();// ray vector
	 * 
	 * // check if ray point is the center if (p0.equals(center)) // return
	 * List.of(new GeoPoint(this, ray.getPoint(radius)));
	 * 
	 * Vector u = center.subtract(p0);// the vector between center and ray double tm
	 * = alignZero(v.dotProduct(u)); // the scale for the ray in order to get
	 * parallel to the sphere center // double d =
	 * alignZero(Math.sqrt(u.lengthSquared() - tm * tm));// get the // distance
	 * between the ray and the sphere center // check if d is bigger then radius-the
	 * ray doesn't cross the sphere // if (d >= radius) // return null; double dSqr
	 * = u.lengthSquared() - (tm * tm); double thSqr = radius * radius - dSqr; if
	 * (alignZero(thSqr) <= 0) return null; double th =
	 * alignZero(Math.sqrt(thSqr));// according pitagoras double t1 = alignZero(tm +
	 * th); double t2 = alignZero(tm - th); if (t1 > 0 && t2 > 0 &&
	 * !isZero(ray.getPoint(t1).subtract(center).dotProduct(v)) &&
	 * !isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) // if orthogonal ->
	 * no intersection // convert from list of Point to GeoPoint: return List.of(new
	 * GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	 * 
	 * else if (t1 > 0 && !isZero(ray.getPoint(t1).subtract(center).dotProduct(v)))
	 * // if only t1 is not orthogonal and // positive // convert from list of Point
	 * to GeoPoint: return List.of(new GeoPoint(this, ray.getPoint(t1))); else if
	 * (t2 > 0 && !isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) // if
	 * only t2 is not orthogonal and // positive // convert from list of Point to
	 * GeoPoint: return List.of(new GeoPoint(this, ray.getPoint(t2))); else return
	 * null; }
	 */

	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

		Vector pointToCenter;
		try {
			pointToCenter = center.subtract(ray.getP0());
		} catch (IllegalArgumentException ignore) {
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		}

		double tm = pointToCenter.dotProduct(ray.getDir());
		double distanceFromCenterSquared = pointToCenter.dotProduct(pointToCenter) - tm * tm;
		double thSquared = radius * radius - distanceFromCenterSquared;
		// check that ray crosses area of sphere, if not then return null
		if (alignZero(thSquared) <= 0)
			return null;

		double th = Math.sqrt(thSquared);
		double secondDistance = tm + th;
		if (alignZero(secondDistance) <= 0)
			return null;
		double firstDistance = tm - th;
		return firstDistance <= 0 ? List.of(new GeoPoint(this, ray.getPoint(secondDistance))) //
				: List.of(new GeoPoint(this, ray.getPoint(firstDistance)),
						new GeoPoint(this, ray.getPoint(secondDistance)));
	}
}

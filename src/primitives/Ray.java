package primitives;

import java.util.List;
import java.util.Objects;

import geometries.Intersectable.GeoPoint;

public class Ray {
	final private Point p0; // Private field for the starting point of the ray
	final private Vector dir; // Private field for the direction of the ray

	/**
	 * Constructor that takes a Point object for the starting point of the ray and a
	 * Vector object for the direction of the ray The direction vector is normalized
	 * to ensure it has unit length
	 */
	public Ray(Point p, Vector vec) {
		dir = vec.normalize(); // normalize the direction vector
		p0 = p; // set the starting point of the ray
	}

	/**
	 * Override of the toString() method to return a string representation of the
	 * Ray object
	 */
	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}


	/**
	 * Override of the equals() method to compare two Ray objects for equality based
	 * on their p0 and dir fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		return Objects.equals(dir, other.dir) && Objects.equals(p0, other.p0);
	}

	public Point getP0() {
		return p0;
	}

	public Vector getDir() {
		return dir;
	}

	/**
	 * 
	 * @param t
	 * @return If t is 0 the function returns p0 otherwise returns a new point which
	 *         is P0 + vector direction * scalar
	 */
	public Point getPoint(double t) {
		Point p = p0.add(dir.scale(t));
		return Util.isZero(t) ? p0 : p;
	}

	/**
	 * In the points list - find the point with minimal distance from the ray head
	 * point and return it
	 * 
	 * @param pointList list of intersection point
	 * @return the closet point to ray point (p0)
	 */
	public Point findClosestPoint(List<Point> points) {
		return (points == null || points.isEmpty()) ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * this function return the close {@link GeoPoint} point to the beginning of the
	 * ray
	 * 
	 * @param list of GeoPoint points
	 * @return the closest GeoPoint point to the beginning of the ray
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty())
            return null;
        GeoPoint closest = null;
        double minDistance = Double.MAX_VALUE;
        for (GeoPoint p : points) {
            double distance = p.point.distance(p0);
            if (distance < minDistance) {
                closest = p;
                minDistance = distance;
            }
        }
        return closest;
    }
	
	/*public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
		GeoPoint closestGeoPoint = null;
		double minDistance = Double.POSITIVE_INFINITY;
		for (GeoPoint item : points) {
			double distance = item.point.distance(p0);
			if (distance < minDistance) {
				minDistance = distance;
				closestGeoPoint = item;
			}
		}
		return closestGeoPoint;
	}*/
}

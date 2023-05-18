package primitives;

import java.util.List;
import java.util.Objects;

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
	 * Override of the hashCode() method to provide a hash code for a Ray object
	 * based on its p0 and dir fields
	 */
	@Override
	public int hashCode() {
		return Objects.hash(dir, p0);
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
	public Point findClosestPoint(List<Point> pointList) {
		if (pointList == null)// if the list is empty
			return null;

		Point closestPoint = pointList.get(0); // begin with the first point
		double min = p0.distance(pointList.get(0)); // find the distance between the point

		for (int i = 0; i < pointList.size(); i++) // run on the list
		{
			if (p0.distance(pointList.get(i)) < min) // if there is a closer point update
			{
				min = p0.distance(pointList.get(i));
				closestPoint = pointList.get(i);
			}
		}
		return closestPoint;
	}
}

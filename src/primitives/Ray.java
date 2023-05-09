package primitives;

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
	 * @return If t is 0 the function returns p0 otherwise returns a new point which is P0 + vector direction * scalar 
	 */
	public Point getPoint(double t) {
		Point p = p0.add(dir.scale(t));
		return Util.isZero(t) ? p0 : p;
	}
}

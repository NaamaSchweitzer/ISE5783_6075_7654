package primitives;

import java.util.Objects;

/**
 * 
 * @author Hadas Carmen & Naama Schweitzer
 *
 */
public class Point {

	final Double3 xyz; // Private field for the X, Y, and Z values of the 3 damentional point

	/** Zero triad (0,0,0) */
	public static final Point ZERO = new Point(0, 0, 0);

	/**
	 * Constructor that gets three double values for the X, Y, and Z
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * Constructor that gets a Double3 object
	 */
	Point(Double3 x) {
		xyz = x;
	}

	/**
	 * get the x value of the point
	 * 
	 * @return x coordinate value
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * get the y value of the point
	 * 
	 * @return y coordinate value
	 */
	public double getY() {
		return xyz.d2;
	}

	/**
	 * get the z value of the point
	 * 
	 * @return z coordinate value
	 */
	public double getZ() {
		return xyz.d3;
	}

	/**
	 * Override of the hashCode() method to provide a hash code for a Point object
	 * based on its xyz field
	 */
	@Override
	public int hashCode() {
		return Objects.hash(xyz);
	}

	/**
	 * Override of the equals() method to compare two Point objects for equality
	 * based on their xyz field
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return Objects.equals(xyz, other.xyz);
	}

	/**
	 * Override of the toString() method to return a string representation of a
	 * Point object
	 */
	@Override
	public String toString() {
		return "Point [xyz=" + xyz + "]";
	}

	/**
	 * Method to add a Vector object to the current point and return a new Point
	 * object as the result
	 */
	public Point add(Vector vec) {
		return new Point(xyz.add(vec.xyz));
	}

	/**
	 * Method to subtract another Point object from the current point and return a
	 * new Vector object as the result
	 */
	public Vector subtract(Point p) {
		return new Vector(xyz.subtract(p.xyz));
	}

	/**
	 * Method to compute the distance between the current point and another Point
	 * object using the Pythagorean theorem
	 */
	public Double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * Method to compute the squared distance between the current point and another
	 * Point object according to squared distance method: distance^2 = (x1-x2)^2 +
	 * (y1-y2)^2 + (z1-z2)^2
	 */
	public Double distanceSquared(Point p) {
		return (xyz.d1 - p.xyz.d1) * (xyz.d1 - p.xyz.d1) // (x1-x2)^2
				+ (xyz.d2 - p.xyz.d2) * (xyz.d2 - p.xyz.d2) // (y1-y2)^2
				+ (xyz.d3 - p.xyz.d3) * (xyz.d3 - p.xyz.d3); // (z1-z2)^2
	}
}

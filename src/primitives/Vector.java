package primitives;

public class Vector extends Point {

	// Override the toString() method to print the Vector's components.
	@Override
	public String toString() {
		return "Vector [xyz=" + xyz + "]";
	}

	/**
	 * Constructor for the Vector class.
	 *
	 * @param d1 the x-coordinate of the Vector
	 * @param d2 the y-coordinate of the Vector
	 * @param d3 the z-coordinate of the Vector
	 * @throws IllegalArgumentException if the Vector has zero length
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);

		// Check if the Vector has zero length and throw an exception if it does.
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("not valid");
	}

	/**
	 * Constructor for the Vector class.
	 *
	 * @param x the Double3 object containing the x, y, and z coordinates of the
	 *          Vector
	 * @throws IllegalArgumentException if the Vector has zero length
	 */
	Vector(Double3 x) {
		super(x);

		// Check if the Vector has zero length and throw an exception if it does.
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("not valid");
	}

	/**
	 * Adds the given Vector to this Vector and returns the result.
	 *
	 * @param vec the Vector to add to this Vector
	 * @return the result of adding the given Vector to this Vector
	 * @throws IllegalArgumentException if the resulting Vector is zero, or if the
	 *                                  two Vectors are opposite and have the same
	 *                                  length
	 */
	public Vector add(Vector vec) {

		// Check if the resulting Vector is zero and throw an exception if it is.
		if (vec.xyz.product(this.xyz).lowerThan(0) && vec.length() == this.length()
				&& crossProduct(vec).xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("not valid");

		// Return the result of adding the given Vector to this Vector.
		return new Vector(vec.xyz.add(this.xyz));
	}

	/**
	 * Scales this Vector by the given factor and returns the result.
	 *
	 * @param x the factor to scale this Vector by
	 * @return the result of scaling this Vector by the given factor
	 */
	public Vector scale(double x) {
		return new Vector(xyz.scale(x));
	}

	/**
	 * Calculates the cross product of this Vector and the given Vector and returns
	 * the result.
	 *
	 * @param vec the Vector to calculate the cross product with
	 * @return the result of calculating the cross product of this Vector and the
	 *         given Vector
	 * @throws IllegalArgumentException if the result of the cross product is zero
	 */
	public Vector crossProduct(Vector vec) {

		// Calculate the cross product of this Vector and the given Vector.
		Double3 crossProduct = new Double3(xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2,
				-(xyz.d1 * vec.xyz.d3 - xyz.d3 * vec.xyz.d1), xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1);

		// Check if the result of the cross product is zero and throw an exception if it
		// is.
		if (crossProduct.equals(Double3.ZERO))
			throw new IllegalArgumentException("not valid");

		// Return the result of the cross product.
		return new Vector(crossProduct);
	}

	/**
	 * Calculates the squared length of this Vector and returns the result.
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	public double length() {
		// Return the square root of the squared length of the vector.
		return Math.sqrt(lengthSquared());
	}

	public Vector normalize() {
		// Return a new vector that is the normalized version of this vector.
		// The normalization is achieved by dividing each component of this vector by
		// its length.
		return new Vector(this.xyz.reduce(length()));
	}

	public double dotProduct(Vector vec) {
		// Return the dot product of this vector and the given vector.
		// The dot product is calculated by multiplying each corresponding component of
		// the two vectors and summing the results.
		return (xyz.d1 * vec.xyz.d1) + (xyz.d2 * vec.xyz.d2) + (xyz.d3 * vec.xyz.d3);
	}
}

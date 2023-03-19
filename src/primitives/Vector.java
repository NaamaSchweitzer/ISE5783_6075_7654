package primitives;

public class Vector extends Point {

	@Override
	public String toString() {
		return "Vector [xyz=" + xyz + "]";
	}

	/**
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		 if(xyz == Double3.ZERO)
			 throw new IllegalArgumentException("not valid");
	}

	/**
	 * @param x
	 */
	Vector(Double3 x) {
		super(x);
		if(xyz == Double3.ZERO)
			 throw new IllegalArgumentException("not valid");
	}
	
	public Vector add(Vector vec) {
		return new Vector(vec.xyz.add(this.xyz));
	}
	
	public Vector scale(double x) {
		return new Vector(xyz.scale(x));
	}

	public Vector crossProduct(Vector vec) {
		return new Vector(xyz.d1 * vec.xyz.d1, xyz.d2 * vec.xyz.d2, xyz.d3 * vec.xyz.d3);
	}
	
	public double lengthSquared() {
		return dotProduct(this);
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public Vector normalize() {
		return new Vector(this.xyz.reduce(length()));
	}	
	
	public double dotProduct(Vector vec) {
		return (xyz.d1 * vec.xyz.d1) + (xyz.d2 * vec.xyz.d2) + (xyz.d3 * vec.xyz.d3);
	}
}

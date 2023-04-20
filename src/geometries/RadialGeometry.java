package geometries;

public abstract class RadialGeometry implements Geometry {

	final protected double radius;

	/**
	 * @param radius
	 */
	public RadialGeometry(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

}

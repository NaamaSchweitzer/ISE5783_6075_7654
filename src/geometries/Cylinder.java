package geometries;

import primitives.Ray;

public class Cylinder extends Tube {
	double height;

	/**
	 * @param radius
	 * @param axisRay
	 * @param height
	 */
	public Cylinder(double radius, Ray axisRay, double height) {
		super(radius, axisRay);
		this.height = height;
	}

	public double getHeight() {
		return height;
	}
}

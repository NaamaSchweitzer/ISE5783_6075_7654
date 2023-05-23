package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

	final double height; // Private field for the height of cylinder

	/**
	 * 
	 * Constructor for creating a new cylinder object
	 * 
	 * @param radius
	 * @param axisRay
	 * @param height
	 */
	public Cylinder(double radius, Ray axisRay, double height) {
		super(radius, axisRay); // Call the constructor of the superclass
		this.height = height; // Set the height of the cylinder
	}

	public double getHeight() {
		return height;
	}

}

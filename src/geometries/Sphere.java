package geometries;

import primitives.Point;

public class Sphere extends RadialGeometry{
	
	Point center;
	double radius;
	
	/**
	 * @param radius
	 * @param center
	 * @param radius2
	 */
	public Sphere(double radius, Point center, double radius2) {
		super(radius);
		this.center = center;
		radius = radius2;
	}

	public Point getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}
	
}

package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	private final Point center; // The center point of the sphere

	/**
	 * @param radius
	 * @param center
	 */
	public Sphere(double radius, Point center) {
		super(radius);
		this.center = center;
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point p) {
		return p.subtract(center).normalize(); // Calculate and return the normal vector at the given point on the
												// sphere
	}

}

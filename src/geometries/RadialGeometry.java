package geometries;

import primitives.Point;
import primitives.Vector;

public class RadialGeometry implements Geometry {


	protected double radius;
	
	/**
	 * @param radius
	 */
	public RadialGeometry(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
}

package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

	final protected Ray axisRay; // The central ray of the tube

	/**
	 * @param radius
	 * @param axisRay
	 */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public Vector getNormal(Point p) {
		if (p.equals(axisRay.getP0()))
			return axisRay.getDir(); // Return the direction of the central ray if the given point is on it

		Vector v = p.subtract(axisRay.getP0()); // Vector from the central ray's base to the given point
		double t = axisRay.getDir().dotProduct(v); // Parameter of the projection of the vector "v" onto the central ray
		Point O = axisRay.getP0(); // The point on the central ray closest to the given point
		if (t != 0) {
			O = O.add(axisRay.getDir().scale(t)); // Calculate the closest point on the central ray to the given point
		}
		return p.subtract(O).normalize(); // Calculate and return the normal vector at the given point on the tube
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}

package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * The Geometry interface is an abstract interface. It represents a geometric
 * object in three-dimensional space. It defines a method for getting the normal
 * vector at a given point on the geometry.
 */
public interface Geometry {

	/**
	 * Calculates and returns the normal vector at a given point on the geometry.
	 * 
	 * @param p The point on the geometry to calculate the normal vector at
	 * @return The normal vector at the given point on the geometry
	 */
	public Vector getNormal(Point p);
}

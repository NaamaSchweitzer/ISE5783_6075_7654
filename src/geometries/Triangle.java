package geometries;

import primitives.Point;

public class Triangle extends Polygon {

	/**
	 * Constructs a triangle with given 3 points
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3); // Use the constructor of Polygon and set the size of points list to 3
	}
}

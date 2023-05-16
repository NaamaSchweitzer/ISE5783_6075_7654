package geometries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {

	/**
	 * A container for Geometries (Intersectables)
	 **/
	private List<Intersectable> geometries;

	/* ********* Constructors *********** */
	/**
	 * Constructor
	 * 
	 * @param geometries return a list of geometries
	 **/
	public Geometries(Intersectable... geos) {
		Collections.addAll(this.geometries, geos); // add new geometries to the end of list
	}

	/**
	 * a default constructor
	 * 
	 * @param none
	 */
	public Geometries() {
		this.geometries = new LinkedList<>();
	}

	/**
	 * 
	 * @param geos
	 */
	public void add(Intersectable... geos) {
		Collections.addAll(this.geometries, geos); // add the new geometries to list
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = null;

		for (Intersectable geo : geometries) { // run on list of geometries
			List<Point> otherIntersections = geo.findIntersections(ray); // find intersections of each geometry
			if (!otherIntersections.isEmpty()) {
				if (intersections.isEmpty()) // if no intersections were inserted yet
					intersections = new LinkedList<>(); // create a new LinkedList
				intersections.addAll(otherIntersections); // insert all intersections
			}
		}
		return intersections; // return the list of intersections
	}

}

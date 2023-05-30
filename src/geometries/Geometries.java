package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

public class Geometries extends Intersectable {

	/**
	 * A container for Geometries (Intersectables)
	 **/
	private final List<Intersectable> geometries = new LinkedList<>();

	/* ********* Constructors *********** */
	/**
	 * Constructor with list of geometries
	 * 
	 * @param geometries return a list of geometries
	 **/
	public Geometries(Intersectable... geos) {
		add(geos); // add new geometries to the end of list
	}

	/**
	 * a default constructor
	 * 
	 * @param none
	 */
	public Geometries() {
	}

	/**
	 * 
	 * @param geos
	 */
	public void add(Intersectable... geos) {
		this.geometries.addAll(List.of(geos)); // add the new geometries to list
	}

	/**
	 * This function returns all the {@link GeoPoint} intersection points with a ray
	 * and the geometries in the bundle
	 * 
	 * @param ray - that may has intersection points with the geometries
	 * @return list of the intersection points
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		for (Intersectable geo : geometries) { // run on list of geometries
			List<GeoPoint> otherIntersections = geo.findGeoIntersections(ray); // find intersections of each geometry
			if (otherIntersections != null)
				if (intersections == null) // if no intersections were inserted yet
					intersections = new LinkedList<>(otherIntersections); // create a new LinkedList
				else
					intersections.addAll(otherIntersections); // insert all intersections

		}
		return intersections; // return the list of intersections
	}

}

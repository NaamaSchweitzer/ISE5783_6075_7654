package geometries;

import java.util.List;
import java.util.Objects;

import primitives.Point;
import primitives.Ray;

/**
 * This class served all geometries for trace rays and find intersections
 * 
 * @author Hadas Carmen &amp; Naama Schweitzer
 */
public abstract class Intersectable {

	/**
	 * Helping class (contained in {@link Intersectable}) Attributes a point to the
	 * geometric type to which it belongs
	 */
	public static class GeoPoint {

		public Geometry geometry;
		public Point point;

		/**
		 * @param geometry
		 * @param point
		 */
		public GeoPoint(Geometry geometry, Point point) {
			super();
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			return Objects.equals(geometry, other.geometry) && Objects.equals(point, other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
		}
	}

	/**
	 * This function returns all the intersection points of the geometry
	 * 
	 * @param ray that may has intersection with the geometry
	 * @return list of intersections
	 */
	public final List<Point> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * The function returns list of intersection points between a ray and geometry
	 * 
	 * @param ray that may has intersection with a geometry
	 * @return a list of points that intersect a geometry
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Helper function for {@link #findGeoIntersections} The function returns list
	 * of intersection points between a ray and geometry
	 * 
	 * @param ray that may has intersection with a geometry
	 * @return a list of points that intersect a geometry
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
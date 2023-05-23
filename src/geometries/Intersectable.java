package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * This class served all geometries for trace rays and find intersections
 * 
 * @author Hadas Carmen &amp; Naama Schweitzer
 */
public interface Intersectable {
	
	List<Point> findIntersections(Ray ray);

}

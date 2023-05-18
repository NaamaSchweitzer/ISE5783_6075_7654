package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * an inheritance function from base this function returns the color of the
	 * closest point to the ray.
	 * 
	 * @param ray - the ray between camera and view plane
	 * @return the color - color of closest point
	 */
	@Override
	public Color traceRay(Ray ray) {
		List<Point> intersections = scene.geometries.findIntersections(ray);// find intersection point
		if (intersections == null) // if there are no intersection points return color of background
			return scene.background;
		Point closestPoint = ray.findClosestPoint(intersections);// find closest point between ray
		return calcColor(closestPoint); // return the color of closestPoint
	}

	/**
	 * calculate the scene color
	 * @param point 
	 * @return the scene's ambient light
	 */
	private Color calcColor(Point point) {
		return scene.background;
	}
}

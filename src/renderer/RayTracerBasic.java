package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * An inheritance function from base this function returns the color of the
	 * closest point to the ray.
	 * 
	 * @param ray - the ray between camera and view plane
	 * @return the color of closest point
	 */
	@Override
	public Color traceRay(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// find intersection point
		if (intersections == null) // if there are no intersection points return color of background
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);// find closest point between ray
		return calcColor(closestPoint); // return the color of closestPoint
	}

	/**
	 * Calculate the scene color
	 * 
	 * @param gp
	 * @return the scene's ambient light
	 */
	private Color calcColor(GeoPoint gp) {
		return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
	}
}

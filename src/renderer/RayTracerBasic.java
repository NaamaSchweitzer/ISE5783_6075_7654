package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

	private static final double DELTA = 0.1; // constant for shadow rays

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * function will check if point is unshaded
	 *
	 * @param gp - geometry point to check
	 * @param l  - light vector
	 * @param n  - normal vector
	 * @return true if unshaded
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nv) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = gp.point.add(deltaVector);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

		if (intersections != null) {
			double distance = lightSource.getDistance(gp.point);
			for (GeoPoint intersection : intersections) {
				if (intersection.point.distance(gp.point) < distance)
					return false;
			}
		}

		return true;
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
		return calcColor(closestPoint, ray); // return the color of closestPoint
	}

	/**
	 * function calculates local effects of color on point
	 * 
	 * @param gp  geometry point to color
	 * @param ray ray that intersects
	 * @return color
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = Color.BLACK;
		Vector vector = ray.getDir();
		Vector normal = gp.geometry.getNormal(gp.point);
		double nv = alignZero(normal.dotProduct(vector));
		if (nv == 0)
			return color;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector lightVector = lightSource.getL(gp.point);
			double nl = alignZero(normal.dotProduct(lightVector));
			if (nl * nv > 0) // sign(nl) == sing(nv)
				if (unshaded(gp, lightVector, normal, lightSource, nv)) {

					Color lightIntensity = lightSource.getIntensity(gp.point);
					color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
							lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
				}
		}
		return color;
	}

	/**
	 * function calculates specular color
	 * 
	 * @param material    material of geometry
	 * @param normal      normal of geometry
	 * @param lightVector light vector
	 * @param nl          dot product of normal and light vector
	 * @param vector      direction of ray
	 * @return specular color
	 */
	private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
		Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
		double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
		return material.kS.scale(Math.pow(max, material.nShininess));

	}

	/**
	 * function calculates diffusive color
	 * 
	 * @param material material of geometry
	 * @param nl       dot product of normal and light vector
	 * @return diffusive color
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

	/**
	 * function calculates color of point
	 *
	 * @param gp point to color
	 * @return color
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return gp.geometry.getEmission().add(scene.ambientLight.getIntensity(), calcLocalEffects(gp, ray));
	}
}

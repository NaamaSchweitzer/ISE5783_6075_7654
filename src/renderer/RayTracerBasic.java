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
import static primitives.Util.isZero;


public class RayTracerBasic extends RayTracerBase {

	private static final double DELTA = 0.1; // constant for shadow rays
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final Double3 INITIAL_K = Double3.ONE;

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
				if (intersection.point.distance(gp.point) < distance && gp.geometry.getMaterial().kT.equals(Double3.ZERO))
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
		GeoPoint closestPoint = findClosestIntersection(ray);// find closest point between ray
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
	 * TODO
	 * 
	 * @param geoPoint
	 * @param level
	 * @param color
	 * @param kx
	 * @param k
	 * @param ray
	 * @return
	 */
	/**
	 * Calculates reflected ray and refraction ray
	 *
	 * @param geoPoint geometry point
	 * @param ray      ray
	 * @return color
	 */
	private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Color color = Color.BLACK;
		Material material = geoPoint.geometry.getMaterial();
		Vector v = ray.getDir();
		Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
		Ray reflectedRay = constructReflectionRay(geoPoint, normal, v);
		Ray refractedRay = constructRefractionRay(geoPoint, normal, v);
		return calcGlobalEffect(reflectedRay, level, k, material.kT)
				.add(calcGlobalEffect(refractedRay, level, k, material.kT));
	}

	/**
     * TODO
     * @param geoPoint
     * @param level
     * @param color
     * @param kx
     * @param k
     * @param ray
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
    	Double3 kkx = k.product(kx);
    	if (kkx.lowerThan(MIN_CALC_COLOR_K)) 
    		return Color.BLACK;
    	GeoPoint gp = findClosestIntersection(ray);
    if (gp == null) 
    	return scene.background.scale(kx);
    return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))?
    		Color.BLACK : calcColor(gp, ray, level-1, kkx);
    
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
	 * function will construct a reflection ray
	 *
	 * @param geoPoint geometry point to check
	 * @param normal   normal vector
	 * @param vector   direction of ray to point
	 * @return reflection ray
	 */
	private Ray constructReflectionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
		Vector reflectedVector = vector.subtract(normal.scale(2 * vector.dotProduct(normal)));
		return new Ray(geoPoint.point, reflectedVector);
	}

	/**
	 * function will construct a refraction ray
	 *
	 * @param geoPoint geometry point to check
	 * @param normal   normal vector
	 * @param vector   direction of ray to point
	 * @return refraction ray
	 */
	private Ray constructRefractionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
		return new Ray(geoPoint.point, vector);
	}

	/**
	 * Find the closest intersection point with a ray.
	 *
	 * @param ray The ray to checks intersections with.
	 * @return The closest intersection point with the ray.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// find intersection point
		if (intersections == null) // if there are no intersection points return color of background
			return null;
		return ray.findClosestGeoPoint(intersections);
	}

	/**
	 * The function calculates the color of {@link GeoPoint} point on a geometry as
	 * it sees from the camera position, with consideration of the ambient-light and
	 * the transparency of the object. The final color is combination of the
	 * material color and the attributes of the object, the ambient-light color, the
	 * distance from the camera and sometimes other object in the scene. (Using
	 * {@link MAX_CALC_COLOR_LEVEL} and {@link INITIAL_K}).
	 * 
	 * @param geoPoint a point on a geometry with its geometry
	 * @param ray      the ray that constructed from the camera and intersected the
	 *                 geometry
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
	}

	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}
}

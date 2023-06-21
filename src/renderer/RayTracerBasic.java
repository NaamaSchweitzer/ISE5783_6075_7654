package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * The class extends RayTracerBase. Find the intersections of ray with the scene
 * objects and returns the color of the intersections.
 * 
 * @author Naama and Hadas
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * constant value of max recursive level of transparency calculation
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	/**
	 * stop condition of the recursive transparency calculation, when the
	 * attenuation coefficient is very small. (the max recursive level of
	 * reflection).
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * constant attenuation value, initialized for max transparency of shading-rays
	 * calculation (1, means no attenuation)
	 */
	private static final Double3 INITIAL_K = Double3.ONE;

	/**
	 * RayTracerBase constructor, using scene parameter
	 * 
	 * @param the scene that contain the traced ray
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * An inheritance function from base this function returns the color of the
	 * closest point to the ray.
	 * 
	 * @param ray - the traced ray (the ray between camera and view plane)
	 * @return the color of closest point
	 */
	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	/**
	 * The function traces rays and return average color of pixel 
	 * @param rays - rays to trace
	 * @return
	 */
	@Override
	public Color traceRays(List<Ray> rays) {

		Color color = Color.BLACK;
		for (Ray ray : rays) {
			color = color.add(traceRay(ray));
		}
		return color.reduce(rays.size());

	}

	/**
	 * Helping method for color calculation of a point on a geometry as it seems
	 * from the camera point of view. Calculating the local effects: the diffusion
	 * and the specular factors.
	 * 
	 * @param gp  - the observed point on the geometry
	 * @param ray - ray from the camera that intersect the geometry
	 * @param k   - initial attenuation coefficient value (between 0-1)
	 * @return the color of the point with consideration of local effects
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		Color color = gp.geometry.getEmission();
		Vector vector = ray.getDir();
		Vector normal = gp.geometry.getNormal(gp.point);
		double nv = alignZero(normal.dotProduct(vector));
		if (nv == 0)
			return color;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector lightVector = lightSource.getL(gp.point);
			double nl = alignZero(normal.dotProduct(lightVector));
			if (nl * nv > 0) {// sign(nl) == sing(nv)
				Double3 ktr = transparency(gp, lightSource, lightVector, normal);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
							lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
				}
			}
		}
		return color;
	}

	/**
	 * Helping method for color calculation of a point on a geometry as it sees from
	 * the camera point of view. The function is modeling transparent objects (with
	 * various opacity levels) and reflecting surfaces such as mirrors.
	 * 
	 * @param geoPoint - the observed point on the geometry
	 * @param ray      - from the camera that intersect the geometry
	 * @param level    - of recursion for the global effects calculation
	 * @param k        - initial attenuation coefficient value (between 0-1)
	 * @return the color of the point with consideration of global effects
	 */
	private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Material material = geoPoint.geometry.getMaterial();
		Vector v = ray.getDir();
		Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
		Ray reflectedRay = constructReflectionRay(geoPoint, normal, v);
		Ray refractedRay = constructRefractionRay(geoPoint, normal, v);
		return calcGlobalEffect(reflectedRay, level, k, material.kR)
				.add(calcGlobalEffect(refractedRay, level, k, material.kT));
	}

	/**
	 * Helping method for color calculation (function calcGlobalEffects) of a point
	 * on a geometry as it sees from the camera point of view. Calculating the
	 * global effects.
	 * 
	 * @param ray   - ray from the camera that intersect the geometry
	 * @param level - level of recursion
	 * @param k     - the material reflection\refraction coefficient value (between
	 *              0-1)
	 * @param kx    - product of the global and local attenuation coefficient
	 * @return the color of the point with consideration of global effect
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);

	}

	/**
	 * The function calculates specular component of the color
	 * 
	 * @param material - material of geometry, used for its kS and nShininess fields
	 *                 - its specular component and smoothness factor
	 * @param normal   - normal of geometry
	 * @param lv       - light vector
	 * @param nl       - dot product of normal and light vector
	 * @param vector   - direction of ray
	 * @return the color's specular component
	 */
	private Double3 calcSpecular(Material material, Vector normal, Vector lv, double nl, Vector vector) {
		Vector reflectedVector = lv.subtract(normal.scale(2 * nl));
		double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
		return material.kS.scale(Math.pow(max, material.nShininess));

	}

	/**
	 * The function calculates diffusive component of the color
	 * 
	 * @param material - material of geometry, used for its kD field - its diffuse
	 *                 component
	 * @param nl       - dot product of normal and light vector
	 * @return the color's diffusive component
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

	/**
	 * The function calculates the ray reflection of these rays will move the
	 * starting point of the ray on the regular geometry line towards the new ray.
	 * 
	 * @param geoPoint - the intersected point on the geometry
	 * @param normal   - the normal of the geometry
	 * @param vector   - the ray to be reflected from light source
	 * @return the reflected ray
	 */
	private Ray constructReflectionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
		Vector reflectedVector = vector.subtract(normal.scale(2 * vector.dotProduct(normal))).normalize();
		return new Ray(geoPoint.point, reflectedVector, normal);
	}

	/**
	 * The function calculates the ray transparency of these rays will move the
	 * starting point of the ray on the regular geometry line towards the new ray.
	 * 
	 * @param geoPoint - the intersected point on the geometry
	 * @param normal   - the normal of the geometry
	 * @param vector   - the ray to be transparency from light source
	 * @return the refracted ray
	 */
	private Ray constructRefractionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
		return new Ray(geoPoint.point, vector, normal);
	}

	/**
	 * Find the closest intersection point with a ray.
	 *
	 * @param ray - The ray to checks intersections with.
	 * @return The closest intersection point of the ray and geometry.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// find intersection point
		if (intersections == null) // if there are no intersection points return color of background
			return null;
		return ray.findClosestGeoPoint(intersections);
	}

	/**
	 * The function calculates the color of point on a geometry as it seems from the
	 * camera position, with consideration of the ambient-light and the transparency
	 * of the object. The final color is combination of the material color and the
	 * attributes of the object, the ambient-light color, the distance from the
	 * camera and sometimes other object in the scene.
	 * 
	 * @param gp  - a point on a geometry with its geometry
	 * @param ray - the ray that constructed from the camera and intersected the
	 *            geometry
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
	}

	/**
	 * The function calculates the color of point on a geometry as it sees from the
	 * camera position (helping method for calcColor(GeoPoint, Ray)).
	 * 
	 * @param gp    - a point on a geometry
	 * @param ray   - the ray that constructed from the camera and intersected the
	 *              geometry
	 * @param level - of recursion for the transparency and global effects
	 *              calculation
	 * @param k     - initial attenuation coefficient value (between 0-1)
	 * @return the color of the point on the geometry
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * boolean function to check if point is unshaded
	 * 
	 * @param gp          - geometry point to check
	 * @param l           - light vector
	 * @param n           - normal vector
	 * @param lightSource
	 * @param nv
	 * @return true if unshaded
	 */
	/*
	 * unused unshaded
	 * 
	 * private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource
	 * lightSource) { Vector lightDirection = l.scale(-1); // from point to light
	 * source Ray lightRay = new Ray(gp.point, lightDirection, n); List<GeoPoint>
	 * intersections = scene.geometries.findGeoIntersections(lightRay); if
	 * (intersections != null) { double distance =
	 * lightSource.getDistance(gp.point); for (GeoPoint intersection :
	 * intersections) { if (intersection.point.distance(gp.point) < distance &&
	 * gp.geometry.getMaterial().kT.equals(Double3.ZERO)) return false; } }
	 * 
	 * return true; }
	 */

	/**
	 * The function checks transparency shading between a point and the light
	 * source. For each intersection which is closer to the point than the light
	 * source multiply ktr by Material.kT of its geometry. The returned value is the
	 * transparency value between 1 (no Shaded at all) and 0 (full shaded).
	 * 
	 * @param gp - a point on a geometry
	 * @param ls - is the checked light source
	 * @param l  - is the vector of the light direction
	 * @param n  - the normal of the geometry
	 * @return the transparency value of the point
	 */
	private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1);
		double lightDistance = ls.getDistance(geoPoint.point);
		var intersections = scene.geometries.findGeoIntersections(new Ray(geoPoint.point, lightDirection, n));
		if (intersections == null)
			return Double3.ONE;

		var ktr = Double3.ONE;
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
				ktr = ktr.product(gp.geometry.getMaterial().kT);
				if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
					return Double3.ZERO;
				}
			}
		}
		return ktr;
	}

}

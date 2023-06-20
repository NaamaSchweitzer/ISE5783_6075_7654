/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * @author Naama, Hadas
 *
 */
public abstract class RayTracerBase {

	protected final Scene scene;

	/**
	 * RayTracerBase constructor
	 * 
	 * @param scene - the scene to find the intersections of the ray with the scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;

	}

	/**
	 * Abstract trace ray function
	 * 
	 * @param ray - the ray to trace
	 * @return the color of the intersection
	 */
	abstract Color traceRay(Ray ray);
	
	/**
     * function that traces rays and return average color of pixel
     * @param rays rays to trace
     */
    abstract Color traceRays(List<Ray> rays);

}

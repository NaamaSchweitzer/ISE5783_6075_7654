/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
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
	 * 
	 * @param rays rays to trace
	 */
	abstract Color traceRays(List<Ray> rays);

	/**
	 * Checks the color of the pixel with the help of individual rays and averages
	 * between them and only if necessary continues to send beams of rays in
	 * recursion
	 * 
	 * @param centerP   center pixl
	 * @param Width     Length
	 * @param Height    width
	 * @param minWidth  min Width
	 * @param minHeight min Height
	 * @param cameraLoc Camera location
	 * @param Vright    Vector right
	 * @param Vup       vector up
	 * @param prePoints pre Points
	 * @return Pixel color
	 */
	public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth,
			double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);

}

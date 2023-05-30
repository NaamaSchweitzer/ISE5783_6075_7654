/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author hadas and naama.
 *
 */
/**
 * LightSource interface served all the light sources like sun, spot-light ect
 * Light source effects the color of the objects in the scene The influence of
 * eny light source is changing according to the distance of the object from the
 * light source and the intensity and direction of the light source
 */
public interface LightSource {

	/**
	 * Get light intensity at a {@link Point} in the scene
	 * 
	 * @param p the observed point
	 * @return the color of the point
	 */
	public Color getIntensity(Point p);

	/**
	 * Get the light direction {@link Vector} from the light-source to a
	 * {@link Point} in the scene
	 * 
	 * @param p the observed point in the scene
	 * @return the light direction vector
	 */
	public Vector getL(Point p);

	/**
	 * return the distance between the light source and a {@link Point} in the scene
	 * 
	 * @param p the observed point
	 * @return the distance between the light source and the point
	 */
	double getDistance(Point p);
}

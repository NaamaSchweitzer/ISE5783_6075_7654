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
public interface LightSource {

	public Color getIntensity(Point p);

	public Vector getL(Point p);

}

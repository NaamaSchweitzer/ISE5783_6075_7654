/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author user1
 *
 */
public class DirectionalLight extends Light implements LightSource {

	private final Vector direction;

	/**
	 * DirectionalLight constructor initialize direction direction of the light
	 * source and the intensity
	 * 
	 * @param color of the intensity
	 */
	public DirectionalLight(Color intensity, Vector dir) {
		super(intensity);
		this.direction = dir.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity();
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point p) {
		return Double.POSITIVE_INFINITY;
	}

}

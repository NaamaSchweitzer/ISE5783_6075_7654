/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class is a light source (like lamp for example) The position and
 * intensity of the light source are changing the effect of the light in the
 * scene The class extends {@link Light} and implements {@link LightSource}
 * interface
 */
public class PointLight extends Light implements LightSource {

	// #region fields
	/**
	 * the position of the light source in the scene
	 */
	private final Point position;

	// ****************attenuation factors****************
	/**
	 * linear func value of attenuation
	 */
	private double kC = 1d;

	/**
	 * quadrant func value of attenuation
	 */
	private double kL = 0d;

	/**
	 * constant func value of attenuation
	 */
	private double kQ = 0d;
	// #endregion

	/**
	 * Constructor of PointLight initialize the intensity color and the position of
	 * the light source in the scene.
	 * 
	 * @param intensity of the light source
	 * @param position  of the light source in the scene
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * @param kC the kC to set
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * @param kL the kL to set
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double distanceSquared=p.distanceSquared(position);//d^2
		double distance=p.distance(position);//d
		return getIntensity().scale(1 / (kC + kL * distance + kQ * distanceSquared));
	}

	@Override
	public Vector getL(Point p) {
		if (p.equals(position)) {//check if p==position ,because vector zero is bad
			return null;
		}
		return p.subtract(position).normalize();//return the normalized vector of (p-position)
	}

}

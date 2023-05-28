/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The class defined a spot-light The class extends {@link PointLight} class,
 * with addition of the direction of the light
 */
public class SpotLight extends PointLight {

	private final Vector direction;

	/**
	 * Constructor of a spot light object
	 *
	 * @param intensity, the color of the light source
	 * @param position   of the light source
	 * @param direction  of the light
	 */
	public SpotLight(Color intensity, Point position, Vector dir) {
		super(intensity, position);
		this.direction = dir.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		double l = direction.dotProduct(getL(p));// dir*L
		// max(0,dir*L)

		if (l <= 0) // if(l<0) l=0;
			return Color.BLACK; // no color for t=zero (intensity*0)/(kC+kL*distance+kQ*distanceSquared)=0
		return super.getIntensity(p).scale(l); // return (intensity*dir*l)/(kC+kL*distance+kQ*distanceSquared)
	}

}

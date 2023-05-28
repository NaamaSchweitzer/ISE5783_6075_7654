/**
 * 
 */
package lighting;

import primitives.Color;

/**
 * @author user1
 *
 */
abstract class Light {

	private final Color intensity;

	/**
	 * @param intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}

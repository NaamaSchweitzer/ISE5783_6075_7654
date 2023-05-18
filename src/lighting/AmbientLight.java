package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * 
 * @author Hadas & Naama
 *
 */
public class AmbientLight {
	final private Color intensity; // the final intensity of the light

	// a constant field the default intensity is black (no light intensity):
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructor (double3) that recives 2 parameters and calculate light intensity
	 * 
	 * @param iA - the original color of the light (the intensity of the light
	 *           according to RGB)
	 * @param kA - the attenuation factor of the original light
	 */
	public AmbientLight(Color iA, Double3 kA) {
		super();
		intensity = iA.scale(kA);// iP = kA*iA
	}

	/**
	 * Constructor (double) that recives 2 parameters and calculate light intensity
	 * 
	 * @param iA - the original color of the light (the intensity of the light
	 *           according to RGB)
	 * @param kA - the attenuation factor of the original light
	 */
	public AmbientLight(Color iA, double kA) {
		super();
		intensity = iA.scale(kA);// iP = kA*iA
	}

	/**
	 * constructor that recives 1 parameter (for light intensity) and set it to
	 * field
	 * 
	 * @param inten - set the final light intensity
	 */
	public AmbientLight(Color inten) {
		super();
		this.intensity = inten;
	}

	/**
	 * return light intensity
	 * 
	 * @return Color
	 */
	public Color getIntensity() {
		return intensity;
	}

}

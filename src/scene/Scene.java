/**
 * 
 */
package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

/**
 * @author Hadas &amp; Naama
 *
 */
public class Scene {

	// scene is PDS so the fields would be public:
	public final String name; // The name of the scene
	public Color background = Color.BLACK; // The background color of the scene (initialized to black)
	public AmbientLight ambientLight = AmbientLight.NONE; // The ambient light for the scene (initialized to NONE)
	public Geometries geometries = new Geometries(); // The 3D model (initialized to an empty model)
	public List<LightSource> lights = new LinkedList<>();

	/**
	 * constructor that receives 1 parameter and initialize scene
	 * 
	 * @param name - scene name
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * Setters for the fields (except name). The setters are form to use in the
	 * Builder Design Pattern – each method assign the attribute its respective
	 * value and returns the scene object itself (this)
	 */

	/**
	 * @param background - the background to set
	 * @return scene (this)
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * @param ambientLight - the ambientLight to set
	 * @return scene (this)
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * @param geometries - the geometries to set
	 * @return scene (this)
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * @param lights the lights to set
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}
}

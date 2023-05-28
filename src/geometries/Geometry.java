package geometries;

import primitives.Vector;
import primitives.Color;
import primitives.Material;
import primitives.Point;

/**
 * The Geometry interface is an abstract interface. It represents a geometric
 * object in three-dimensional space. It defines a method for getting the normal
 * vector at a given point on the geometry.
 */
public abstract class Geometry extends Intersectable {

	protected Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * Calculates and returns the normal vector at a given point on the geometry.
	 * 
	 * @param p The point on the geometry to calculate the normal vector at
	 * @return The normal vector at the given point on the geometry
	 */
	public abstract Vector getNormal(Point p);

	/**
	 * @return the emission
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * @param emission the emission to set
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

}

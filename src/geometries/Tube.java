package geometries;

import primitives.Ray;

public class Tube extends RadialGeometry {
	
	Ray axisRay;
	
	/**
	 * @param radius
	 * @param axisRay
	 */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	public Ray getAxisRay() {
		return axisRay;
	}

}

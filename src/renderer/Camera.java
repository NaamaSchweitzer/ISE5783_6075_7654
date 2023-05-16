package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

public class Camera {

	// private fields

	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;

	/**
	 * 
	 * constructor -The constructor gets 2 vectors and point, checks that the
	 * vectors are vertical and initializes them and their normal vector-vRight
	 * 
	 * @param p0
	 * @param vTo
	 * @param vUp
	 */
	/*
	 * public Camera(Point p0, Vector vTo, Vector vUp) { if
	 * (!isZero(vTo.dotProduct(vUp))) throw new
	 * IllegalArgumentException("Error, cannot create Camera, vUp and vTo are not vertical"
	 * ); this.p0 = p0; this.vTo = vTo.normalize(); this.vUp = vUp.normalize();
	 * this.vRight = (vTo.crossProduct(vUp)).normalize();
	 * 
	 * }
	 */

	public Camera(Point p0, Vector vTo, Vector vUp) {

		if (!isZero(vUp.dotProduct(vTo))) {
			throw (new IllegalArgumentException("ERROR: The vectors must be vertical"));
		}
		this.vRight = vTo.crossProduct(vUp).normalize();
		this.p0 = p0;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
	}

	// setters for fields

	/**
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	// setters

	/**
	 * this function initializes view plane
	 * 
	 * @param w - width
	 * @param h - height
	 * @return Camera
	 */
	public Camera setVPSize(double w, double h) {
		this.width = w;
		this.height = h;
		return this;
	}

	/**
	 * this function initializes Camera distance from view plane
	 * 
	 * @param d - distance
	 * @return Camera
	 */
	public Camera setVPDistance(double d) {
		this.distance = d;
		return this;
	}

	/**
	 * This function return a ray from the camera to the point (x,y) on the view
	 * plane
	 * 
	 * @param nX - column height
	 * @param nY - row width
	 * @param j  - column index
	 * @param i  - row index
	 * @return Ray
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		// Image center
		if (isZero(distance)) {
			throw new IllegalArgumentException("distance can not be 0");
		}
		Point pc = this.p0.add(this.vTo.scale((this.distance))); // p0 + d*vto

		// Ratio (pixel width & height)
		if (isZero(nY)) {
			throw new IllegalArgumentException("can not divide in 0");
		}
		if (isZero(nX)) {
			throw new IllegalArgumentException("can not divide in 0");
		}
		double Ry = this.height / nY;
		double Rx = this.width / nX;

		// Pixel[i,j] center
		double yi = -(i - (nY - 1) / 2d) * Ry;
		double xj = (j - (nX - 1) / 2d) * Rx;

		Point Pij = pc;

		if (!isZero(xj)) {
			Pij = Pij.add(vRight.scale(xj));
		}
		if (!isZero(yi)) {
			Pij = Pij.add(vUp.scale(yi));
		}

		Vector Vij = Pij.subtract(this.p0);

		return new Ray(this.p0, Vij);
	}

}

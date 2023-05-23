package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.MissingResourceException;

public class Camera {

	// private fields

	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;

	/**
	 * 
	 * constructor -The constructor gets 2 vectors and point, checks that the
	 * vectors are vertical and initializes them and their normal vector-vRight
	 * 
	 * @param p0
	 * @param vTo
	 * @param vUp
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {

		if (!isZero(vUp.dotProduct(vTo))) {
			throw (new IllegalArgumentException("ERROR: The vectors must be vertical"));
		}
		this.p0 = p0;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
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
	 * Image writer setter and return the camera
	 * 
	 * @param imageWriter - the image writer
	 * @return the camera after set the image writer
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * Ray tracer setter and return the camera
	 * 
	 * @param rayTracerBase - the ray tracer
	 * @return the camera after set the ray tracer
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
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
		Point pc = this.p0.add(vTo.scale((distance))); // p0 + d*vto

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
		double yi = -1 * (i - ((nY - 1) / 2d)) * Ry;
		double xj = (j - ((nX - 1) / 2d)) * Rx;

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

	/**
	 * This function checks if all the parameters are valid for the camera
	 */
	public void renderImage() {
		if (p0 == null)
			throw new MissingResourceException("ERROR: The camera position is null", "Camera", "p0");
		if (vUp == null)
			throw new MissingResourceException("ERROR: The camera up direction is null", "Camera", "vUp");
		if (vTo == null)
			throw new MissingResourceException("ERROR: The camera towards direction is null", "Camera", "vTo");
		if (vRight == null)
			throw new MissingResourceException("ERROR: The camera right direction is null", "Camera", "vRight");
		if (width == 0)
			throw new MissingResourceException("ERROR: The width of the image is zero", "Camera", "width");
		if (height == 0)
			throw new MissingResourceException("ERROR: The height of the image is zero", "Camera", "height");
		if (distance == 0)
			throw new MissingResourceException("ERROR: The distance from the camera to the image plane is zero",
					"Camera", "distance");
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");
		if (rayTracerBase == null)
			throw new MissingResourceException("ERROR: The ray tracer base is null", "Camera", "rayTracerBase");

		int ny = imageWriter.getNy();
		int nx = imageWriter.getNx();
		for (int i = 0; i < ny; i++)
			for (int j = 0; j < nx; j++) {
				Color color = this.castRay(nx, ny, j, i);
				imageWriter.writePixel(j, i, color);
			}
		;
	}

	/**
	 * Create grid of lines to draw the view plane
	 * 
	 * @param interval - the interval between the lines
	 * @param color    - the color of the lines
	 * @throws MissingResourceException - if the image writer is null
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");

		int ny = imageWriter.getNy();
		int nx = imageWriter.getNx();
		for (int i = 0; i < ny; i++)
			for (int j = 0; j < nx; j++)
				if (i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(j, i, color);

	}

	/**
	 * Activates the appropriate image maker's method
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");

		imageWriter.writeToImage();
	}

	/**
	 * Cast ray from camera in order to color a pixel
	 * 
	 * @param nX  resolution on X axis (number of pixels in row)
	 * @param nY  resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 */
	private Color castRay(int nX, int nY, int col, int row) {
		Ray ray = this.constructRay(nX, nY, col, row);
		Color color = this.rayTracerBase.traceRay(ray);

		return color;
	}

}

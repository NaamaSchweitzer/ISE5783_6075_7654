package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;
import static primitives.Util.alignZero;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Camera {

	// private fields

	private Point p0; // Camera location
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;

	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;

	// --------- field Anti Aliasing -------
	private int antiAliasingFactor = 1;

	/**
	 * 
	 * constructor - The constructor gets 2 vectors and point, checks that the
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
	 * function that sets the antiAliasingFactor
	 *
	 * @param antiAliasingFactor value to set
	 * @return camera itself
	 */
	public Camera setAntiAliasingFactor(int antiAliasingFactor) {
		this.antiAliasingFactor = antiAliasingFactor;
		return this;
	}


	/**
	 * function that calculates the pixels location
	 *
	 * @param nX the x resolution
	 * @param nY the y resolution
	 * @param i  the x coordinate
	 * @param j  the y coordinate
	 * @return the ray
	 */
	private Point findPixelLocation(int nX, int nY, int j, int i) {

		double rY = height / nY;
		double rX = width / nX;

		double yI = -(i - (nY - 1d) / 2) * rY;
		double jX = (j - (nX - 1d) / 2) * rX;
		Point pIJ = p0.add(vTo.scale(distance));

		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));
		if (jX != 0)
			pIJ = pIJ.add(vRight.scale(jX));
		return pIJ;
	}

	/**
	 * This function that returns the ray from the camera to the point
	 *
	 * @param nX the x resolution
	 * @param nY the y resolution
	 * @param i  the x coordinate
	 * @param j  the y coordinate
	 * @return the ray
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		return new Ray(p0, findPixelLocation(nX, nY, j, i).subtract(p0));
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
	public List<Ray> constructRays(int nX, int nY, int j, int i) {
		  List<Ray> rays = new LinkedList<>();
	        Point centralPixel = findPixelLocation(nX, nY, j, i);
	        double rY = height / nY / antiAliasingFactor;
	        double rX = width / nX / antiAliasingFactor;
	        double x, y;

	        for (int rowNumber = 0; rowNumber < antiAliasingFactor; rowNumber++) {
	            for (int colNumber = 0; colNumber < antiAliasingFactor; colNumber++) {
	                y = -(rowNumber - (antiAliasingFactor - 1d) / 2) * rY;
	                x = (colNumber - (antiAliasingFactor - 1d) / 2) * rX;
	                Point pIJ = centralPixel;
	                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
	                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
	                rays.add(new Ray(p0, pIJ.subtract(p0)));
	            }
	        }
	        return rays;
	}

	/**
	 * This function checks if all the parameters are valid for the camera
	 */
	public Camera renderImage() {
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
		for (int j = 0; j < nx; j++)
			for (int i = 0; i < ny; i++) {
				Color color = this.castRay(nx, ny, j, i);
				imageWriter.writePixel(j, i, color);
			}
		return this;
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
		for (int j = 0; j < nx; j++)
			for (int i = 0; i < ny; i++)
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
	 * @param nX  - resolution on X axis (number of pixels in row)
	 * @param nY  - resolution on Y axis (number of pixels in column)
	 * @param col - pixel's column number (pixel index in row)
	 * @param row - pixel's row number (pixel index in column)
	 */
	private Color castRay(int nX, int nY, int col, int row) {
		if (antiAliasingFactor == 1) // no improvement
			return rayTracerBase.traceRay(constructRay(nX, nY, col, row));
		else
			return rayTracerBase.traceRays(constructRays(nX, nY, col, row));
	}

}

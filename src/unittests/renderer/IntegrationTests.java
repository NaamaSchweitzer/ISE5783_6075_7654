/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

/**
 * @author Hadas Carmen & Naama Schweitzer
 *
 */
class IntegrationTests {
	static final Point ZERO_POINT = new Point(0, 0, 0);

	public void cameraIntegrations(Geometry geo, Camera camera, int expected, String testCase) {
		List<Point> intersectionPoints;
		int intersections = 0;
		// run on view plane - 3X3 size in pixels
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				intersectionPoints = geo.findIntersections(camera.constructRay(3, 3, j, i));
				if (intersectionPoints != null)
					intersections += intersectionPoints.size();
			}
		}
		assertEquals("ERROR " + testCase + ": Wrong amount of intersections", intersections, expected);
	}

	/**
	 * integration tests for constructing a ray through a pixel with a sphere
	 * {@link elements.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void SphereIntegration() {

		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
				3);
		Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1)
				.setVPSize(3, 3);

		// TC01: 2 intersection points
		cameraIntegrations(new Sphere(1, new Point(0, 0, -3)), camera, 2, "TC01");

		// TC02: 18 intersection points
		cameraIntegrations(new Sphere(2.5, new Point(0, 0, -2.5)), camera2, 18, "TC02");

		// TC03: 10 intersection points
		cameraIntegrations(new Sphere(2, new Point(0, 0, -2)), camera2, 10, "TC03");

		// TC04: 9 intersection points
		cameraIntegrations(new Sphere(4, new Point(0, 0, -1)), camera2, 9, "TC04");

		// TC05: 0 intersection points
		cameraIntegrations(new Sphere(0.5, new Point(0, 0, 1)), camera, 0, "TC05");

	}

	private void assertEquals(String string, int i, int intersections) {
	}

	/**
	 * integration tests for constructing a ray through a pixel with a plane
	 * {@link elements.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void PlaneIntegration() {
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, 1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
				3);
		// Tc01: 9 intersection points- plane against camera
		cameraIntegrations(new Plane(new Point(0, 0, 5), new Vector(0, 0, 1)), camera, 9, "TC01");

		// TC02: 9 intersection points- plane with small angle
		cameraIntegrations(new Plane(new Point(0, 0, 5), new Vector(0, -1, 2)), camera, 9, "TC02");

		// TC03: 6 intersection points- plane parallel to lower rays
		cameraIntegrations(new Plane(new Point(0, 0, 5), new Vector(0, -1, 1)), camera, 6, "TC03");

	}

	/**
	 * integration tests for constructing a ray through a pixel with a triangle
	 * {@link elements.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	public void TriangleIntegration() {
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
				3);
		// TC01: 1 intersection point- small triangle
		cameraIntegrations(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera, 1,
				"TC01");

		// TC02: 2 intersection points - medium triangle
		cameraIntegrations(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera, 2,
				"TC02");

	}
}
/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Planes
 * 
 * @author Hadas Carmen &amp; Naama Schweitzer
 *
 */
class PlaneTests {

	/** Test method for {@link geometries.Plane#Plane(primitives.Point...)}. */
	@Test
	void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave square with vertices in correct order
		try {
			new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct plane");
		}

		// =============== Boundary Values Tests ==================

		// TC02: First &amp; second points are merged
		assertThrows(IllegalArgumentException.class, //
				() -> {
					new Plane(new Point(2, 1, 0), new Point(2, 1, 0), new Point(7, 1, 3));
				}, "ERROR: first &amp; second points are merged");

		// TC03: first &amp; second points are on the same line
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(2, 1, 0), new Point(5, 0, 3), new Point(3.5, 0.5, 1.5)),
				"ERROR: first &amp; second points are on the same line");

	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here

		Plane pln = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		// ensure there are no exceptions
		assertDoesNotThrow(() -> pln.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pln.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pln.getNormal(new Point(0, 0, 1)), "wrong normal to trinagle");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 0, 1));

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects the plane
		Ray ray = new Ray(new Point(-1, 0, 0), new Vector(2, 0, 0));
		assertEquals(plane.findIntersections(ray).get(0), new Point(1, 0, 0),
				"TC01: ERROR: Ray intersects the plane, there has to be no intersections points");

		// TC02: Ray not intersects the plane
		ray = new Ray(new Point(2, 0, 0), new Vector(3, 0, 0));
		assertNull(plane.findIntersections(ray), "TC02: ERROR: There should be no intersections points");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray is parallel to the plane
		// TC11: Ray is included in the plane
		ray = new Ray(new Point(1, 0, 0), new Vector(1, 2, 0));
		assertNull(plane.findIntersections(ray),
				"TC11: ERROR: Ray included in the plane, there has to be no intersections");

		// TC12: Ray is not included in the plane
		ray = new Ray(new Point(2, 0, 0), new Vector(2, 4, 0));
		assertNull(plane.findIntersections(ray),
				"TC12: ERROR: Ray parallel to the plane, there shouldn't be intersections");

		// **** Group: Ray is orthogonal to the plane
		// TC13: Ray before the plane
		ray = new Ray(new Point(-4, 0, 0), new Vector(2, 0, 0));
		assertEquals(plane.findIntersections(ray).get(0), new Point(1, 0, 0),
				"TC13: ERROR: Ray is orthogonal before, there should be one intersection point");

		// TC14: Ray in the plane
		ray = new Ray(new Point(1, 0, 0), new Vector(2, 0, 0));
		assertNull(plane.findIntersections(ray), "TC14: ERROR: Ray is orthogonal in the plane, there should be null");

		// TC15: Ray after the plane
		ray = new Ray(new Point(2, 0, 0), new Vector(6, 0, 0));
		assertNull(plane.findIntersections(ray), "TC15: ERROR: Ray is orthogonal after, there should be null");

		// TC16: Ray is neither orthogonal nor parallel to and begins at the plane
		ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 2));
		assertNull(plane.findIntersections(ray),
				"TC16: ERROR: Ray is neither orthogonal nor parallel to and begins at the plane, there should be null");

		// TC17: Ray is neither orthogonal nor parallel to the plane and begins in
		// the same point which appears as reference point in the plane
		ray = new Ray(new Point(1, 0, 0), new Vector(0, 1, 1));
		assertNull(plane.findIntersections(ray),
				"TC17: ERROR: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane, there should be null");
	}
}

/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import primitives.Point;
import primitives.Vector;

/**
 * Testing Planes
 * 
 * @author Naama
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

		// TC02: First & second points are merged
		assertThrows(IllegalArgumentException.class, //
				() -> {new Plane(new Point(2, 1, 0), new Point(2, 1, 0), new Point(7, 1, 3));},
				"ERROR: first & second points are merged");

		// TC03: first & second points are on the same line
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(2, 1, 0), new Point(5, 0, 3), new Point(3.5, 0.5, 1.5)),
				"ERROR: first & second points are on the same line");

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

}

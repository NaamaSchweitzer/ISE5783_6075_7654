/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * @author Naama
 *
 */
class SphereTests {

	/**
	 * Test method for
	 * {@link geometries.RadialGeometry#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here

		Sphere sphere = new Sphere(1, new Point(0, 0, 0));
		// ensure there are no exceptions
		assertDoesNotThrow(() -> sphere.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = sphere.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
	}

}

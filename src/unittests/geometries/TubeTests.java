/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import geometries.Tube;
import primitives.Vector;

/**
 * @author Naama
 *
 */
class TubeTests {

	/**
	 * Test method for
	 * {@link geometries.RadialGeometry#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Tube tube = new Tube(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
		assertEquals(tube.getNormal(new Point(0, 2, 5)), new Vector(0, 1, 0), "ERROR: The normal is not correct");

		// ========== Boundary Values Tests ==================
		// TC11: A right angle with the ray source point
		tube = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
		assertEquals(tube.getNormal(new Point(1, 0, 0)), new Vector(1, 0, 0),
				"ERROR: A right angle with the ray source point");
	}

}

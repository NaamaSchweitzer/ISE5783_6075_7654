/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Hadas Carmen &amp; Naama Schweitzer
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

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
				"Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		assertEquals(sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
				List.of(new Point(2, 0, 0)), "Ray starts inside the sphere");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
				"Ray starts after the sphere");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		assertEquals(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0.5, 0.5))).size(), 1,
				"Ray starts at sphere and goes inside");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(4, 4, 4))),
				"Ray starts at sphere and goes outside");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		p1 = new Point(0, 0, 0);
		p2 = new Point(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point(-3, 0, 0), new Vector(3, 0, 0)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray starts before the sphere");
		
		
		// TC14: Ray starts at sphere and goes inside (1 points)
		assertEquals(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-0.5, 0, 0))),
				List.of(new Point(0, 0, 0)), "Ray starts at sphere and goes inside");
		
		// TC15: Ray starts inside (1 points)
		assertEquals(sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(-0.5, 0, 0))),
				List.of(new Point(0, 0, 0)), "Ray starts inside");
		
		// TC16: Ray starts at the center (1 points)
		assertEquals(sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-0.5, 0, 0))),
				List.of(new Point(0, 0, 0)), "Ray starts at the center");
		
		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(3, 0, 0))),
				"Ray starts at sphere and goes outside");
		
		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(4, 0, 0))),
				"Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(1, 0, 0))),
                        "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 0, 0))),
                        "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0), new Vector(1, 0, 0))),
                        "Ray starts after the tangent point");
		
        // **** Group: Special cases
		// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0))),
                "TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
	}
}

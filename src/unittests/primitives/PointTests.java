/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * 
 * @author Hadas
 * @author Naama
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of add on point and vector
	
		Point p1 = new Point(1, 2, 3);
	    Vector v = new Vector(-2, -4, -6);
	    Point p2 = new Point(-1, -2, -3);
		assertEquals(p1.add(v), p2, "ERROR: Point add does not work correctly");	
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of subtract		
		Point p1 = new Point(1, 2, 3);
	    Point p2 = new Point(-2, -4, -6); 
	    Vector v = new Vector(3, 6, 9);
		assertEquals(p1.subtract(p2), v, "ERROR: Point subtract does not work correctly");
		
        // ============ Boundary Values Tests ==============
        // TC02: A case of subtract with a point on the same point
        assertThrows(IllegalArgumentException.class, 
        		() -> { p1.subtract(p1);}, 
        		"ERROR: Point subtract does not throw exception correctly");
	}
	
	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of Distance
	    Point p1 = new Point(1, 2, 3);
	    Point p2 = new Point(1, 1, 1);
        assertEquals(p1.distance(p2), Math.sqrt(5), 0.0001, "ERROR: distance result is incorrect");
		
		// ============ Boundary Values Tests ==============
        // TC02: A case of a Distance with a point on the same point
        assertEquals(p1.distance(p1), 0, 0.0001, "ERROR: distance result is incorrect");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of DistanceSquared
	    Point p1 = new Point(1, 2, 3);
	    Point p2 = new Point(1, 1, 1);
        assertEquals(p1.distanceSquared(p2), 5, 0.0001, "ERROR: DistanceSquared result is incorrect");
		
		// ============ Boundary Values Tests ==============
        // TC02: A case of a DistanceSquared with a point on the same point
        assertEquals(p1.distanceSquared(p1), 0, 0.0001, "ERROR: DistanceSquared result is incorrect");
	}

}

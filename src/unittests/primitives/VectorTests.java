/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import static primitives.Util.isZero;
import primitives.Vector;

/**
 * @author Hadas
 * @author Naama
 *
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple case of add
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(new Vector(2, 3, 4), v1.add(new Vector(1, 1, 1)), "Error: TC01, add result is incorrect");
		// ============ Boundary Values Tests ==============
		// TC02: A case of add with a vector and minus vector
		Vector v2 = new Vector(-1, -2, -3);
		assertThrows(IllegalArgumentException.class, () -> v1.add(v2),
				"ERROR: Vector + does not throw the right exception");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of scale
		Vector v1 = new Vector(2, 2, 2);
		Vector v2 = new Vector(1, 1, 1);
        assertEquals( v2.scale(2), v1, "Error: scale() wrong result");
        
        // =============== Boundary Values Tests ==================
        // TC11: A simple case of zero scale
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"ERROR: Vector scale() with 0 does not throw the right exception");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);
		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

		// TC02: Test cross-product result orthogonality to its operands
		assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
		assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-product of co-lined vectors
		Vector v3 = new Vector(-2, -4, -6);
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
				"crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		 // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of lengthSquared
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(v1.lengthSquared(), 14, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of length
		Vector v1 = new Vector(0, 3, 4);
		assertEquals(v1.length(), 5, "ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v = new Vector(0, 3, 4);
		Vector n = v.normalize();
		// ============ Equivalence Partitions Tests ==============
		// TC01: Simple test
		assertEquals(1d, n.lengthSquared(), 0.00001, "ERROR: the normalized vector is not a unit vector");
		assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),
				"ERROR: the normalized vector is not parallel to the original one");
		assertEquals(new Vector(0, 0.6, 0.8), n, "ERROR: Wrong normalized vector");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		// TC01: Test that dot-product is proper (orthogonal vectors taken for
		// simplicity)
		assertEquals(v1.dotProduct(v2), -28, 0.0001, "ERROR: dotProduct() wrong value");
		// TC02: Test dot-product result orthogonality to its operands
		assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");

		// =============== Boundary Values Tests ==================
		// TC11: test dot-product with zero vector
		assertThrows(IllegalArgumentException.class, () -> v1.dotProduct(new Vector(0, 0, 0)),
                "Error: TC11, dotProduct() for parallel vectors does not throw an exception");

	}

}

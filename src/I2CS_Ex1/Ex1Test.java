package I2CS_Ex1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};
    static double[] po5 = {0, 0.61, -3, 2};
    static double[] po6 = {0, 0.61, -3, 2, 0};


    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testF() {
        double fx0 = Ex1.f(po1, 0);
        double fx1 = Ex1.f(po1, 1);
        double fx2 = Ex1.f(po1, 2);
        double fx3 = Ex1.f(po6, 0);
        assertEquals(2, fx0, Ex1.EPS);
        assertEquals(4, fx1, Ex1.EPS);
        assertEquals(6, fx2, Ex1.EPS);
        assertEquals(0, fx3, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1(x) + p2(x) == (p1+p2)(x)
     */
    void testF2() {
        double x = Math.PI;
        double[] po12 = Ex1.add(po1, po2);
        double f1x = Ex1.f(po1, x);
        double f2x = Ex1.f(po2, x);
        double f12x = Ex1.f(po12, x);
        assertEquals(f1x + f2x, f12x, Ex1.EPS);
    }

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testRootRec() {
        double fx0 = Ex1.root_rec(po1, -3, 0, Ex1.EPS);
        double fx1 = Ex1.root_rec(po1, 0, 2, Ex1.EPS);
        assertEquals(-1, fx0, Ex1.EPS);
        assertEquals(Double.NaN, fx1, Ex1.EPS);
    }

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testRootRec2() {
        double fx2 = Ex1.root_rec(po5, -1, 1, Ex1.EPS);
        double fx3 = Ex1.root_rec(po5, -1, 0, Ex1.EPS);
        double fx4 = Ex1.root_rec(po5, 0, 1, Ex1.EPS);
        //double fx5 = Ex1.root_rec(po5, -1, 2, Ex1.EPS);
        assertEquals(0, fx2, Ex1.EPS);
        assertEquals(0, fx3, Ex1.EPS);
        assertEquals(0, fx4, Ex1.EPS);
        //assertEquals(0, fx5, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testPolynomFromPoints() {
        double[] xx = {-5, 5, 10};
        double[] yy = {5, 0, 10};
        double[] p = Ex1.polynomFromPoints(xx, yy);
        double y1 = Ex1.f(p, xx[0]);
        double y2 = Ex1.f(p, xx[1]);
        double y3 = Ex1.f(p, xx[2]);
        assertEquals(5, y1, Ex1.EPS);
        assertEquals(0, y2, Ex1.EPS);
        assertEquals(10, y3, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testPolynomFromPoints2() {
        double[] xx = {10, 14.2};
        double[] yy = {3.78, 5.7};
        double[] p = Ex1.polynomFromPoints(xx, yy);
        double y1 = Ex1.f(p, xx[0]);
        double y2 = Ex1.f(p, xx[1]);
        assertEquals(3.78, y1, Ex1.EPS);
        assertEquals(5.7, y2, Ex1.EPS);
    }

    @Test
    /**
     * Tests the equality of pairs of arrays.
     */
    public void testEquals() {
        double[][] d1 = {{0}, {1}, {1,2,0,0}};
        double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
        double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
        for(int i=0;i<d1.length;i=i+1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for(int i=0;i<d1.length;i=i+1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */
    public void testFromString() {
        double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if(!isSame1) {fail();}
        if(!isSame2) {fail();}
        assertEquals(sp, Ex1.poly(p1));
    }

    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */
    public void testFromString2() {
        double[] p = {0, -1.1, 2.3, 0, 3.1, 0}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^4 +2.3x^2 -1.1x";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if(!isSame1) {fail();}
        if(!isSame2) {fail();}
        assertEquals(sp, Ex1.poly(p1));
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue() {
        double x1=-4, x2=0;
        double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
        assertEquals(rs1,rs2, Ex1.EPS);
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue2() {
        double[] p1 = {0, 1, 0};
        double[] p2 = {0, -1, 0, 1};
        double x1=0, x2=2, x3=1, x4=3;
        double rs1 = Ex1.sameValue(p1,p2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(p2,p1, x3, x4, Ex1.EPS);
        assertEquals(0,rs1, Ex1.EPS);
        assertEquals(1.41406,rs2, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testLength() {
        double[] p1 = {0, 1.33333333, 0};
        double[] p2 = {1};
        double[] p3 = {0, -1, 0, 1};
        double x1=0, x2=2, x3=3, x4=-1, x5=1;
        double rs1 = Ex1.length(p1, x1, x3, 2);
        double rs2 = Ex1.length(p2, x1, x2, 2);
        double rs3 = Ex1.length(p3, x4, x5, 1000);
        assertEquals(5,rs1, Ex1.EPS);
        assertEquals(2,rs2, Ex1.EPS);
        assertEquals(2.6227,rs3, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function - it should be symmetric.
     */
    public void testArea() {
        double x1=-4, x2=0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1,a2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */
    public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0,1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
        double area = 2.5;
        assertEquals(area, a1, Ex1.EPS);
        assertEquals(area, a2, Ex1.EPS);
        assertEquals(area, a3, Ex1.EPS);
        assertEquals(area, a100, Ex1.EPS);
    }
    @Test
    /**
     * Test the area function.
     */
    public void testArea3() {
        double[] po_a = {2,1,-0.7, -0.02,0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
        double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(area, a1, Ex1.EPS);
    }

    @Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex1.mul(po1, po2);
		double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 2x + 6
		double[] dp2 = Ex1.derivative(dp1); // 2
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}
}

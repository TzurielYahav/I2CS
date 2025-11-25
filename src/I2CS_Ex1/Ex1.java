package I2CS_Ex1;

import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1
{
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x)
    {
		double ans = 0;
		for(int i=0;i<poly.length;i++) {
			double c = Math.pow(x, i);
			ans += c*poly[i];
		}
		return ans;
	}
	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps)
    {
        double f1 = f(p,x1);
        double x12 = (x1+x2)/2;
        double f12 = f(p,x12);
        if (Math.abs(f12)<eps) {return x12;}
        if (f12*f1<=0) {return root_rec(p, x1, x12, eps);}
        if (Math.abs(x2-x1)<eps) {return Double.NaN;}
        return root_rec(p, x12, x2, eps);
	}

	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy)
    {
		double [] ans = null;
		int lx = xx.length;
		int ly = yy.length;
		if(xx!=null && yy!=null && lx==ly && lx>1 && lx<4)
        {
            if(lx == 2)
            {
                ans = new double[2];
                ans[1] = (yy[1] - yy[0]) / (xx[1] - xx[0]);
                ans[0] = yy[0] - xx[0] * ans[1];
            }
            else
            {
                ans = new double[3];
                double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]);
                ans[2] = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;
                ans[1] = (Math.pow(xx[2],2) * (yy[0] - yy[1]) + Math.pow(xx[1],2) * (yy[2] - yy[0]) + Math.pow(xx[0],2) * (yy[1] - yy[2])) / denom;
                ans[0] = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
            }
		}
		return ans;
	}
	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
	 */
	public static boolean equals(double[] p1, double[] p2)
    {
		if(p1!=null && p2!=null && p1.length== p2.length)
        {
            // === 1 ===
            /* */
            for (int i=0;i<p1.length;i++)
            {
                if(f(p1, i) != f(p2, i))
                {
                    return false;
                }
            }
            // */
            // === 2 ===
            /* */
            for (int i=0;i<p1.length;i++)
            {
                if(p1[i] != p2[i])
                {
                    return false;
                }
            }
            // */
            // =========
            return true;
        }
		return false;
	}

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly)
    {
		StringBuilder ans = new StringBuilder();
		if(poly.length==0) {
            ans = new StringBuilder("0");}
		else
        {
            for (int i=poly.length-1;i>=0;i--)
            {
                if(poly[i] != 0)
                {
                    if(i < poly.length-1 && poly[i] > 0)
                    {
                        ans.append("+");
                    }
                    ans.append(poly[i]);
                    if(i > 0)
                    {
                        ans.append("x");
                    }
                    if(i > 1)
                    {
                        ans.append("^");
                        ans.append(i);
                    }
                    if (i > 0)
                    {
                        ans.append(" ");
                    }
                }
            }
		}
		return ans.toString();
	}
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps)
    {
		// double ans = x1;

        double f1 = f(p1,x1);
        double f2 = f(p2,x1);
        double x12 = (x1+x2)/2;
        double f12 = f(p1,x12);
        double f22 = f(p2,x12);
        if (Math.abs(f12 - f22) < eps) {return x12;}
        if((f12 - f22) * (f1 - f2) <= 0) {return sameValue(p1, p2, x1, x12, eps);}
        else {return sameValue(p1, p2, x12, x2, eps);}
		// return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments)
    {
		double ans = 0;
        double xSampleLength = x2 - x1 / numberOfSegments;
        double a1 = x1;
        double a2 = 0;
        double b1 = 0;
        double b2 = 0;
        for(int i=0;i<numberOfSegments;i++)
        {
            a2 = a1 + xSampleLength;
            b1 = f(p,a1);
            b2 = f(p,a2);
            ans += distanceOf2Points(a1,a2,b1,b2);
            a1 = a2;
        }
		return ans;
	}

    /**
     * Given two points (x1,y1) , (x2,y2).
     * This function computes the distance between the two points.
     * The area is computed using (https://www.mathsisfun.com/algebra/distance-2-points.html)
     * @param x1 - first point's x value
     * @param x2 - second point's x value
     * @param y1 - first point's y value
     * @param y2 - second point's y value
     * @return the distance between the points.
     */
    private static double distanceOf2Points(double x1,double x2, double y1, double y2)
    {
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid)
    {
        double ans = 0;
        double xSampleLength = x2 - x1 / numberOfTrapezoid;
        double a1 = x1 + xSampleLength / 2;
        double b1 = 0;
        double b2 = 0;
        for(int i=0;i<numberOfTrapezoid;i++)
        {
            b1 = f(p1,a1);
            b2 = f(p2,a1);
            if(b1 > b2)
            {
                ans += b1 - b2;
            }
            else
            {
                ans += b2 - b1;
            }
            a1 += xSampleLength;
        }
        ans *= xSampleLength;
		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
	 */
	public static double[] getPolynomFromString(String p)
    {
        String[] splitString =  p.split(" ");
        double [] ans = ZERO;
        if(splitString.length > 0)                                  //  "-1.0x^2 +3.0x +2.0"
        {
            // ============ Find Poly Power ============
            int indexOfFirstPow = splitString[0].indexOf("^");
            if(indexOfFirstPow > -1)
            {
                ans = new double[Integer.parseInt(splitString[0].split("\\^")[1]) + 1];
            }
            else if (splitString[0].contains("x"))
            {
                ans = new double[2];
            }
            else
            {
                ans = new double[1];
            }
            // ============ Build Poly ============
            for(int i=0;i<splitString.length;i++)
            {
                // ============ Get Pow ============
                int pow = -1;
                if (splitString[i].contains("^"))
                {
                    pow = Integer.parseInt(splitString[0].split("\\^")[1]);
                }
                else if(splitString[i].contains("x"))
                {
                    pow = 1;
                }
                else
                {
                    pow = 0;
                }
                // ============ Get Coefficient ============
                if(pow > 0)
                {
                    String a = splitString[i].split("x")[0];
                    if(a.isEmpty())
                    {
                        ans[pow] = 1;
                    }
                    else
                    {
                        ans[pow] = Double.parseDouble(a);
                    }
                }
                else
                {
                    ans[pow] = Double.parseDouble(splitString[i]);
                }
            }
        }
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans = ZERO;
        double[] shortPoly;
        if (p1.length == 0 || p2.length == 0)
        {
            if (p1.length == 0)
            {
                if (p2.length == 0)
                {
                    return ans;
                }
                return p2;
            }
            return p1;
        }
        if (p1.length >= p2.length)
        {
            ans = p1;
            shortPoly = p2;
        }
        else
        {
            ans = p2;
            shortPoly = p1;
        }
        for (int i = 0; i < shortPoly.length; i++)
        {
            ans[i] += shortPoly[i];
        }
        if (ans[ans.length - 1] == 0)
        {
            int cutLengthCount = 0;
            for (int i=ans.length-1;i>=0;i--)
            {
                if (ans[i] == 0)
                {
                    cutLengthCount++;
                }
                else
                {
                    i = -1;
                }
            }
            if (cutLengthCount != 0)
            {
                if (cutLengthCount == ans.length)
                {
                    ans = ZERO;
                }
                else
                {
                    double[] cutAns = new double[ans.length - cutLengthCount];
                    cutAns = Arrays.copyOfRange(ans, 0, cutAns.length);
                    return  cutAns;
                }
            }
        }
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;
        if (p1.length == 0 || p2.length == 0)
            return ans;
        ans = new double[p1.length + p2.length - 1];
        for (int i = 0; i < p1.length; i++)
        {
            if(p1[i] != 0)
            {
                for (int j = 0; j < p2.length; j++)
                {
                    ans[i+j] +=  p1[i] * p2[j];
                }
            }
        }
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po
	 * @return
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;
        if (po.length < 2)
        {
            return ans;
        }
        ans  = new double[po.length - 1];
        for (int i = 1; i < po.length; i++)
        {
            ans[i-1] = po[i] * i;
        }
		return ans;
	}
}

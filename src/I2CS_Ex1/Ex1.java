package I2CS_Ex1;

import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * <a href="https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true">...</a>
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
     * If the polynom has leading zeros remove them (they're useless).
     * For each element in the polynom add (x^i)*poly[i] to the answer.
	 * @param poly the polynomial function
	 * @param x the x value for the polynomial function
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x)
    {
        if(poly[poly.length - 1] == 0){ poly = cutPolyLeadingZeros(poly);} // If the polynom has leading zeros remove them
        double ans = 0;
        for(int i=0;i<poly.length;i++) {
            double c = Math.pow(x, i);      // Computes the x element in the i position of the polynom as c=x^i
            ans += c*poly[i];               // Multiplies the coefficient of the element with x (c)
        }
		return ans;
	}
	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
     * The function computes the middle of the range [x1,x2],
     * Checks if the middle is the answer,
     * If not it checks if the answer is lower or higher than the middle
     * and continues to check the half range recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps)
    {
        double f1 = f(p,x1);                                // compute the value of p(x1)
        double x12 = (x1+x2)/2;                             // compute the half range of [x1,x2]
        double f12 = f(p,x12);                              // compute the value of p(x2)
        if (Math.abs(f12)<eps) {return x12;}                // if |p(x)| < eps return x
        if (f12*f1<=0) {return root_rec(p, x1, x12, eps);}  // if the answer is in [x1,x12] range
        if (x2-x1<EPS * EPS) {return Double.NaN;}           // if x1 is almost equal to x2 - there is no valid x in the range
        return root_rec(p, x12, x2, eps);                   // else the answer is in [x12,x2] range
	}

	/**
     * This function computes a polynomial representation from a set of 2D points on the polynom.
     * The solution is based on: //	<a href="http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points">...</a>
     * Note: this function only works for a set of points containing up to 3 points, else returns null.
     * The Function builds two types of polynoms from two different inputs:
     * A first degree polynom Ax+B from two points,
     * or a second degree polynom Ax^2+Bx+C from three points
     * the Function computes the coefficients A,B,C from the points and puts it in an array
     * @param xx the x values of the points in order
     * @param yy the y values of the points in order
     * @return an array of doubles representing the coefficients of the polynom.
     */
	public static double[] polynomFromPoints(double[] xx, double[] yy)
    {
		double [] ans = null;
		int lx = xx.length;
		int ly = yy.length;
		if(lx == ly && lx > 1 && lx < 4)  // checks if the input is valid
        {
            if(lx == 2)                                     // if there are only two points
            {
                ans = new double[2];                        // make a new polynom from first degree Ax+B
                ans[1] = (yy[1] - yy[0]) / (xx[1] - xx[0]); // compute the A element
                ans[0] = yy[0] - xx[0] * ans[1];            // compute the B element
            }
            else                                            // if there are three points
            {
                ans = new double[3];                        // make a new polynom from second degree Ax^2+Bx+C
                double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]); // the calculation is in the web page in the description
                ans[2] = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom; // A element
                ans[1] = (Math.pow(xx[2],2) * (yy[0] - yy[1]) + Math.pow(xx[1],2) * (yy[2] - yy[0]) + Math.pow(xx[0],2) * (yy[1] - yy[2])) / denom; // B element
                ans[0] = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom; // C element
            }
		}
		return ans;
	}

    /** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     * The function first checks if the polynoms are from the same degree,
     * and then checks if the difference between the values of the polynoms
     * for 'n' number of points is bigger than EPS, where n is the degree of the polynoms + 1
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
	 */
	public static boolean equals(double[] p1, double[] p2)
    {
        if(p1[p1.length - 1] == 0)                  // If p1 has leading zeros remove them
        {
            p1 = cutPolyLeadingZeros(p1);
        }
        if(p2[p2.length - 1] == 0)                  // If p2 has leading zeros remove them
        {
            p2 = cutPolyLeadingZeros(p2);
        }
		if(p1!=null && p2!=null && p1.length == p2.length)  // check if the input is valid and the polynoms are from the same degree
        {
            for (int i=0;i<p1.length;i++)                   // a loop that checks n points between the polynoms when n is (the degree of the polynoms + 1)
            {
                if(Math.abs(f(p1, i * 1000) - f(p2, i * 1000)) > EPS) // the x value to check is i * 1000, if |p1(x) - p2(x)| > EPS, then the difference between the points is too big
                {
                    return false;
                }
            }
            return true;
        }
		return false;
	}

    /**
     * This function takes a polynom and checks if there are leading zeros in the array of the polynom.
     * If there are it removes them from the array.
     * The function checks if there are any zeros to remove, and if this is not the ZERO array.
     * Then it loops over the array until it reaches a non zero element and counts the amount of zeros to remove.
     * If the whole array is zeros it returns the zero array, else it returns a new array without the zeros.
     * @param p the polynom to check
     * @return the new polynom array without the leading zeros.
     */
    private static double[] cutPolyLeadingZeros(double[] p)
    {
        if (p[p.length - 1] == 0 && p.length > 1)   // If the element in the last place of the array is  a 0, and this is not the ZERO array
        {
            int zerosToRemove = 0;
            for (int i = p.length - 1; i > 0; i--)  // A loop to go over the array from last place to first
            {
                if (p[i] == 0)                      // If the element is a zero add one more to remove
                {
                    zerosToRemove++;
                }
                else                                // else (we reached a non zero element) exit the loop
                {
                    i = -1;
                }
            }
            if (zerosToRemove == p.length)          // if the amount to remove is the whole array return the zero array
                return ZERO;
            return Arrays.copyOfRange(p, 0, p.length - zerosToRemove); // make a new array without the leading zeros
        }
        return p;
    }

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
     * The function goes over the polynom in a loop from last to first and for each element:
     * 1. It checks if it's not a zero.
     * 2. Adds the sign (+,-) and the coefficient of x
     * 3. an x if needed.
     * 4. a '^' and the power if needed.
     * 5. a space if it's not the last element.
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly)
    {
		StringBuilder ans = new StringBuilder();
		if(poly.length==0) {                            // if the array has no elements return an empty string
            ans = new StringBuilder();}
		else
        {
            for (int i=poly.length-1;i>=0;i--)          // A loop to go over the array from end to start
            {
                if(poly[i] != 0)                        // If the current element is not a zero then we add it
                {
                    if(!ans.isEmpty() && poly[i] > 0)   // If the element is a positive number and not the first one add a plus - ("5x +5")
                    {
                        ans.append("+");
                    }
                    ans.append(poly[i]);                // Add the element number to the string
                    if(i > 0)                           // If the element's x is not to the power of 0, add an x to the string
                    {
                        ans.append("x");
                    }
                    if(i > 1)                           // If the element's x power is higher than 1, add an ^ to the string and the power (i)
                    {
                        ans.append("^");
                        ans.append(i);
                    }
                    if (i > 0)                          // If this is not the last element add a space after it
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
     * The function removes leading zeros from the functions,
     * Then calculates the f(x1) for both polynoms,
     * The middle x of the range [x1,x2] and the f(x12) for the middle range for both polynoms.
     * Then it checks if the middle point is the intersection point,
     * If not it checks if the polynoms intersect in the first half of the range and continue in that range.
     * If not it checks if the range [x1,x2] is almost zero, if yes we didn't find any intersection point.
     * Else continue in the second half
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @param x1 minimal value of the range
	 * @param x2 maximal value of the range
	 * @param eps epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps)
    {
        if(p1[p1.length - 1] == 0){ p1 = cutPolyLeadingZeros(p1);}  // Remove leading zeros form the first array
        if(p2[p2.length - 1] == 0){ p2 = cutPolyLeadingZeros(p2);}  // Remove leading zeros form the second array
        double f1 = f(p1,x1);                                       // Calc the f(x1) for the first function
        double f2 = f(p2,x1);                                       // Calc the f(x1) for the second function
        double x12 = (x1+x2)/2;                                     // Calc the x of the middle of the range [x1,x2]
        double f12 = f(p1,x12);                                     // Calc the f(x12) for the first function
        double f22 = f(p2,x12);                                     // Calc the f(x12) for the second function
        if (Math.abs(f12 - f22) < eps) {return x12;}                // If the difference of the functions at the middle x is almost zero return the middle x
        if((f12 - f22) * (f1 - f2) <= 0) {return sameValue(p1, p2, x1, x12, eps);} // If the functions switch between upper and lower in the first half of the range, check the first half
        if (Math.abs(x2-x1)<EPS * EPS) {return Double.NaN;}         // If the range [x1,x2] is almost zero we didn't find an intersection point
        return sameValue(p1, p2, x12, x2, eps);                     // Else check the second half
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
     * The function divides the range [x1,x2] into equal segments according to the number of segments
     * For each segment it takes the start and end points in the function for that segment,
     * and calculates the distance between them using the 'distanceOf2Points' function.
	 * @param p the polynomial function
	 * @param x1 minimal value of the range
	 * @param x2 maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments)
    {
		double ans = 0;
        double xSampleLength = (x2 - x1) / numberOfSegments;    // Calc the x length of each segment
        double a1 = x1;                                         // a1 is the start x coord of the current segment
        double a2;                                          // a1 is the end x coord of the current segment
        double b1 = f(p,a1);                                    // b1 is the start y coord of the current segment
        double b2;                                          // b2 is the end y coord of the current segment
        for(int i=0;i<numberOfSegments;i++)
        {
            a2 = a1 + xSampleLength;                            // Calc a2 for the current segment
            b2 = f(p,a2);                                       // Calc b2 for the current segment
            ans += distanceOf2Points(a1,a2,b1,b2);              // Calc distance between (a1,b1) (a2,b2) for the current segment and add it to the sum
            a1 = a2;
            b1 = b2;
        }
		return ans;
	}

    /**
     * Given two points (x1,y1) , (x2,y2).
     * This function computes the distance between the two points.
     * The area is computed using (<a href="https://www.mathsisfun.com/algebra/distance-2-points.html">...</a>)
     * distance of (x1,y1) (x2,y2) == sqrt((x2 - x1)^2 + (y2 - y1)^2)
     * @param x1 first point's x value
     * @param x2 second point's x value
     * @param y1 first point's y value
     * @param y2 second point's y value
     * @return the distance between the points.
     */
    private static double distanceOf2Points(double x1,double x2, double y1, double y2)
    {
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)); // algorithm is in the web page
    }
	
	/**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
     * This function computes an approximation of the area between the polynomial functions within the x-range.
     * The area is computed using Riemann's like integral (<a href="https://en.wikipedia.org/wiki/Riemann_integral">...</a>)
     * The function divides the range [x1,x2] into equal segments according to the number of segments
     * For each segment it calculates the start and end points in the functions for that segment,
     * It checks if the functions intersect at that segment.
     * If they intersect it finds the intersection point and calculates the area of the triangles that are created.
     * If they don't intersect it calculates the area of the trapezoid that is created.
     * Area of trapezoid -> (a + b) * h / 2  |  where a,b are the sides of the trapezoids (|f1(a1)-f2(a1)| , |f1(a2)-f2(a2)|), and h is the segment length,
     * After taking out common element for all trapezoids - ((a1 + b1) + ... + (ax + bx)) * h / 2
     * We just add all of the sides of the trapezoids together and multiply by the common element.
     * At the end we add the area of the triangles to the trapezoids.
     * @param p1 - first polynomial function
     * @param p2 - second polynomial function
     * @param x1 - minimal value of the range
     * @param x2 - maximal value of the range
     * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
     * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid)
    {
        double ans = 0;                                             // The sum of the area of the trapezoids
        double trianglesArea = 0;                                   // The sum of the area of the triangles when needed
        double xSampleLength = (x2 - x1) / numberOfTrapezoid;       // The x length of one segment
        double a1 = x1;                                             // a1 is the start x coord of the current segment
        double a2;                                              // a2 is the end x coord of the current segment
        double b11 = f(p1,a1);                                      // b11 is the f1(a1) coord of the current segment
        double b12 = f(p2,a1);                                      // b12 is the f2(a1) coord of the current segment
        double b21;                                             // b21 is the f1(a2) coord of the current segment
        double b22;                                             // b22 is the f2(a2) coord of the current segment
        for(int i=0;i<numberOfTrapezoid;i++)
        {
            a2 = a1 + xSampleLength;                                // Calc a2 for the current segment
            b21 = f(p1,a2);                                         // Calc b21 for the current segment
            b22 = f(p2,a2);                                         // Calc b22 for the current segment
            if((b11 - b12) * (b21 - b22) >= 0)                      // If there is NO intersection in the current segment
            {
                ans += Math.abs(b11 - b12) + Math.abs(b21 - b22);   // add the sides of the segment to the trapezoid calculation
            }
            else                                                    // If there IS an intersection in the current segment
            {
                double intersectionX = sameValue(p1, p2, a1, a2, EPS);  // Find the x point of intersection
                trianglesArea += (Math.abs(b11 - b12) * (intersectionX - a1) + Math.abs(b22 - b21) * (a2 - intersectionX)) / 2; // Divide the segment into two triangles at the intersection point and calc the area of the triangles
            }
            a1 += xSampleLength;
            b11 = b21;
            b12 = b22;
        }
        ans *= xSampleLength / 2;                                   // Finish the trapezoid calc for all of the segments
        ans += trianglesArea;                                       // Add the triangles area
		return ans;
	}

    /**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * The function gets a string from type "-1.2x^3 +3.1x^2 +2.0", splits the input string to elements by spaces.
     * If the string is not empty it finds the highest power of the polynom in the string to determine the length of the array.
     * The function loops over the string elements and for each it finds the power (to determine the position in the array)
     * And then the coefficient of the element to put in the array
	 * @param p a String representing polynomial function.
	 * @return the array representing the polynom in the string p.
	 */
	public static double[] getPolynomFromString(String p)
    {
        String[] splitString =  p.split(" ");                 // Make an array of strings, each index contains an element of the polynom
        double [] ans = ZERO;
        if(splitString.length > 0)                                  // If the string wasn't empty
        {
            // ============ Find Power of Poly ============
            if(splitString[0].contains("^"))                        // If the string highest power is greater than 1
            {
                ans = new double[Integer.parseInt(splitString[0].split("\\^")[1]) + 1]; // Find the power
            }
            else if (splitString[0].contains("x"))                  // If the string highest power is 1
            {
                ans = new double[2];
            }
            else                                                    // If the string highest power 0
            {
                ans = new double[1];
            }
            // ============ Build Poly ============
            for (String s : splitString) {
                // ============ Get Pow ============
                int pow = getPow(s);                                 // Get the power of the element
                // ============ Get Coefficient ============
                if (pow > 0)                                         // We need to split the string if there is an x
                {
                    String a = s.split("x")[0];
                    if (a.isEmpty())                                 // If there is no number before the x in the string - ("x^2")
                    {
                        ans[pow] = 1;
                    } 
                    else                                            // Else get the number
                    {
                        ans[pow] = Double.parseDouble(a);
                    }
                } 
                else                                                // There is no x, just a number in the string
                {
                    ans[pow] = Double.parseDouble(s);
                }
            }
        }
		return ans;
	}

    /**
     * This function finds the power of a string representing a polynom from type "Ax^n"
     * The function checks if the string contains a '^' and splits the string to find the power.
     * Else the function checks if the string contains an 'x' -> the power is 1.
     * Else the power is 0.
     * @param s the string to find the power of.
     * @return the power of the polynom in the string.
     */
    private static int getPow(String s)
    {
        if (s.contains("^"))                    // If the element power is greater than 1
        {
            return Integer.parseInt(s.split("\\^")[1]);
        }
        else if (s.contains("x"))               // Else if the element power is 1
        {
            return 1;
        }
        else                                    // Else the element power is 0
        {
            return 0;
        }
    }

    /**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     * The function checks which is the longer polynom of p1,p2.
     * the function copies the longer polynom to the answer
     * and then loops over the shorter polynom and adds it to the answer.
     * At the end it removes any leading zeros from the answer.
	 * @param p1 the first polynom to add.
	 * @param p2 the second polynom to add.
	 * @return an array representing the outcome of the addition of p1,p2.
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans;
        if (p1.length == 0 || p2.length == 0)                // If one of the polynoms is null don't preform the addition
        {
            return null;
        }
        if (p1.length >= p2.length)
        {
            ans = Arrays.copyOfRange(p1, 0, p1.length); // Copy the longer polynom (p1) to the answer
            for (int i = 0; i < p2.length; i++)
            {
                ans[i] += p2[i];                             // For each element of the shorter polynom, add it to the answer
            }
        }
        else
        {
            ans = Arrays.copyOfRange(p2, 0, p2.length); // Copy the longer polynom (p2) to the answer
            for (int i = 0; i < p1.length; i++)
            {
                ans[i] += p1[i];                             // For each element of the shorter polynom, add it to the answer
            }
        }
        ans = cutPolyLeadingZeros(ans);                      // If there are leading zeros remove them
		return ans;
	}

    /**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2).
     * The function makes a new array with the length of the highest power in the outcome
     * (the sum of the highest powers of each polynom),
     * loops over each element in p1 (that is not 0) and multilies it with each element in p2,
     * and adds the answer to the outcome array at the index of the sum of the powers of the elements multiplied.
	 * @param p1 the first polynom to multiply
	 * @param p2 the second polynom to multiply
	 * @return an array representing the outcome of the multiplication of p1,p2.
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans;
        if (p1.length == 0 || p2.length == 0)           // If one of the polynoms is null don't preform the multiplication
            return null;
        ans = new double[p1.length + p2.length - 1];    // Make a new array with the length of the highest power in the answer
        for (int i = 0; i < p1.length; i++)
        {
            if(p1[i] != 0)                              // For each element in p1 if it's not zero multiply it with every element in p2
            {
                for (int j = 0; j < p2.length; j++)
                {
                    ans[i+j] +=  p1[i] * p2[j];         // Put the outcome of the multiplication at the index of the sum of the powers
                }
            }
        }
		return ans;
	}

    /**
	 * This function computes the derivative of the p0 polynomial function.
     * The function creates a new array with length of po - 1,
     * and then loops over each element at index in po,
     * calculates the derivative and puts it in answer at index - 1
	 * @param po the polynomial to differentiate
	 * @return an array representing the derivative of po
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;
        if (po.length < 2)                  // If the polynom has only Cx^0 return ZERO
        {
            return ans;
        }
        ans  = new double[po.length - 1];
        for (int i = 1; i < po.length; i++)
        {
            ans[i-1] = po[i] * i;           // For element at index in po, calculate the derivative and put it in answer at index-1
        }
		return ans;
	}
}

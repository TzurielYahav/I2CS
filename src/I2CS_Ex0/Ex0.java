package I2CS_Ex0;
/**
 * This class is a basis for Ex0 (your first assigment),
 * The definition of the Ex0 can be found here: https://docs.google.com/document/d/1UtngN203ttQKf5ackCnXs4UnbAROZWHr/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * You are asked to complete the functions below amd may add additional functions if needed.

 */
public class Ex0 {
    public final static long ID = 316560499;  // Do update your ID here
    /**
     * This function checks if n is a prime number.
     * Notes:
     * if(n == 2 || n == 3)                     // check if n is 2 or 3 return true
     * else
     * if(n < 2 || n % 2 == 0 || n % 3 == 0)    // check if n is not a valid number or n divides in 2 or 3 return false
     * for (long i = 5; i * i <= n; i += 6)     // a loop to check for dividers of n, up to sqrt(n)
     * if(n % i == 0 || n % (i + 2) == 0)       // check only numbers from kind 6k+1||6k-1 (not divided by 2,3) i=6k-1, i+2=6k+1
     *
     * @param n (Integer) - the number to check, represented as long
     * @return true if and only if there is no integer (p) within the range of [2,n) which divides n.
     *
     */
    public static boolean isPrime(long n) {
        if(n == 2 || n == 3) {return true;}
        else
        {
            if(n < 2 || n % 2 == 0 || n % 3 == 0) {return false;}
        }
        for (long i = 5; i * i <= n; i += 6)
        {
            if(n % i == 0 || n % (i + 2) == 0) {return false;}
        }
        return true;
    }

    /**
     * This function finds the first prime integer (p1) >= start, for which p2=p1+n is also a prime number.
     * The function checks only small primes up to 11 and numbers from kind (6k-1 || 6k+1) to eliminate numbers divided by 2,3
     * Check if n is valid n>=2 && n%2==0
     * if (start < 11) manually check all for all small prime pairs up to 11 [if start==2 there are no prime pairs for 2 because n is even so 2+n==even]
     * if (start > 11) make start be from kind 6k-1 => | 1. not even | 2. not divide by 3 | 3. if n%3==1 (6k+1) check for prime pair and move to n%3==2
     * while
     * check for prime pair - start,start+n (6k-1) - return start
     * check for prime pair - start+2,start+n+2 (6k+1) - return start
     * start += 6 | go to next number of kind 6k-1
     *
     * @param start - a starting value from which p1 should be searched for.
     * @param n - a positive (even) integer value.
     * @return the first prime number p1 such that: i) p1>=start, ii) p1+n is a prime number.
     * in case a wrong value is given to the function
     * (n<0 or n is an odd number) the function should return -1.
     *
     */
    public static long getPrimePair(long start, long n) {
        long ans = -1;
        if(n >= 2 && n % 2 == 0)
        {
            if(start <= 11) // if start==11 => skip loop and else
            {
                for (long i = start; i < 11; i++)
                {
                    if((i == 3 || i == 5 || i == 7) && isPrime(i + n)) {return i;}
                }
            }
            else
            {
                if(start % 2 == 0)
                {
                    start += 1; // go to next odd number
                }
                if(start % 3 == 0)
                {
                    start += 2; // go to next number from kind 6k-1
                }
                else if(start % 3 == 1)
                {
                    if(isPrime(start) && isPrime(start + n)) {return start;}
                    start += 4; // go to next number from kind 6k-1
                }
            }
            while (ans == -1)
            {
                if(isPrime(start) && isPrime(start + n)) // 6k-1
                {
                    return start;
                }
                else if(isPrime(start + 2) && isPrime(start + 2 + n)) // 6k+1
                {
                    return start + 2;
                }
                start += 6;
            }
        }
        return ans;
    }

    /**
     * This function compute the first prime number p1 for which:
     * i) p1 >= start (p1 is a prime number)
     * ii) p1+n==p2 ia a prime number.
     * iii) there are no prime numbers in the (p1,p2) range.
     *
     * The function loops for prime pairs (start,start+n), if start+n is a prime but (start,start+n) is not a good pair,
     * start a loop, (move to start=start+n and check the new start+n for prime and pair,
     * if its prime but not pair move to next start=start+n until start+n is not prime),
     * then check next closest valid number start+=2
     *
     * check if n is valid n>=2 && n%2==0
     * if start < 3 => start = 3 [if start=2 => there are no prime pairs because n is even so 2+n==even]
     * if(start % 2 == 0) start += 1 // move to odd numbers
     * while
     *      check if start+n is prime
     *          check if start is prime
     *              check if there are primes between (start, start+n) => return start
     *          start+=n // move to next valid prime for pair
     *          while // loop until start+n is not a prime
     *              check if start+n is prime // we know start is a prime
     *                  check if there are primes between start, start+n => return start
     *              start+=n // move to next valid prime for pair
     *      start+=2 // move to next valid prime for pair
     *
     * @param start a positive integer which is the lower bound of p1.
     * @param n - the distance of the pair. a positive even integer.
     * @return a prime number p1>=start that the following prime number is p1+n.
     *
     */
    public static long getClosestPrimePair(long start, long n) {
        long ans = -1;
        if(n >= 2 && n % 2 == 0)
        {
            if(start < 3)
            {
                start = 3;
            }
            if(start % 2 == 0) {start += 1;}
            while (ans == -1)
            {
                if (isPrime(start + n))
                {
                    if (isPrime(start))
                    {
                        if (noPrimesBetweenPrimes(start, n)) // private function
                            return start;
                    }
                    start += n;
                    while (isPrime(start + n))
                    {
                        if (noPrimesBetweenPrimes(start, n)) // private function
                            return start;


                        start += n;
                    }
                }
                start += 2;
            }
        }
        //-----------------------------
//        if(n >= 2 && n % 2 == 0)
//        {
//            if(start < 3)
//            {
//                start = 3;
//            }
//            if(start % 2 == 0) {start += 1;}
//            while (ans == -1)
//            {
//                if (isPrime(start + n))
//                {
//                    if (isPrime(start))
//                    {
//                        if (noPrimesBetweenPrimes(start, n)) // private function
//                            return start;
//                    }
//                    start += n;
//                }
//                else start += 2;
//            }
//        }
        //-----------------------------
        return ans;
    }

    /**
     * This function compute the math positive integer p1 for which:
     * i) p1 is a prime number.
     * ii) p1+n==p2 ia a prime number.
     * iii) there are no prime numbers in the (p1,p2) range.
     *
     * check if m,n are valid
     * loop from 0 up to m                      // to find all pairs before needed pair
     *      ans = get next closest prime pair   // check for next prime pair
     *      ans += n                            // skip the pair to continue checking
     * ans = get next closest prime pair        // get the needed pair
     *
     * @param m - number of pairs before the pair. a none negative integer.
     * @param n - the distance of the pair. a positive even integer.
     * @return a prime number p1>=start that the following prime number is p1+n.
     *
     */
    public static long getMthClosestPrimePair(int m, long n) {
        if(m < 0 || n < 0 || n % 2 != 0) {
            System.err.println("Invalid input: got m="+m+", n="+n+"  |  m should be >=0 & n should be a positive even integer ");
            return -1;
        }
        long ans = 3;
        for(int i = 0; i < m; i++)
        {

            ans = getClosestPrimePair(ans, n);
            ans += n;
        }

        ans = getClosestPrimePair(ans, n);
        return ans;
    }

    /// //////// Private Functions - you are welcome to add additional (private) functions below.
    /**
     * This function checks if there are prime numbers between start and start + n
     *
     * if n==2 there are no numbers to check between the pair - return true
     * while (badPrime < start + n) // loop to check up to start+n
     *      if(isPrime(badPrime)) return false
     *      badPrime += 2;
     * return true
     * @param start a positive integer which is the lower bound of the pair.
     * @param n the distance between the pair. a positive even integer.
     * @return true if there are no primes between the pair.
     */
    private static boolean noPrimesBetweenPrimes(long start, long n)
    {
        if(n == 2)
        {
            return true;
        }
        long badPrime = start + 2;
        while (badPrime < start + n)
        {
            if(isPrime(badPrime))
            {
                return false;
            }
            badPrime += 2;
        }
        return  true;
    }
}

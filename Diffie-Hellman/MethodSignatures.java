package assign2;

public class MethodSignatures {

    public long computeRemainder(int base, int exponent, int prime) {
        long result = 1;
        long b = base % prime;
        
        while (exponent > 0) {
            if ((exponent & 1) == 1) { 
                result = (result * b) % prime;
            }
            exponent >>= 1;
            b = (b * b) % prime;
        }
        
        return result;
    }

    public long exchangeKeyValue(long value, int secretKey, int prime) {
        return computeRemainder((int) value, secretKey, prime);
    }

    public boolean isPrime(int p) {
        if (p <= 1) 
        	return false;
        for (int i = 2; i <= Math.sqrt(p); i++) {
            if (p % i == 0) 
            	return false;
        }
        return true;
    }
}



package assign2;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MethodSignatures methods = new MethodSignatures();

        int prime;
        do {
            System.out.print("Enter a prime number (p): ");
            prime = scanner.nextInt();
            if (!methods.isPrime(prime)) {
                System.out.println("The number you entered is not a prime number. Please try again. \n");
            }
        } while (!methods.isPrime(prime));

        int base;
        do {
            System.out.print("Enter the base (b): ");
            base = scanner.nextInt();
            if (!methods.isPrime(base)) {
                System.out.println("The base you entered is not a prime number. Please try again. \n");
            }
        } while (!methods.isPrime(base));

        System.out.print("Enter your private secret key (A): ");
        int secretKeyA = scanner.nextInt();

        System.out.print("Enter the other user's private secret key (B): ");
        int secretKeyB = scanner.nextInt();

        long publicKeyA = methods.computeRemainder(base, secretKeyA, prime);
        long publicKeyB = methods.computeRemainder(base, secretKeyB, prime);

        System.out.println("\nPublic key for User A (remainder): " + publicKeyA);
        System.out.println("Public key for User B (remainder): " + publicKeyB);
      
        long sharedSecretA = methods.exchangeKeyValue(publicKeyB, secretKeyA, prime);
        long sharedSecretB = methods.exchangeKeyValue(publicKeyA, secretKeyB, prime);

        System.out.println("\nShared secret key for (A): " + sharedSecretA);
        System.out.println("Shared secret key for (B): " + sharedSecretB);

        if (sharedSecretA == sharedSecretB) {
            System.out.println("The shared secret keys are identical.");
        } else {
            System.out.println("The shared secret keys are different.");
        }
        
        scanner.close();
    }
}



package des;

import java.util.Scanner;

public class Runner {
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

     System.out.print("Enter a 64-bit key in hexadecimal format: ");
     String key = scanner.nextLine();
     
     KeyGeneration keyGen = new KeyGeneration(key);
     keyGen.generateKeys();
     
     scanner.close();
 	}
}


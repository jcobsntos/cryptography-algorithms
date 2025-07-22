package des;

import java.util.ArrayList;
import java.util.List;

public class KeyGeneration {
    private String hexKey;
    private List<String> subKeys = new ArrayList<>();

    // TABLE 3.1
    private static final int[] TABLE31 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
   
    // Shift for each round table 3.2
    private static final int[] TABLE32 = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1
    };

    // TABLE 3.3
    private static final int[] TABLE33 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

  
    public KeyGeneration(String hexKey) {
        this.hexKey = hexKey;
    }

    public void generateKeys() {
        String binaryKey = hexToBinary(hexKey);
        System.out.println("Initial 64-bit key (binary): " + binaryKey);
        System.out.println("Initial 64-bit key (hex):    " + hexKey);

        String permutedKey = permute(binaryKey, TABLE31);
        System.out.println("\nApply Table 3.1 (56-bit key):");
        System.out.println("Binary: " + permutedKey);
        System.out.println("Hex:    " + binaryToHex(permutedKey));

        String C = permutedKey.substring(0, 28);  
        String D = permutedKey.substring(28, 56); 

        for (int i = 0; i < 16; i++) {
            
        	System.out.printf("\n--- Round %2d ---\n", i + 1);

            if (i == 0) {
 
                System.out.println("Divide into 2 groups");
                System.out.printf("C0 (binary): %s\n", C);
                System.out.printf("D0 (binary): %s\n", D);
                System.out.printf("C0 (hex): %s\n", binaryToHex(C));
                System.out.printf("D0 (hex): %s\n", binaryToHex(D));
            } else {
                System.out.printf("Using C%d and D%d from previous round to generate C%d and D%d.\n", i, i, i + 1, i + 1);
                System.out.println("\nDivide into 2 groups");
                System.out.printf("C%d (binary): %s\n", i, C);
                System.out.printf("D%d (binary): %s\n", i, D);
                System.out.printf("C%d (hex): %s\n", i, binaryToHex(C));
                System.out.printf("D%d (hex): %s\n", i, binaryToHex(D));
            }

           
            C = leftShift(C, TABLE32[i]);
            D = leftShift(D, TABLE32[i]);

            System.out.println("\nRefer to Table 3.2");
            System.out.println("Shift Left (S2): " + C + " (binary) " );
            System.out.println("Shift Left (S3): " + D + " (binary) " );
            
           
            String combinedCD = C + D;
            String subKey = permute(combinedCD, TABLE33);
            subKeys.add(subKey);

            System.out.println("S2 || S3 " + combinedCD + " (binary) " + binaryToHex(combinedCD) + " (hex)");
            System.out.println("C" + (i + 1) + ": " + C + " (binary) " + binaryToHex(C) + " (hex)");
            System.out.println("D" + (i + 1) + ": " + D + " (binary) " + binaryToHex(D) + " (hex)");

            System.out.println("\nApply Table 3.3");
            System.out.printf("K%2d (binary): %s%n", i + 1, subKey);
            System.out.printf("K%2d (hex):    %s%n", i + 1, binaryToHex(subKey));
        }
    }

    private String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int position : table) {
            output.append(input.charAt(position - 1));
        }
        return output.toString();
    }

    private String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();
        for (char hexChar : hex.toCharArray()) {
            binary.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(hexChar), 16))).replace(" ", "0"));
        }
        return binary.toString();
    }

    private String binaryToHex(String binary) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 4) {
            int decimal = Integer.parseInt(binary.substring(i, i + 4), 2);
            hex.append(Integer.toHexString(decimal).toUpperCase());
        }
        return hex.toString();
    }

    private String leftShift(String input, int numShifts) {
        return input.substring(numShifts) + input.substring(0, numShifts);
    }
}

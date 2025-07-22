package assign;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plaintext to be hashed:");
        String inputText = scanner.nextLine();

        // Check if the input is hexadecimal
        if (!isHexadecimal(inputText)) {
            // Convert input to hexadecimal
            inputText = convertToHex(inputText);
            System.out.println("Input was not hexadecimal. Converted to Hex: " + inputText);
        }

        ShaMethod1 shaMethod1 = new ShaMethod1();
        String paddedMessage = shaMethod1.padMessage(inputText);

        // Display original message in bits and padded message in hex
        int originalLengthBits = inputText.length() * 4;  // Each hex digit is 4 bits
        String originalLengthHex = String.format("%x", originalLengthBits);
        System.out.println("Original Message (Hex): " + inputText);
        System.out.println("Original Message size (bits): " + originalLengthBits);
        System.out.println("Original Message size to hex: " + originalLengthHex);
        System.out.println("Padded Message (Hex): " + paddedMessage);
        System.out.println("Padded message size (bits): " + (paddedMessage.length() * 4)); // Length in bits

        // Process the padded message using the SHA-1 algorithm
        ShaMethod2 shaMethod2 = new ShaMethod2();
        String finalDigest = shaMethod2.processMessage(paddedMessage);

        System.out.println("Final Message Digest (Hex): " + finalDigest);
    }

    // Check if a string is in hexadecimal format
    private static boolean isHexadecimal(String input) {
        return input.matches("^[0-9a-fA-F]+$");
    }

    // Convert input string to hexadecimal representation
    private static String convertToHex(String input) {
        StringBuilder hex = new StringBuilder();
        for (char c : input.toCharArray()) {
            hex.append(String.format("%02x", (int) c));
        }
        return hex.toString();
    }
}

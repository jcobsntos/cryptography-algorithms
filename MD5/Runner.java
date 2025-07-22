package md5;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plaintext to hash using MD5:");
        String input = scanner.nextLine();
        scanner.close();

        Md5Method1 method1 = new Md5Method1();
        String hexMessage = method1.convertToHex(input);
        System.out.println("Hexadecimal Message: " + hexMessage);
        System.out.println("Original Message Size: " + (input.getBytes().length * 8) + " bits");

        byte[] paddedMessage = method1.padMessage(input);
        System.out.println("Padded Message (Hex): " + bytesToHex(paddedMessage));
        System.out.println("Padded Message Size: " + (paddedMessage.length * 8) + " bits");

        byte[] finalMessage = method1.appendLength(paddedMessage, input.getBytes().length * 8);
        System.out.println("Final Message (Hex): " + bytesToHex(finalMessage));
        System.out.println("Final Message Size: " + (finalMessage.length * 8) + " bits");

        Md5Method2 method2 = new Md5Method2();
        String md5Hash = method2.computeMD5(finalMessage);
        System.out.println("MD5 Hash: " + md5Hash);
    }

    // Utility method to convert bytes to hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString((b & 0xFF));
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package md5;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Md5Method1 {

    // Converts input text to hexadecimal. If already in hex range, returns as is.
    public String convertToHex(String input) {
        if (isHex(input)) {
            return input.toLowerCase();
        } else {
            return bytesToHex(input.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Checks if the input string is hexadecimal
    private boolean isHex(String input) {
        return input.matches("^[0-9a-fA-F]+$");
    }

    // Converts bytes to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString((b & 0xFF));
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Pads the message as per MD5 specifications
    public byte[] padMessage(String input) {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        int originalLength = inputBytes.length;
        long bitLength = (long) originalLength * 8;

        // Append '1' bit followed by '0' bits until message length ≡ 448 mod 512
        int paddingLength = (56 - (originalLength + 1) % 64);
        if (paddingLength < 0) {
            paddingLength += 64;
        }

        byte[] padding = new byte[1 + paddingLength];
        padding[0] = (byte) 0x80; // Append '1' bit (10000000)

        byte[] paddedMessage = new byte[originalLength + padding.length];
        System.arraycopy(inputBytes, 0, paddedMessage, 0, originalLength);
        System.arraycopy(padding, 0, paddedMessage, originalLength, padding.length);

        return paddedMessage;
    }

    // Appends the original message length as a 64-bit little-endian integer
    public byte[] appendLength(byte[] paddedMessage, long originalBitLength) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(originalBitLength);
        byte[] lengthBytes = buffer.array();

        byte[] finalMessage = new byte[paddedMessage.length + 8];
        System.arraycopy(paddedMessage, 0, finalMessage, 0, paddedMessage.length);
        System.arraycopy(lengthBytes, 0, finalMessage, paddedMessage.length, 8);

        return finalMessage;
    }
}

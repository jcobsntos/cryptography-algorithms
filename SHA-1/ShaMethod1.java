package assign;

public class ShaMethod1 {

    // Pads the message according to SHA-1 standards
    public String padMessage(String messageHex) {
        int originalLengthBits = messageHex.length() * 4;  // Each hex digit is 4 bits
        StringBuilder paddedMessage = new StringBuilder(messageHex);

        // Padding: append '80' (binary 10000000) to signify the '1' bit followed by zeroes
        paddedMessage.append("80");

        // Add zeros until the length is congruent to 448 mod 512
        while ((paddedMessage.length() * 4) % 512 != 448) {
            paddedMessage.append("00");
        }

        // Append original message length in bits (as a 64-bit big-endian number)
        String originalLengthHex = String.format("%016x", originalLengthBits);
        paddedMessage.append(originalLengthHex);

        return paddedMessage.toString();
    }
}





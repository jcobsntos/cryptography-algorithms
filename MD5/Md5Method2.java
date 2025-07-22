package md5;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Md5Method2 {
    // Initialize variables:
    private int A = 0x67452301;
    private int B = 0xefcdab89;
    private int C = 0x98badcfe;
    private int D = 0x10325476;

    // Define S specifies the per-round shift amounts
    private final int[] S = {
        7, 12, 17, 22, // Round 1
        7, 12, 17, 22,
        7, 12, 17, 22,
        7, 12, 17, 22,
        5, 9, 14, 20,  // Round 2
        5, 9, 14, 20,
        5, 9, 14, 20,
        5, 9, 14, 20,
        4, 11, 16, 23, // Round 3
        4, 11, 16, 23,
        4, 11, 16, 23,
        4, 11, 16, 23,
        6, 10, 15, 21, // Round 4
        6, 10, 15, 21,
        6, 10, 15, 21,
        6, 10, 15, 21
    };

    // Use the sin function to initialize K
    private final int[] K = new int[64];

    public Md5Method2() {
        for (int i = 0; i < 64; i++) {
            K[i] = (int) ((long) (Math.abs(Math.sin(i + 1)) * Math.pow(2, 32)));
        }
    }

    // Left rotate function
    private int leftRotate(int x, int c) {
        return (x << c) | (x >>> (32 - c));
    }

    // Compute MD5 hash
    public String computeMD5(byte[] message) {
        // Process each 512-bit block
        int numBlocks = message.length / 64;
        for (int i = 0; i < numBlocks; i++) {
            // Break chunk into sixteen 32-bit little-endian words M[j], 0 ≤ j ≤ 15
            int[] M = new int[16];
            for (int j = 0; j < 16; j++) {
                int index = i * 64 + j * 4;
                M[j] = ((message[index] & 0xFF)) |
                       ((message[index + 1] & 0xFF) << 8) |
                       ((message[index + 2] & 0xFF) << 16) |
                       ((message[index + 3] & 0xFF) << 24);
            }

            // Initialize hash value for this chunk
            int a = A;
            int b = B;
            int c = C;
            int d = D;

            // Main loop
            for (int j = 0; j < 64; j++) {
                int F = 0, g = 0;

                if (j >= 0 && j <= 15) {
                    F = (b & c) | (~b & d);
                    g = j;
                } else if (j >= 16 && j <= 31) {
                    F = (d & b) | (~d & c);
                    g = (5 * j + 1) % 16;
                } else if (j >= 32 && j <= 47) {
                    F = b ^ c ^ d;
                    g = (3 * j + 5) % 16;
                } else if (j >= 48 && j <= 63) {
                    F = c ^ (b | ~d);
                    g = (7 * j) % 16;
                }

                int temp = d;
                d = c;
                c = b;
                b = b + leftRotate(a + F + K[j] + M[g], S[j]);
                a = temp;

                // Log the iteration details
                System.out.println("Iteration " + (j + 1) + ":");
                System.out.println("F: " + Integer.toHexString(F));
                System.out.println("g: " + g);
                System.out.println("K[" + j + "]: " + Integer.toHexString(K[j]));
                System.out.println("M[" + g + "]: " + Integer.toHexString(M[g]));
                System.out.println("A: " + Integer.toHexString(a));
                System.out.println("B: " + Integer.toHexString(b));
                System.out.println("C: " + Integer.toHexString(c));
                System.out.println("D: " + Integer.toHexString(d));
                System.out.println("---------------------------");
            }

            // Add this chunk's hash to result so far:
            A += a;
            B += b;
            C += c;
            D += d;
        }

        // Produce the final hash value (little-endian) as a 128 bit number:
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(A);
        buffer.putInt(B);
        buffer.putInt(C);
        buffer.putInt(D);
        byte[] md5Bytes = buffer.array();

        return Runner.bytesToHex(md5Bytes);
    }
}

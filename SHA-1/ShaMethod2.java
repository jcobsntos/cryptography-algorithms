package assign;
public class ShaMethod2 {

    // Round constants for SHA-1
    private final int[] K = {
        0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6
    };

    // Initial hash values (A, B, C, D, E)
    private int[] H = {
        0x67452301, // A
        0xEFCDAB89, // B
        0x98BADCFE, // C
        0x10325476, // D
        0xC3D2E1F0  // E
    };

    // Processes the padded message in blocks of 512 bits
    public String processMessage(String paddedMessage) {
        int blockCount = paddedMessage.length() * 4 / 512;

        // Process each 512-bit block
        for (int blockIndex = 0; blockIndex < blockCount; blockIndex++) {
            String block = paddedMessage.substring(blockIndex * 128, (blockIndex + 1) * 128);
            processBlock(block);
        }

        // Concatenate the final hash value
        return String.format("%08x%08x%08x%08x%08x", H[0], H[1], H[2], H[3], H[4]);
    }

    // Process a 512-bit block
    private void processBlock(String block) {
        int[] W = new int[80];  // Message schedule array W[0..79]

        // Initialize W[0..15] from the block
        for (int t = 0; t < 16; t++) {
            W[t] = Integer.parseUnsignedInt(block.substring(t * 8, (t + 1) * 8), 16);
        }

        // Expand W[16..79] and initialize them to null first
        for (int t = 16; t < 80; t++) {
            W[t] = 0; // Set W[16..79] to null initially
        }

        // Print the first W[0]-W[15]
        System.out.println("W[0]-W[15]:");
        for (int t = 0; t < 16; t++) {
            System.out.printf("W[%d] = %08x\n", t, W[t]);
        }

        // Print W[16]-W[79] as null first
        System.out.println("W[16]-W[79] (Initial null values):");
        for (int t = 16; t < 80; t++) {
            System.out.printf("W[%d] = null\n", t);
        }

        // Expand W[16]-W[79] with calculated values
        for (int t = 16; t < 80; t++) {
            W[t] = leftRotate(W[t - 3] ^ W[t - 8] ^ W[t - 14] ^ W[t - 16], 1);
        }

        // Show all calculated values for W[0]-W[79]
        System.out.println("Calculated W[0]-W[79]:");
        for (int t = 0; t < 80; t++) {
            System.out.printf("W[%d] = %08x\n", t, W[t]);
        }

        // Step 2: Initialize A, B, C, D, E
        int A = H[0], B = H[1], C = H[2], D = H[3], E = H[4];

        // Perform 80 rounds (T[0]-T[79])
        for (int t = 0; t < 80; t++) {
            int temp = leftRotate(A, 5) + f(t, B, C, D) + E + W[t] + K(t);
            E = D;
            D = C;
            C = leftRotate(B, 30);
            B = A;
            A = temp;

            // Output each round T[0]-T[79] with the current state (A, B, C, D, E), but without W[t]
            System.out.printf("T[%02d]: A=%08x B=%08x C=%08x D=%08x E=%08x\n", 
                              t, A, B, C, D, E);
        }

        // Add the compressed chunk to the current hash value
        H[0] = (H[0] + A) & 0xFFFFFFFF;
        H[1] = (H[1] + B) & 0xFFFFFFFF;
        H[2] = (H[2] + C) & 0xFFFFFFFF;
        H[3] = (H[3] + D) & 0xFFFFFFFF;
        H[4] = (H[4] + E) & 0xFFFFFFFF;
    }

    // Non-linear function f(t)
    private int f(int t, int B, int C, int D) {
        if (t < 20) return (B & C) | (~B & D);
        if (t < 40) return B ^ C ^ D;
        if (t < 60) return (B & C) | (B & D) | (C & D);
        return B ^ C ^ D;
    }

    // Left-rotate a 32-bit integer by n bits
    private int leftRotate(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    // Returns the appropriate round constant based on t
    private int K(int t) {
        if (t < 20) return K[0];
        if (t < 40) return K[1];
        if (t < 60) return K[2];
        return K[3];
    }
}

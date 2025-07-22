package md5;

public class Runner {
    
	public static void main(String[] args) {
        
    	String input = "89ABCDEF";

        LittleEndianMethod littleEndianCalculator = new LittleEndianMethod();

        int value;
        try {
            value = (int) Long.parseLong(input, 16);
        } catch (NumberFormatException e) {
            System.out.println("Error: Input is not a valid hexadecimal string.");
            return;
        }

        System.out.println("Original hex value: " + input);
        
        int littleEndianValue = littleEndianCalculator.convertToLittleEndian(value);
        System.out.printf("Final little endian value: %08X\n", littleEndianValue);
    }
}



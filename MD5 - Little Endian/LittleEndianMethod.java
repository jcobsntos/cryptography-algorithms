package md5;

public class LittleEndianMethod {
   
	public int convertToLittleEndian(int value) {
 
        int byte1 = (value & 0xFF) << 24;
        System.out.printf("(value & 0xFF) << 24 => %08X\n", byte1);

        int byte2 = (value & 0xFF00) << 8;
        System.out.printf("(value & 0xFF00) << 8 => %08X\n", byte2);

        int byte3 = (value >> 8) & 0xFF00;
        System.out.printf("(value >> 8) & 0xFF00 => %08X\n", byte3);
  
        int byte4 = (value >> 24) & 0xFF;
        System.out.printf("(value >> 24) & 0xFF => %08X\n", byte4);

        int littleEndianValue = byte1 | byte2 | byte3 | byte4;

        return littleEndianValue;
    }
}



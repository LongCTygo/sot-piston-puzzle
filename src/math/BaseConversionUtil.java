package math;

public class BaseConversionUtil {
    public static String hexToBin(String hex){
        hex = hex.toLowerCase();
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("a", "1010");
        hex = hex.replaceAll("b", "1011");
        hex = hex.replaceAll("c", "1100");
        hex = hex.replaceAll("d", "1101");
        hex = hex.replaceAll("e", "1110");
        hex = hex.replaceAll("f", "1111");
        return hex;
    }

    public static String binToHex(String bin){
        int decimal = Integer.parseInt(bin,2);
        String hex = Integer.toString(decimal,16);
        return hex;
    }

    public static String getTinyBit(int value) {
        switch (value) {
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "10";
            case 3:
                return "11";
        }
        throw new IllegalArgumentException("Cannot convert.");
    }
}

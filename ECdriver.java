package hw10;

public class ECdriver {
    public static void main(String[] args) {
        FullDES fd = new FullDES(" 0001001100110100010101110111100110011011101111001101111111110001");
        long res = fd.encrypt
                ("0000000100100011010001010110011110001001101010111100110111101111");
        String binres = BinUtil.leftPad(Long.toBinaryString(res), 64, '0');
        System.out.println(binres);
    }
}

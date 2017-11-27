package hw10;

public class ECdriver {
    public static void main(String[] args) {
        FullDES fd = new FullDES("1110010101100111110011011011001111111101110011100111111100101010");
        long res = fd.encrypt
                ("00100101011001111100110110110011111111011100111000101010");
        String binres = BinUtil.leftPad(Long.toBinaryString(res), 64, '0');
        System.out.println(binres);
    }
}

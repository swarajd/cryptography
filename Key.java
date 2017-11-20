package hw10;

/**
 * Created by swarajd on 10/26/17.
 */
public class Key {
    private int[] keyArray;
    private String kStr;
    public Key(int[] kA) {
        this.keyArray = kA;
    }

    public Key(String k) {
        this.keyArray = SPNUtil.StringToArr(k);
        this.kStr = k;
    }

    public int[] xor(int[] plaintext) {
        int len = plaintext.length;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = keyArray[i] ^ plaintext[i];
        }
        return result;
    }

    public int[] xor(String plaintext) {
        int[] ptArr = SPNUtil.StringToArr(plaintext);
        return this.xor(ptArr);
    }

    public String toString() {
        return this.kStr;
    }
}

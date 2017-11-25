package hw10;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;

public class BinUtil {

    public static int xorAllBits(int a, int x, int bitlen) {
        int res = 0;
        int mask = 1;
        for (int i = 0; i < bitlen; i++) {
            int curMask = mask << i;
            res ^= ((x & curMask) & (a & curMask)) >> i;
        }
        return res;
    }

    public static String leftPad(String s, int l, char c) {
        if (s.length() > l) {
            return s;
        }
        int diff = l - s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < diff; i++) {
            sb.append(c);
        }
        sb.append(s);
        return sb.toString();
    }

    public static int binStrToInt(String s) {
        int res = 0;
        for (int i = 3, pow = 0; i >= 0; i--, pow++) {
            int curInt = s.charAt(i) == '1' ? 1 : 0;
            res += Math.pow(2, pow) * curInt;
        }
        return res;
    }

    public static String intToBinStr(int i, int len) {
        String partial;
        if (i == 0) {
            return leftPad("", len, '0');
        }
        StringBuilder sb = new StringBuilder();
        while (i != 0) {
            sb.append(i & 1);
            i >>= 1;
        }
        partial = sb.reverse().toString();
        return leftPad(partial, len, '0');
    }

    public static String hexToBinStr(String hex) {
        StringBuilder sb = new StringBuilder();

        for (Character c: hex.toCharArray()) {
            int cur = Character.digit(c, 16);
            sb.append(intToBinStr(cur, 4));
        }
        return sb.toString();
    }

    public static ArrayList<Integer> differingBits(String a, String b, int
            len) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = len - 1; i >= 0; i--) {
            if (a.charAt(i) != b.charAt(i)) {
                res.add(len-i-1);
            }
        }
        return res;
    }


}

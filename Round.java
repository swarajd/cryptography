package hw10;

import java.util.ArrayList;

/**
 * Created by swarajd on 10/26/17.
 */
public class Round {

    private static boolean displayState = false;

    private int l;

    private Key k;
    private String w;
    private String u;
    private Sbox sb;
    private Permutation pm;
    private String v;

    private String output;

    private int roundType;

    public String getU() {
        return u;
    }

    public String getW() { return w; }


    public Round(Key key, Sbox s, Permutation p, int l) {
        this.k = key;
        this.sb = s;
        this.pm = p;
        this.l = l;
        this.roundType = 0;
        this.output = "";
    }

    /*
        0 - normal round
        1 - second to last round
        2 - last round
     */
    public Round(Key key, Sbox s, Permutation p, int l, int round) {
        this.k = key;
        this.sb = s;
        this.pm = p;
        this.l = l;
        this.roundType = round;
        this.output = "";
    }

    public void setKey(Key key) {
        this.k = key;
    }

    public void setSbox(Sbox s) {
        this.sb = s;
    }

    public void setPermutation(Permutation p) {
        this.pm = p;
    }

    public void setRoundType(int rt) { this.roundType = rt; }

    public String encrypt(String p) {
        this.w = p;
        if (displayState) System.out.printf("K: %s\n", this.k.toString());
        this.u = SPNUtil.ArrToString(this.k.xor(p));
        if (displayState) System.out.printf("u: %s\n", this.u);
        this.output = this.u;

        String postSbox = this.u;
        if (this.roundType == 0 || this.roundType == 1) {
            ArrayList<String> substrings = SPNUtil.splitString(this.u, this.l);

            ArrayList<String> translated = new ArrayList<>();
            for (String s : substrings) {
                translated.add(this.sb.substitute(s));
            }
            postSbox = SPNUtil.mergeSubstrings(translated);
            this.output = postSbox;
        }

        if (this.roundType == 0) {
            int[] bits = SPNUtil.StringToArr(postSbox);
            int[] permuted = this.pm.permuteArray(bits);

            this.v = SPNUtil.ArrToString(permuted);
            this.output = this.v;
            if (displayState) System.out.printf("v: %s\n", this.v);
        }

        return this.output;
    }

    public String decrypt(String c) {
        String result = c;

        if (this.roundType == 0) {
            int[] bits = SPNUtil.StringToArr(c);
            int[] inversed = this.pm.inverseArray(bits);

            result = SPNUtil.ArrToString(inversed);
        }

        if (this.roundType == 0 || this.roundType == 1) {
            ArrayList<String> substrings = SPNUtil.splitString(result, this.l);

            ArrayList<String> translated = new ArrayList<>();
            for (String s : substrings) {
                translated.add(this.sb.inverse(s));
            }
            result = SPNUtil.mergeSubstrings(translated);
        }

        result = SPNUtil.ArrToString(this.k.xor(result));

        return result;
    }
}
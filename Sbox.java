package hw10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swarajd on 10/26/17.
 */
public class Sbox {
    private HashMap<String, String> strSubstitutions;
    private HashMap<String, String> strInverse;
    private HashMap<Integer, Integer> intSubstitutions;
    private HashMap<Integer, Integer> intInverse;
    private int l;

    public Sbox(HashMap<String, String> subs) {
        this.strSubstitutions = subs;
        this.strInverse = new HashMap<>();
        for (Map.Entry<String, String> entry : this.strSubstitutions.entrySet()) {
            this.strInverse.put(entry.getValue(), entry.getKey());
        }
    }

    public Sbox(int l) {
        this.strSubstitutions = new HashMap<>();
        this.strInverse = new HashMap<>();
        this.intSubstitutions = new HashMap<>();
        this.intInverse = new HashMap<>();
        this.l = l;
    }

    public void init(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            this.put(i, arr[i]);
        }
    }

    public void put(String key, String val) {
        this.strSubstitutions.put(key, val);
        this.strInverse.put(val, key);
    }

    public void put(int key, int val) {

        this.intSubstitutions.put(key, val);
        this.intInverse.put(val, key);

        // KEY
        String keybinary = Integer.toBinaryString(key);

        // generate the left padding
        int keylen = keybinary.length();
        int diff = this.l - keylen;
        char[] buffer = new char[diff];
        Arrays.fill(buffer, '0');
        String buf = new String(buffer);

        // append left padding and binary
        String k = buf + keybinary;


        // VAL
        String valbinary = Integer.toBinaryString(val);

        // generate the left padding
        int vallen = valbinary.length();
        diff = this.l - vallen;
        buffer = new char[diff];
        Arrays.fill(buffer, '0');
        buf = new String(buffer);

        // append left paddng and binary
        String v = buf + valbinary;

        this.put(k, v);
    }

    public String substitute(String in) {
        return this.strSubstitutions.get(in);
    }

    public int substitute(int in) {
        return this.intSubstitutions.get(in);
    }

    public String inverse(String in) {
        return this.strInverse.get(in);
    }

    public int inverse(int in) { return this.intInverse.get(in); }
}
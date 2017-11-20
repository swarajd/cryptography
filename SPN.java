package hw10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by swarajd on 10/26/17.
 */
public class SPN {

    private static boolean showRounds = false;

    private int l;
    private int m;

    private String curPlaintext;

    private ArrayList<Round> rounds;

    public SPN(int l, int m) {
        this.l = l;
        this.m = m;

        this.rounds = new ArrayList<>();
        this.curPlaintext = "";
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public void setRounds(Round[] rounds) {
        this.rounds = new ArrayList<Round>(Arrays.asList(rounds));
    }

    public void addRound(Round r) {
        rounds.add(r);
    }

    public void removeRound(int i) {
        rounds.remove(i);
    }

    public String encrypt(String plaintext) {
        this.curPlaintext = plaintext;
        if (showRounds) System.out.printf("w0: %s\n", this.curPlaintext);
        for (int i = 0; i < rounds.size(); i++) {
            Round r = rounds.get(i);
            this.curPlaintext = r.encrypt(this.curPlaintext);
            if (showRounds) System.out.printf("w%d: %s\n", i + 1, this.curPlaintext);
        }
        return this.curPlaintext;
    }


    public String decrypt(String ciphertext) {
        for (int i = rounds.size() - 1; i >= 0; i--) {
            ciphertext = rounds.get(i).decrypt(ciphertext);
        }
        return ciphertext;
    }

    public String getState() {
        return this.curPlaintext;
    }

}

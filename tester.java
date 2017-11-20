package hw10;

import java.util.HashMap;

/**
 * Created by swarajd on 10/26/17.
 */
public class tester {
    public static void main(String[] args) {
        int l = 4;
        int m = 4;
        SPN test = new SPN(l, m);

        String key = "00111010100101001101011000111111";
        KeyScheduler ks = new KeyScheduler(key);

        // initialize the keys
        Key k_1 = new Key(ks.getKey(1));
        Key k_2 = new Key(ks.getKey(2));
        Key k_3 = new Key(ks.getKey(3));
        Key k_4 = new Key(ks.getKey(4));
        Key k_5 = new Key(ks.getKey(5));

        // initialize the Sbox
        Sbox sb = new Sbox(l);

        int[] sbinit = { 0x0E, 0x04, 0x0D, 0x01, 0x02, 0x0F, 0x0B, 0x08,
                         0x03, 0x0A, 0x06, 0x0C, 0x05, 0x09, 0x00, 0x07
        };

        sb.init(sbinit);

        // initialize the permutation
        int[] perm = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
        Permutation pm = new Permutation(perm);

        // create the rounds
        Round r1 = new Round(k_1, sb, pm, l);
        Round r2 = new Round(k_2, sb, pm, l);
        Round r3 = new Round(k_3, sb, pm, l);
        Round r4 = new Round(k_4, sb, pm, l, 1);
        Round r5 = new Round(k_5, sb, pm, l, 2);

        // add the rounds to the SPN
        test.addRound(r1);
        test.addRound(r2);
        test.addRound(r3);
        test.addRound(r4);
        test.addRound(r5);

        // initialize the plaintexts
        String p1 = "0010011010110111";

        String c1 = test.encrypt(p1);
        System.out.printf("original: %s, encrypted: %s\n", p1, c1);

        String p1_ = test.decrypt(c1);
        System.out.printf("original: %s, decrypted: %s\n", c1, p1_);

    }
}

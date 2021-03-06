package hw10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static hw10.partialAES.getResult;

/**
 * Created by swarajd on 11/19/17.
 */
public class driver {
    public static void main(String[] args) {

        // ===== INITIALIZING ALL THE DATA =====
        String[] plaintexts = {"3DB0", "3DB1", "3DB2", "3DB3"};
        String[] ciphertexts = {"D0A2", "D0A3"};
        String[] key_strs = {"4FE83AF9", "CFE83AF9", "4EE83AF9", "CEE83AF9"};


        // ===== INITIALIZING THE DATA FOR THE SPN =====
        int l = 4;
        int m = 4;
        SPN spn = new SPN(l, m);

        // initialize the Sbox
        Sbox sb = new Sbox(l);

        int[] sbinit = {0x0E, 0x04, 0x0D, 0x01, 0x02, 0x0F, 0x0B, 0x08,
                0x03, 0x0A, 0x06, 0x0C, 0x05, 0x09, 0x00, 0x07
        };

        sb.init(sbinit);

        // initialize the permutation
        int[] perm = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
        Permutation pm = new Permutation(perm);


        // ===== 2a =====
        String binKey = BinUtil.hexToBinStr(key_strs[0]);
        KeyScheduler ks = new KeyScheduler(binKey);
        Key[] subkeys = new Key[5];
        for (int i = 0; i < 5; i++) {
            String curSt = ks.getKey(i + 1);
            subkeys[i] = new Key(curSt);
        }


        Round[] rounds = new Round[5];
        for (int i = 0; i < 5; i++) {
            rounds[i] = new Round(subkeys[i], sb, pm, l);
        }

        rounds[3].setRoundType(1);
        rounds[4].setRoundType(2);

        spn.setRounds(rounds);

        ArrayList<String> encryptedList = new ArrayList<>();
        for (String pt : plaintexts) {
            String binStr = BinUtil.hexToBinStr(pt);
            String encStr = spn.encrypt(binStr);
            encryptedList.add(encStr);
        }


        String el1 = encryptedList.get(0);
        String el2 = encryptedList.get(1);
        String el3 = encryptedList.get(2);
        String el4 = encryptedList.get(3);

        ArrayList<Integer> p1 = BinUtil.differingBits(el1, el2, 16);
        ArrayList<Integer> p2 = BinUtil.differingBits(el3, el4, 16);
        ArrayList<Integer> p3 = BinUtil.differingBits(el1, el3, 16);
        ArrayList<Integer> p4 = BinUtil.differingBits(el2, el4, 16);

        System.out.println("===== 2a =====");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        // ===== 2b =====

        System.out.println("===== 2b =====");


        for (String enc : ciphertexts) {
            String curBin = BinUtil.hexToBinStr(enc);
            String decrypted = spn.decrypt(curBin);
            ArrayList<Integer> positions = BinUtil.differingBits(curBin,
                    decrypted, 16);
            System.out.println(positions);
        }


        // ===== 2c =====
        String plaintext = BinUtil.hexToBinStr(plaintexts[0]);

        encryptedList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            binKey = BinUtil.hexToBinStr(key_strs[i]);
            ks = new KeyScheduler(binKey);
            subkeys = new Key[5];
            for (int j = 0; j < 5; j++) {
                subkeys[j] = new Key(ks.getKey(j + 1));
            }

            rounds = new Round[5];
            for (int j = 0; j < 5; j++) {
                rounds[j] = new Round(subkeys[j], sb, pm, l);
            }

            spn.setRounds(rounds);

            String encrypted = spn.encrypt(plaintext);
            encryptedList.add(encrypted);
        }

        el1 = encryptedList.get(0);
        el2 = encryptedList.get(1);
        el3 = encryptedList.get(2);
        el4 = encryptedList.get(3);

        p1 = BinUtil.differingBits(el1, el2, 16);
        p2 = BinUtil.differingBits(el3, el4, 16);
        p3 = BinUtil.differingBits(el1, el3, 16);
        p4 = BinUtil.differingBits(el2, el4, 16);

        System.out.println("===== 2c =====");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        // ===== 3b =====
        System.out.println("===== 3b =====");
        int pi_z1 = getResult(0);
        int pi_z2 = getResult(1);
        int pi_z3 = getResult(2);
        int pi_z4 = getResult(3);

        System.out.println(pi_z1 ^ pi_z2);
        System.out.println(pi_z3 ^ pi_z4);

        // ===== 4 INITIALIZATION =====
        Sbox partFourSbox = new Sbox(l);

        int[] p4init = {0x8, 0x4, 0x2, 0x1, 0xC, 0x6, 0x3, 0xD, 0xA, 0x5, 0xE,
                0x7, 0xF, 0xB, 0x9, 0x0};

        partFourSbox.init(p4init);

        // ===== 4a =====
        int[][] NL_values = new int[16][16];

        int y;
        int cursum = 0;
        for (int a = 0; a < 16; a++) {
            for (int b = 0; b < 16; b++) {
                cursum = 0;
                for (int x = 0; x < 16; x++) {
                    y = partFourSbox.substitute(x);
                    int curVal = BinUtil.xorAllBits(a, x, 4) ^
                            BinUtil.xorAllBits(b, y, 4);
                    if (curVal == 0) {
                        cursum++;
                    }
                }
                NL_values[a][b] = cursum;
            }
        }

        System.out.println("===== 4a =====");
        System.out.println(Arrays.deepToString(NL_values).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));


        // ===== 4c =====
        System.out.println("===== 4c =====");
        String firstKey = "93E026DE";
        String firstKeyBin = BinUtil.hexToBinStr(firstKey);
        String secondKey = "E5D7F82E";
        String secondKeyBin = BinUtil.hexToBinStr(secondKey);

        KeyScheduler firstKS = new KeyScheduler(firstKeyBin);
        KeyScheduler secondKS = new KeyScheduler(secondKeyBin);

        // doing this for the first key
        subkeys = new Key[5];
        for (int i = 0; i < 5; i++) {
            subkeys[i] = new Key(firstKS.getKey(i + 1));
        }

        rounds = new Round[5];
        for (int i = 0; i < 5; i++) {
            rounds[i] = new Round(subkeys[i], partFourSbox, pm, l);
        }
        rounds[3].setRoundType(1);
        rounds[4].setRoundType(2);

        spn.setRounds(rounds);

        int zeroCount = 0;
        for (int i = 0; i < 65536; i++) {
            String curPlaintext = BinUtil.intToBinStr(i, 16);
            String throwaway = spn.encrypt(curPlaintext);
            int fst = rounds[0].getW().charAt(15) == '1' ? 1 : 0;
            int snd = rounds[3].getU().charAt(0) == '1' ? 1 : 0;
            int thr = rounds[3].getU().charAt(8) == '1' ? 1 : 0;
            if ((fst ^ snd ^ thr) == 0) {
                zeroCount++;
            }
        }

        System.out.printf("[key: %s] number of plaintexts where T = " +
                "0: %d\n", firstKey, zeroCount);

        // doing this for the second key
        subkeys = new Key[5];
        for (int i = 0; i < 5; i++) {
            subkeys[i] = new Key(secondKS.getKey(i + 1));
        }

        rounds = new Round[5];
        for (int i = 0; i < 5; i++) {
            rounds[i] = new Round(subkeys[i], partFourSbox, pm, l);
        }

        rounds[3].setRoundType(1);
        rounds[4].setRoundType(2);

        spn.setRounds(rounds);

        zeroCount = 0;
        for (int i = 0; i < 65536; i++) {
            String curPlaintext = BinUtil.intToBinStr(i, 16);
            String throwaway = spn.encrypt(curPlaintext);
            int fst = rounds[0].getW().charAt(15) == '1' ? 1 : 0;
            int snd = rounds[3].getU().charAt(0) == '1' ? 1 : 0;
            int thr = rounds[3].getU().charAt(8) == '1' ? 1 : 0;
            if ((fst ^ snd ^ thr) == 0) {
                zeroCount++;
            }
        }

        System.out.printf("[key: %s] number of plaintexts where T = " +
                "0: %d\n", secondKey, zeroCount);

        // ===== EC =====

    }
}

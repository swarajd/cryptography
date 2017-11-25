package hw10;


public class FullDES {


    private static int[] pc1 = {57, 49, 41, 33, 25, 17, 9, 1, 58,
            50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52,
            44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14,
            6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};

    private static int[] pc2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
            32};

    private static int[] shift_amts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2,
            2, 2, 1};

    private static int[] message_perm = {58, 50, 42, 34, 26, 18, 10, 2, 60,
            52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56,
            48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35,
            27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31,
            23, 15, 7};

    private static int[] expansion = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9,
            10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20,
            21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

    private static int[][][] sboxes = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    private static int[] f_perm = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26,
            5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4,
            25};

    private static int[] final_perm = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47,
            15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13,
            53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51,
            19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57,
            25};

    private long key;

    private long[] C_List;
    private long[] D_List;

    private long[] subkey_list;

    public FullDES(long key) {
        this.init(key);
    }


    public FullDES(String key) {
        long lkey = this.StrToLong(key);
        this.init(lkey);
    }

    public void init(long key) {
        this.key = key;
        this.C_List = new long[17];
        this.D_List = new long[17];
        this.subkey_list = new long[16];

        long stage1 = this.key;
        long stage2 = this.permute_pc1(stage1);
        this.split(stage2);
        this.generate_pairs();
        this.generate_subkeys();
    }

    private long StrToLong(String s) {
        long accum = 0;
        int len = s.length();
        for (int i = len - 1; i >= 0; i--) {
            long cur = s.charAt(i) == '1' ? 1 : 0;
            int shift = ((len - 1) - i);
            accum += (cur << shift);
        }
        return accum;
    }

    private long permute_pc1(long n) {
        long result = 0;
        for (int i = 0; i < pc1.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (64 - pc1[i]));
            relevant_bit >>>= (64 - pc1[i]);
            relevant_bit <<= pc1.length - i - 1;
            result |= relevant_bit;
        }
        return result;
    }

    private void split(long n) {
        long mask = 0b1111111111111111111111111111;
        long c = (n & (mask << 28)) >>> 28;
        long d = n & mask;
        this.C_List[0] = c;
        this.D_List[0] = d;
    }

    private void generate_pairs() {
        long mask = 0b1111111111111111111111111111;
        for (int i = 1; i < 17; i++) {
            this.C_List[i] = rotate(this.C_List[i - 1], shift_amts[i - 1],
                    28) & mask;
            this.D_List[i] = rotate(this.D_List[i - 1], shift_amts[i - 1],
                    28) & mask;
        }
    }

    private long rotate(long n, int k, int nb) {
        return (n << k) | (n >>> (nb - k));
    }

    private void generate_subkeys() {
        for (int i = 0; i < 16; i++) {
            long cd = (this.C_List[i + 1] << 28) | (this.D_List[i + 1]);
            this.subkey_list[i] = this.permute_pc2(cd);
        }
    }

    private long permute_pc2(long n) {
        long result = 0;
        for (int i = 0; i < pc2.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (56 - pc2[i]));
            relevant_bit >>>= (56 - pc2[i]);
            relevant_bit <<= pc2.length - i - 1;
            result |= relevant_bit;
        }
        return result;
    }

    public long encrypt(String message) {
        long n = this.StrToLong(message);
        return this.encrypt(n);
    }

    public long encrypt(long message) {
        long stage1 = message;
        long stage2 = this.permute_message(message);

        long[] L_List = new long[17];
        long[] R_List = new long[17];

        long mask = 0b11111111111111111111111111111111L;

        L_List[0] = (stage2 & (mask << 32)) >>> 32;
        R_List[0] = stage2 & mask;

        System.out.printf("L0: %s\nR0: %s\n", Long.toBinaryString(L_List[0]),
                Long.toBinaryString(R_List[0]));

        for (int i = 1; i <= 16; i++) {
            L_List[i] = R_List[i - 1];
            R_List[i] = L_List[i - 1] ^ f(R_List[i - 1], subkey_list[i - 1]);

            System.out.printf("L%d: %s\nR%d: %s\n", i, Long.toBinaryString
                            (L_List[i]), i,
                    Long.toBinaryString(R_List[i]));
        }

        System.out.println(Long.toBinaryString(L_List[16]));
        System.out.println(Long.toBinaryString(R_List[16]));

        long RL = (R_List[16] << 32) | L_List[16];
        System.out.println(Long.toBinaryString(RL));

        long final_ = this.final_permute(RL);

        return final_;
    }

    private long permute_message(long n) {
        long result = 0;
        for (int i = 0; i < message_perm.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (64 - message_perm[i]));
            relevant_bit >>>= (64 - message_perm[i]);
            relevant_bit <<= message_perm.length - i - 1;
            result |= relevant_bit;
        }
        return result;
    }

    private long f(long Rn, long Kn) {
        String ck = BinUtil.leftPad(Long.toBinaryString(Kn), 48, '0');
        System.out.printf("current key: %s\n", ck);
        long ERn = this.expand(Rn);
        String expar = BinUtil.leftPad(Long.toBinaryString(ERn), 48, '0');
        System.out.printf("expanded R : %s\n", expar);
        long xored = Kn ^ ERn;
        String xred = BinUtil.leftPad(Long.toBinaryString(xored), 48, '0');
        System.out.printf("xored      : %s\n", xred);
        long mask = 0b111111;
        int row_mask_left = 0b100000;
        int row_mask_right = 0b1;
        int col_mask = 0b011110;
        long partial = 0;
        for (int i = 0; i < 8; i++) {
            int input_shift = ((8 - i - 1) * 6);
            int ith_group = (int)((xored & (mask << input_shift)) >>>
                    input_shift);
            int row = ((ith_group & row_mask_left) >>> 4) |
                    (ith_group & row_mask_right);
            int col = (ith_group & col_mask) >>> 1;
            int cur_num = sboxes[i][row][col];
            String curSt = BinUtil.leftPad(Integer.toBinaryString(cur_num),
                    4, '0');
            System.out.printf("group: %d, row: %d, col: %d, cur: %s\n",
                    ith_group, row, col, curSt);
            int output_shift = (8 - i - 1) * 4;
            partial |= (cur_num << output_shift);
        }

        String part = BinUtil.leftPad(Long.toBinaryString(partial), 32, '0');
        System.out.printf("sboxed: %s\n", part);

        long result = this.f_permute(partial);

        String res_ = BinUtil.leftPad(Long.toBinaryString(result), 32, '0');
        System.out.printf("result: %s\n", res_);

        return result;
    }

    private long expand(long n) {
        long result = 0;
        for (int i = 0; i < expansion.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (32 - expansion[i]));
            relevant_bit >>>= (32 - expansion[i]);
            relevant_bit <<= expansion.length - i - 1;

            result |= relevant_bit;
        }
        return result;
    }

    private long f_permute(long n) {
        long result = 0;
        for (int i = 0; i < f_perm.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (32 - f_perm[i]));
            relevant_bit >>>= (32 - f_perm[i]);
            relevant_bit <<= f_perm.length - i - 1;
            result |= relevant_bit;
        }
        return result;
    }

    private long final_permute(long n) {
        long result = 0;
        for (int i = 0; i < final_perm.length; i++) {
            /*
            plan:
                - get relevant bit of n
                - normalize it
                - shift it to i
                - or it with result
             */
            long relevant_bit = n & (1l << (64 - final_perm[i]));
            relevant_bit >>>= (64 - final_perm[i]);
            relevant_bit <<= final_perm.length - i - 1;
            result |= relevant_bit;
        }
        return result;
    }

}

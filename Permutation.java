package hw10;

/**
 * Created by swarajd on 10/26/17.
 */
public class Permutation {
    private int[] permutation;
    private int[] inverse;

    public Permutation(int[] perm) {
        int plen = perm.length;
        this.inverse = new int[plen];
        for (int i = 0; i < plen; i++) {
            perm[i] = perm[i] - 1;
            this.inverse[perm[i]] = i;
        }
        this.permutation = perm;
    }

    public int[] permuteArray(int[] bits) {
        int blen = bits.length;
        int[] result = new int[blen];
        for (int i = 0; i < blen; i++) {
            result[i] = bits[this.permutation[i]];
        }
        return result;
    }

    public int[] inverseArray(int[] bits) {
        int blen = bits.length;
        int[] result = new int[blen];
        for (int i = 0; i < blen; i++) {
            result[i] = bits[this.inverse[i]];
        }
        return result;
    }
}

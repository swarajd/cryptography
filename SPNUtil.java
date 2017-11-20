package hw10;

import java.util.ArrayList;

/**
 * Created by swarajd on 10/26/17.
 */
public class SPNUtil {
    public static int[] StringToArr(String st) {
        int stlen = st.length();
        int[] result = new int[stlen];
        for (int i = 0; i < stlen; i++) {
            result[i] = st.charAt(i) - '0'; //convert char to int
        }
        return result;
    }

    public static String ArrToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static ArrayList<String> splitString(String str, int l) {

        int nparts = str.length()/l;

        ArrayList<String> substrings = new ArrayList<>();
        for (int i = 0; i < nparts; i++) {
            String chunk = str.substring(i*nparts, i*nparts + l);
            substrings.add(chunk);
        }
        return substrings;
    }

    public static String mergeSubstrings(ArrayList<String> arr) {
        StringBuilder sb = new StringBuilder();
        for (String s: arr) {
            sb.append(s);
        }
        return sb.toString();
    }
}

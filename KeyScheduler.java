package hw10;

/**
 * Created by swarajd on 10/26/17.
 */
public class KeyScheduler {
    private String key;
    public KeyScheduler(String key) {
        this.key = key;
    }

    public String getKey(int i) {
        int offset = 4 * i - 4;
        return this.key.substring(offset, offset + 16);
    }
}

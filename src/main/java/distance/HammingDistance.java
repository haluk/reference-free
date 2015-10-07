package distance;

/**
 * Created by hd on 10/6/15.
 */
public class HammingDistance implements EditDistance {

    @Override
    public int getDistance(String s1, String s2) {
        // sanity check
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            throw new IllegalArgumentException();
        }

        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }
}

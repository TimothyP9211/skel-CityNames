package citynetwork;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static boolean isValidCode(String _cityName, String _cityCode) {
        int index = 0;
        char[] cityName = _cityName.toCharArray();
        char[] cityCode = _cityCode.toCharArray();
        if (_cityCode.contains(" ")) {
            return false;
        }
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < cityName.length; i++) {
            if (index >= 3) {
                break;
            }
            if (cityName[i] == cityCode[index]) {
                seen.add(i);
                index++;
            }
        }
        return seen.size() == 3;
    }

}

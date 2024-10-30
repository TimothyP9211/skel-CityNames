package citynetwork;

import java.util.*;

public class CityNetwork {
    public final List<CityTLA> _cities = new ArrayList<>();

    public boolean checkRep() {
        for (int i = 0; i < _cities.size(); i++) {
            CityTLA city1 = _cities.get(i);
            if (!city1.checkRep()) {
                return false;
            }
            for (int j = 0; j < _cities.size(); j++) {
                if (i != j ) {
                    CityTLA city2 = _cities.get(j);
                    if (Utils.isValidCode(city2._cityName, city1._cityCode)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param cityNames is not null and only contains strings with upper-case alphabets and spaces
     * @return a {@code CityNetwork} instance with the maximum number of cities from {@code cityNames} that can have ambiguity-free TLAs
     */
    public static final CityNetwork buildNetwork(List<String> cityNames) {
        CityNetwork network = new CityNetwork();
        Set<String> allCodes = new HashSet<>();
        Set<String> citesAdded = new HashSet<>();

        for (String city1 : cityNames) {
            Set<String> city1Codes = getAllCodes(city1);
            for (String code : city1Codes) {
                boolean unique = true;
                if (!allCodes.contains(code)) {
                    for (String city2 : cityNames) {
                        if (Utils.isValidCode(city2, code) && !city1.equals(city2)) {
                            unique = false;
                        }
                    }
                    if (unique && !citesAdded.contains(city1)) {
                        allCodes.add(code);
                        citesAdded.add(city1);
                        network._cities.add(new CityTLA(city1, code));
                        break;
                    }
                }
            }
        }

        if (network._cities.isEmpty()) {
            for (String city : cityNames) {
                Set<String> cityCodes = getAllCodes(city);
                for (String code : cityCodes) {
                    if (Utils.isValidCode(city, code)) {
                        network._cities.add(new CityTLA(city, code));
                        return network;
                    }
                }
            }
        }
        return network;
    }

    private static Set<String> getAllCodes(String city) {
        Set<String> validCodes = new HashSet<>();
        Set<String> allCodes = new HashSet<>();
        List<Character> possibleChars = new ArrayList<>();
        char[] chars = city.toCharArray();
        for (char c : chars) {
            possibleChars.add(c);
        }
        for (char c : possibleChars) {
            findCodes(allCodes, "", String.valueOf(c), possibleChars);
        }

        for (String code : allCodes) {
            if (Utils.isValidCode(city, code)) {
                validCodes.add(code);
            }
        }
        return validCodes;
    }

    private static void findCodes(Set<String> codes, String current, String next, List<Character> bucket) {
        if (current.length() == 3) {
            codes.add(current);
        } else {
            current = current + next;
            for (Character c : bucket) {
                findCodes(codes, current, c.toString(), bucket);
            }
        }
    }
}

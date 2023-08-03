package org.dddml.aptosdemocontracts.specialization;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean collectionContainsIgnoringCase(Collection<String> collection, String val) {
        if (collection.contains(val)) {
            return true;
        }
        for (String f : collection) {
            if (f == val) {
                return true;
            }
            if (f == null) {
                return false;
            }
            if (f.equalsIgnoreCase(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean mapContainsKeyIgnoringCase(Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            return true;
        }
        return collectionContainsIgnoringCase(map.keySet(), key);
    }

    public static String mapGetValueIgnoringCase(java.util.Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        for (String f : map.keySet()) {
            if (equalsIgnoreCase(f, key)) {
                return map.get(f);
            }
        }
        return null;
    }

    private static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

}

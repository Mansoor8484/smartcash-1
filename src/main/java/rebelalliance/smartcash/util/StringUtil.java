package rebelalliance.smartcash.util;

import java.util.Map;
import java.util.Objects;

public class StringUtil {
    public String csvFromKeys(Map<Object, Object> hashMap) {
        return String.join(",", hashMap.keySet().stream().map(Objects::toString).toArray(String[]::new));
    }
}

package kr.nexg.esm.util;

import java.util.Map;

public class config {

    public static Object setValue(Map<String, Object> vars, String field, Object defaultVal) {
        if (vars.containsKey(field)) {
            int strLength = 0;
            try {
                strLength = ((String) vars.get(field)).length();
            } catch (Exception e) {
                strLength = String.valueOf(vars.get(field)).length();
            }

            if (strLength > 0) {
                return vars.get(field);
            } else {
                return defaultVal;
            }
        } else {
            return defaultVal;
        }
    }
    
    public static Object listToString(Object[] object) {
        int cnt = 0;
        StringBuilder result = new StringBuilder();
        
        for (Object el : object) {
            if (cnt == 0) {
                result.append(el);
            } else {
                result.append(",").append(el);
            }
            cnt++;
        }

        return result.toString();
    }
}

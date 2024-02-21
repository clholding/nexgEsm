package kr.nexg.esm.util;

import java.util.HashMap;
import java.util.Map;

public class mode_convert {

    public static int convert_modedata(String modeChar) {
        Map<String, String> modeDic = new HashMap<>();
        modeDic.put("ALL", "0");
        modeDic.put("FW", "1");
        modeDic.put("VForce", "2");
        modeDic.put("SW", "3");
        modeDic.put("M2MG", "4");

        if (!modeDic.containsKey(modeChar)) {
            return 0;
        } else {
            return Integer.parseInt(modeDic.get(modeChar));
        }
    }
}

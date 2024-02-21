import java.util.Map;

public class YourClass {

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

    public static void main(String[] args) {
        // 예시로 사용할 맵을 생성하고 테스트
        Map<String, Object> exampleMap = Map.of("field1", "value1", "field2", "");
        String result = (String) setValue(exampleMap, "field1", "default");
        System.out.println(result);  // 출력: value1

        result = (String) setValue(exampleMap, "field2", "default");
        System.out.println(result);  // 출력: default

        result = (String) setValue(exampleMap, "nonexistentField", "default");
        System.out.println(result);  // 출력: default
    }
}
package kr.nexg.esm.common.util;

public class EnumUtil {

	public static String blank(String text) {
		String str = "";
		if("".equals(text)) {
			str = "_";
		}else {
			str = text;
		}
		return str;
	}

}

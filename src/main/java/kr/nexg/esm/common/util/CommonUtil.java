package kr.nexg.esm.common.util;

public class CommonUtil {
	public static String setEmptyString(String str) {
	    return !"null".equals(str) ? str : "";
	}
}

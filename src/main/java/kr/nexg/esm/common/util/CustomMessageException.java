package kr.nexg.esm.common.util;

public class CustomMessageException extends RuntimeException{
	public CustomMessageException(String message) {
        super(message); // RuntimeException 클래스의 생성자를 호출
    }
}

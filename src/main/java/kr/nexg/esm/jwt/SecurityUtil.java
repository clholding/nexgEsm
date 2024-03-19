package kr.nexg.esm.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        return authentication.getName();
    }
    public static String getRole() {
    	
    	final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication == null || authentication.getAuthorities().toString() == null) {
    		throw new RuntimeException("No authentication information.");
    	}
    	
    	return authentication.getAuthorities().toString().replace("[", "").replace("]", "").replace("ROLE_", "");
    }
}

package kr.nexg.esm.jwt.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthVo implements UserDetails {
 
	private static final long serialVersionUID = -1212636375024155365L;
	
	private int id;
    private String login;
    private String pwd;
    private String oldPwd;
    private String newPwd;
    private String name;
    private String desc;
    private String email;
    private String active;
    private String groupId;
    private String deviceId;
    private int failcount;
    private String pwdExpireDate;
    private String ldate;
    private String allowIp1;
    private String allowIp2;
    private String status;
    private String hbtime;
    private String defMode;
    private String alarm;
    private String popupTime;
    private int role1;
    private int loginStatus;
    
    private int maxFailCount;
    private int blockingTime;
    private String adminFailAction;
    private String curTime;
    
    private String forcedLogin;
    
    private String refreshToken;
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(groupId));
        return auth;
    }
    
    @Override
    public String getUsername() {
    	return login;
    }
 
    @Override
    public String getPassword() {
        return pwd;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public void setFailcount(int failcount) {
		this.failcount = failcount;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}
    
	
	
    
}
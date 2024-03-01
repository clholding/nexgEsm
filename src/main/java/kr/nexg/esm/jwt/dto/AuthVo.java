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
    private String name;
    private String desc;
    private String email;
    private String defMode;
    private String sessionTime;
    private String alarm;
    private String popupTime;
    private String active;
    private String cdate;
    private String udate;
    private String ldate;
    private String loginExpireDate;
    private String pwdExpireDate;
    private String loginActiveLifetime;
    private String pwdExpireCycle;
    private String failcount;
    private String groupId;
    private String ip1;
    private String ip2;
    private String deviceState;
    private String recentFailDevice;
    private String resourceTop5;
    private String weekLogStats;
    private String weekFailState;
    private String deviceSort;
    private String deviceOrder;
    private String status;
    private String logout;
    private String hbtime;
    private String monitoringIntergrated;
    private String allowIp1;
    private String allowIp2;
 
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
}
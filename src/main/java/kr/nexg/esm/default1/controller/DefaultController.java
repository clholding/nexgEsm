package kr.nexg.esm.default1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import kr.nexg.esm.jwt.service.AuthService;

@RestController
@RequestMapping("/default")
public class DefaultController {

	@Autowired
	AuthService authService;
	
	@Autowired
    JwtTokenProvider jwtTokenProvider;
    
	@PostMapping("/logincheck")
	public TokenVo logincheck(@RequestBody AuthVo authVo) {

		TokenVo tokenVo = authService.logincheck(authVo);
		
		return tokenVo;
	}
	
	@PostMapping("/refresh")
	public TokenVo refresh(@RequestBody AuthVo authVo) {
		
		TokenVo tokenVo = null;
		if(jwtTokenProvider.validateToken(authVo.getRefreshToken())) {
			
			Authentication authentication = jwtTokenProvider.getAuthentication(authVo.getRefreshToken());
			tokenVo = jwtTokenProvider.generateToken(authentication);
		}
		
		return tokenVo;
	}
	
}

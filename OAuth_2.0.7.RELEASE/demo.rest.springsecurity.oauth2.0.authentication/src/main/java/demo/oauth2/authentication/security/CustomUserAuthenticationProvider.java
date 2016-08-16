package demo.oauth2.authentication.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Object principal = authentication.getPrincipal();
		Object credentials = authentication.getCredentials();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		if(principal.equals("user")&& principal.equals("user")){
			CustomUserPasswordAuthenticationToken auth = new CustomUserPasswordAuthenticationToken(principal, credentials,grantedAuthorities);
			return auth;
		}
		else if(principal.equals("admin") && principal.equals("admin")){
			CustomUserPasswordAuthenticationToken auth = new CustomUserPasswordAuthenticationToken(principal,credentials,grantedAuthorities);
			return auth;
		}
		else if(principal.equals("user1") && principal.equals("user1")){
			CustomUserPasswordAuthenticationToken auth = new CustomUserPasswordAuthenticationToken(principal, credentials,grantedAuthorities);
			return auth;
		}
		else{
			throw new BadCredentialsException("Bad User Credentials.");
		}
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}
}

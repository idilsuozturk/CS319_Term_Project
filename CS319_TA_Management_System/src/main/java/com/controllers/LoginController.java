package com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/frontend/adminpanel/dist/pages")
public class LoginController {

    private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


    
    /*
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
		// ...
	}*/


   /* @PostMapping("/login")
public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
    try {
        // Giriş bilgilerini taşıyan authentication objesi
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.username(), loginRequest.password());

        // Giriş bilgilerini doğrulama (UserDetailsService devreye girer)
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        // Doğrulanan kullanıcıyı SecurityContext'e koy (session tabanlı sistem için şart)
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // 200 OK yanıtı dön
        return ResponseEntity.ok().build();
    } catch (AuthenticationException ex) {
        // Hatalı girişlerde 401 Unauthorized dön
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    
}


	public record LoginRequest(String username, String password) {
	}
}*/

}

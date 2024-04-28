package propensi.myjisc.user.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import propensi.myjisc.user.dto.AuthRequestDTO;
import propensi.myjisc.user.dto.AuthResponseDTO;
import propensi.myjisc.user.dto.JWTResponseDTO;
import propensi.myjisc.user.dto.RegisterRequest;
import propensi.myjisc.user.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }
    
    @PostMapping("/Register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    // @PostMapping("/logout")
    // public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     if (auth != null) {
    //         new SecurityContextLogoutHandler().logout(request, response, auth);
    //     }
    //     SecurityContextHolder.clearContext();
    //     return ResponseEntity.ok().build();
    // }
    

}

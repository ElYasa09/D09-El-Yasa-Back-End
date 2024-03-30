package propensi.myjisc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import propensi.myjisc.user.dto.AuthRequestDTO;
import propensi.myjisc.user.dto.AuthResponseDTO;
import propensi.myjisc.user.dto.RegisterRequest;
import propensi.myjisc.user.model.Role;
import propensi.myjisc.user.model.User;
import propensi.myjisc.user.repository.UserRepository;

@Service
// @RequiredArgsConstructor
public class AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    
    public AuthResponseDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .username(request.getUsername())
        .email(request.getEmail())
        .password(encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
            .token(jwtToken)
            .build();
        
        
    }


    public AuthResponseDTO authenticate(AuthRequestDTO request) {
         authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
            return AuthResponseDTO.builder()
            .token(jwtToken)
            .build();
    }

    public String encode(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

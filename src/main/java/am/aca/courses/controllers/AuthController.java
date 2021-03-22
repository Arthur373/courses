package am.aca.courses.controllers;

import am.aca.courses.entity.UserEntity;
import am.aca.courses.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Rest Controller for Authenticate users.
 *
 * @author Arthur
 * @version 1.0
 */
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserEntity userEntity) {
        Objects.requireNonNull(userEntity.getEmail());
        Objects.requireNonNull(userEntity.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword()));
            String token = jwtTokenProvider.generateToken(userEntity.getEmail());
            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }
}

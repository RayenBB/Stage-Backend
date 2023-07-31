package com.stage.security.auth;

import com.stage.security.config.JwtService;
import com.stage.security.user.User;
import com.stage.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
            .nomentreprise(request.getNomentreprise())
            .numinscription(request.getNuminscription())
            .numidentifiant(request.getNumidentifiant())
            .typesoumissionnaire(request.getTypesoumissionnaire())
            .pays(request.getPays())
            .typedemarche(request.getTypedemarche())
            .numtel(request.getNumtel())
            .numfax(request.getNumfax())
            .emailsociete(request.getEmailsociete())
            .nom(request.getNom())
            .titre(request.getTitre())
            .telephone(request.getTelephone())
            .numerodebureau(request.getNumerodebureau())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}

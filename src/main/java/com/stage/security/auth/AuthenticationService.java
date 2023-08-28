package com.stage.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.security.config.JwtService;
import com.stage.security.token.Token;
import com.stage.security.token.TokenRepository;
import com.stage.security.token.TokenType;
import com.stage.security.user.User;
import com.stage.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
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
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
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
    var refreshToken = jwtService.generateRefreshToken(user);

    System.out.println(user.getRole());
    System.out.println(jwtToken);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    List<String> roles = user.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .username(user.getNom())
            .roles(roles)
        .build();
  }
  public AuthenticationResponse updateUser(RegisterRequest request) {
    String email = request.getEmail();
    User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    // Update user information based on the request
    user.setNomentreprise(request.getNomentreprise());
    user.setNuminscription(request.getNuminscription());
    user.setNumidentifiant(request.getNumidentifiant());
    user.setTypesoumissionnaire(request.getTypesoumissionnaire());
    user.setPays(request.getPays());
    user.setTypedemarche(request.getTypedemarche());
    user.setNumtel(request.getNumtel());
    user.setNumfax(request.getNumfax());
    user.setEmailsociete(request.getEmailsociete());
    user.setNom(request.getNom());
    user.setTitre(request.getTitre());
    user.setTelephone(request.getTelephone());
    user.setNumerodebureau(request.getNumerodebureau());

    // Update other fields

    repository.save(user);

    // You can return an appropriate response here
    // For example, a success message or a new AuthenticationResponse



    // Generate token and create response
    String jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .username(user.getNom())

            .build();
  }
  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getNom())
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}

package com.stage.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stage.security.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String nomentreprise;
  private String numinscription;
  private String numidentifiant;
  private String typesoumissionnaire;
  private String pays;
  private String typedemarche;
  private String numtel ;
  private String numfax;
  private String emailsociete;

  private String nom;
  private String titre;
  private String telephone;
  private String numerodebureau;

  @Column(name = "email", unique = true, length = 191, nullable=false )

  @NotEmpty



  private String email;
  @Column(nullable = false)
  @NotEmpty
  @NotBlank
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
  @OneToMany(mappedBy = "user"  ,cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Token> tokens;

  private Boolean locked=false;
  private Boolean enabled=false;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
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

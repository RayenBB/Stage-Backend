package com.stage.security.auth;

import com.stage.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


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
  private String email;
  private String password;
  private Role role;
}

package com.stage.security.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  @Transactional
  @Modifying
  @Query("UPDATE User a " +
          "SET a.enabled = TRUE WHERE a.email = ?1")
  int enableAppUser(String email);
  @Query(value = "select enabled from user " +
          "where email = ?1",nativeQuery = true)
  Boolean findByEnabled(String email);
  @Transactional
  @Modifying
  @Query(value = "delete from  user  " +
          "WHERE id = ?1",nativeQuery = true)
  Integer deleteAppUser(Integer id);



}

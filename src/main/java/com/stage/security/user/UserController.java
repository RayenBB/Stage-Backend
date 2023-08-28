package com.stage.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController {
    @Autowired
private UserService UserService;
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public String get() {
        return "GET:: admin controller";
    }

   // @PreAuthorize("hasAuthority('admin:read')")
   @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers () {
        List<User> Users = UserService.findAllUsers();
        return new ResponseEntity<>(Users, HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<User> getUserById (@PathVariable("id") Integer id) {
        User User = UserService.findUserById(id);
        return new ResponseEntity<>(User, HttpStatus.OK);
    }
    @GetMapping("/findbymail/{mail}")
    public ResponseEntity<User> getUserBymail (@PathVariable("mail") String mail) {
        User User = UserService.findUserBymail(mail);
        return new ResponseEntity<>(User, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User User) {
        User newUser = UserService.addUser(User);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User User) {
        User updateUser = UserService.updateUser(User);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        UserService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

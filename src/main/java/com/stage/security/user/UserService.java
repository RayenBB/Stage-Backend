package com.stage.security.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userrepository;
    public User addUser(@NotNull User user) {
        return userrepository.save(user);
    }

    public List<User> findAllUsers() {
        return userrepository.findAll();
    }

    public User updateUser(User User) {
        return userrepository.save(User);
    }

    public User findUserById(Integer id) {
        return userrepository.findById(id)
                .orElse(null);
    }
    public User findUserBymail(String mail) {
        return userrepository.findByEmail(mail)
                .orElse(null);
    }

    public void deleteUser(Integer id){
        userrepository.deleteById(id);
    }
}

package com.codecool.bruteforce.authentication;

import com.codecool.bruteforce.users.model.User;
import com.codecool.bruteforce.users.repository.UserRepository;

import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository repository;

    public AuthenticationServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public boolean authenticate(String userName, String password) {
        List<User> allUsers = repository.getAll();
        for(User user : allUsers){
            if(user.userName().equals(userName) && user.password().equals(password)){
                return true;
            }
        }
        return false;
    }
}

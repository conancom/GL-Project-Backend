package com.projectgl.backend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    final public UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findUserByUsername(String username){
        List<User> users = userRepository.findByUserame(username);

    }

    public void findUserByEmail(String Email){

    }

    public void userExists(User user){
        findUserByEmail(uses);

    }
}

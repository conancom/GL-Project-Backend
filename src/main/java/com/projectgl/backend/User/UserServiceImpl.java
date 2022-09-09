package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    final public UserRepository userRepository;
    final public EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    public void findUserByUsername(String username){
        List<User> users = userRepository.findByUsername(username);

    }

    public void findUserByEmail(String Email){

    }

    public boolean userExists(RegisterDto registerDto){
        TypedQuery<User> query = entityManager.createQuery("SELECT new com.projectgl.backend.User.User(u.username) FROM User u WHERE u.username = :username",
        User.class);
        return !query.setParameter("username", registerDto.getUsername()).getResultList().isEmpty();
    }

    public RegisterResponse createUser(RegisterDto registerDto){
        User user = new User(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword());
        userRepository.save(user);
        return RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build();
    }
}

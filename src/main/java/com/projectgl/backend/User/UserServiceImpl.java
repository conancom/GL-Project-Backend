package com.projectgl.backend.User;

import com.projectgl.backend.Dto.RegisterDto;
import com.projectgl.backend.Response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
public class UserServiceImpl implements UserService {

    final public UserRepository userRepository;
    final public EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    public boolean userExistsUsername(RegisterDto registerDto) {
        return !entityManager.createQuery("SELECT u.username FROM User u WHERE u.username = :username", User.class).setParameter("username", registerDto.getUsername()).getResultList().isEmpty();
    }

    public boolean userExistsEmail(RegisterDto registerDto) {
        return !entityManager.createQuery("SELECT u.username FROM User u WHERE u.email = :email", User.class).setParameter("email", registerDto.getEmail()).getResultList().isEmpty();
    }

    public RegisterResponse createUser(RegisterDto registerDto) {
        User user = new User(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword());
        userRepository.save(user);
        return RegisterResponse.builder().status(RegisterResponse.Status.SUCCESS).username(registerDto.getUsername()).build();
    }
}

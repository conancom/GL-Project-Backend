package com.projectgl.backend.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUserame(String username);

    public List<User> findByEmail(String email);

}

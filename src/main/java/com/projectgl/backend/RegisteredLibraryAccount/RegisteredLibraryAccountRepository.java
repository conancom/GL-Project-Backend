package com.projectgl.backend.RegisteredLibraryAccount;

import com.projectgl.backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface RegisteredLibraryAccountRepository extends JpaRepository<RegisteredLibraryAccount, Long> {
    ArrayList<RegisteredLibraryAccount> getRegisteredLibraryAccountsByUser(User user);

}

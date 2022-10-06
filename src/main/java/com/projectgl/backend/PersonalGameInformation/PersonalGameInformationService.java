package com.projectgl.backend.PersonalGameInformation;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import com.projectgl.backend.User.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface PersonalGameInformationService {
    ArrayList<PersonalGameInformation> findPersonalGameInformationByRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount);

}

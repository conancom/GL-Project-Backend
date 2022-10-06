package com.projectgl.backend.PersonalGameInformation;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonalGameInformationServiceImpl implements PersonalGameInformationService {

    final PersonalGameInformationRepository personalGameInformationRepository;

    @Autowired
    public PersonalGameInformationServiceImpl(PersonalGameInformationRepository personalGameInformationRepository) {
        this.personalGameInformationRepository = personalGameInformationRepository;
    }

    public ArrayList<PersonalGameInformation> findPersonalGameInformationByRegisteredLibraryAccount(RegisteredLibraryAccount registeredLibraryAccount) {
        return personalGameInformationRepository.getPersonalGameInformationByRegisteredLibraryAccount(registeredLibraryAccount);
    }
}

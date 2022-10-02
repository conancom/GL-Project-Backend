package com.projectgl.backend.Dto;

import com.projectgl.backend.RegisteredLibraryAccount.RegisteredLibraryAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
@Getter
public class LibraryAccountDetails {

    private RegisteredLibraryAccount.AccountType libraryName;

    private Long library_id;

    private ArrayList<GameDetail> gameDetails;

}

package com.projectgl.backend.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Setter
@Getter
public class LibraryAccountDetails {

    private String library_name;

    private Long library_id;

    private ArrayList<GameDetail> gameDetails;

}

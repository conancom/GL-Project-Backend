package com.projectgl.backend.PersonalGameInformation;

import javax.persistence.*;

@Entity
@Table(name = "personalgameinformation")
public class PersonalGameInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

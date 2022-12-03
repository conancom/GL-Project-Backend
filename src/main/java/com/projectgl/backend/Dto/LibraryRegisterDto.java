package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryRegisterDto {

    private String session_id;

    private String library_type;

    private String library_api_key;

}

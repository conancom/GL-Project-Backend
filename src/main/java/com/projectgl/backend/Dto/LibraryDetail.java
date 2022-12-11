package com.projectgl.backend.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LibraryDetail {

    private String library_type;

    private long library_id;

}

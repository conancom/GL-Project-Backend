package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GogTokenResponse {

    private int expires_in;

    private String scope;

    private String token_type;

    private String access_token;

    private String user_id;

    private String refresh_token;

    private String session_id;
}

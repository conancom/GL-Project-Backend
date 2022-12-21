package com.projectgl.backend.Response;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IgdbAuthResponse {

    private String access_token;

    private int expires_in;

    private String token_type;
}

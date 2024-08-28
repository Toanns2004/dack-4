package org.example.projectapi.dto.response;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private Long userId;
}

package likelion.market.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JwtResponseDto {

    private String jwt;
    private String username;
}

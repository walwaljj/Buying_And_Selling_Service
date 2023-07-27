package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude
@Getter
@Builder
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}

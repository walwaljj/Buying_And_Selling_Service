package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.UserEntity;
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
    public static UserDto fromUserEntity(UserEntity user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}

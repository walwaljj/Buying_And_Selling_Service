package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.OneToMany;
import likelion.market.entity.CommentEntity;
import likelion.market.entity.NegotiationEntity;
import likelion.market.entity.SalesItemEntity;
import likelion.market.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude
@Getter
@Builder
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

//    private List<SalesItemEntity> salesItem;
//    private List<NegotiationEntity> negotiation;
//    private List<CommentEntity> comment;
    public static UserDto fromUserEntity(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}

package likelion.market.service;

import likelion.market.dto.UserDto;
import likelion.market.entity.UserEntity;
import likelion.market.enums.Role;
import likelion.market.repository.UserRepository;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.security.config.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomUserDetailsManager manager;
    private final PasswordEncoderConfig passwordEncoder;

    public UserDto createUser(UserDto userDto) {


        if( manager.userExists(userDto.getUsername()) ){
            throw new IllegalArgumentException( userDto.getUsername() + " 은(는) 존재하는 id 입니다.");
        }

        if( !userDto.getPassword().equals(userDto.getPasswordChk())){
            log.info("password = {} ", userDto.getPassword() );
            log.info("PasswordChk = {} ", userDto.getPasswordChk() );

            throw new IllegalArgumentException( " 비밀번호를 확인해주세요.");
        }


        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber()).build();


        UserEntity user = userRepository.save(UserEntity.builder()
                .username(userDetails.getUsername())
                .password(passwordEncoder.passwordEncoder().encode(userDetails.getPassword()))
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .role(Role.USER).build());

        return UserDto.fromUserEntity(user);
    }

    public UserDetails loginUser(String username, String password){

        UserDetails user = manager.loadUserByUsername(username);

        if(! passwordEncoder.passwordEncoder().matches(password,user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return user;

    }

    public UserDto findById(Integer id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            return UserDto.fromUserEntity(user);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}

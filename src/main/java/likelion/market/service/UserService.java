package likelion.market.service;

import likelion.market.dto.UserDto;
import likelion.market.entity.UserEntity;
import likelion.market.repository.UserRepository;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.security.config.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomUserDetailsManager manager;
    private final PasswordEncoderConfig passwordEncoder;

    public UserDto createUser(UserDetails userDetails) {

        if( manager.userExists(userDetails.getUsername())){
            throw new IllegalArgumentException( userDetails.getUsername() + " 은(는) 존재하는 id 입니다.");
        }

        CustomUserDetails userDto = (CustomUserDetails)  userDetails;
        UserEntity user = userRepository.save(UserEntity.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.passwordEncoder().encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber()).build());

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserDto loginUser(String username, String password){

        CustomUserDetails userByUsername = (CustomUserDetails) manager.loadUserByUsername(username);
        String userPassword = userByUsername.getPassword();

        if(! passwordEncoder.passwordEncoder().matches(password,userPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userRepository.findById(userByUsername.getId()).get();

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }
}

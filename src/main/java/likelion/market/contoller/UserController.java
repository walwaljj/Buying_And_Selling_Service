package likelion.market.contoller;

import likelion.market.security.config.PasswordEncoderConfig;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.dto.UserDto;
import likelion.market.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;
    private final CustomUserDetailsManager manager;
    private final PasswordEncoderConfig passwordEncoder;

    @PostMapping("/sign")
    public UserDto sign( @RequestBody UserDto dto ){

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber()).build();

        UserDto user = service.createUser(userDetails);

        log.info("name = {} ", user.getUsername() );
        log.info("password = {} ", user.getPassword() );
        log.info("Email = {} ", user.getEmail() );
        log.info("PhoneNumber = {} ", user.getPhoneNumber() );

        return user;
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto dto){
        return service.loginUser(dto.getUsername(),dto.getPassword());
    }

}

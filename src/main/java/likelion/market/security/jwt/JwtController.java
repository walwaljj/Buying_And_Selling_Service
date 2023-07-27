package likelion.market.security.jwt;

import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.UserDto;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.security.config.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *  token Controller class
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("token")
public class JwtController {

    private final CustomUserDetailsManager manager;
    private final PasswordEncoderConfig passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    /** 토큰을 생성 합니다.
     *
     * @param dto user 정보
     * @return token 정보를 반환
     */
    @PostMapping("/issue")
    public JwtTokenDto issueJwt(@RequestParam UserDto dto){

        UserDetails userByUsername = manager.loadUserByUsername(dto.getUsername());

        // 비밀번호 일치 하지 않을 경우
        if(!passwordEncoder.passwordEncoder().matches(dto.getPassword(), userByUsername.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 토큰 생성
        JwtTokenDto tokenDto = new JwtTokenDto();
        tokenDto.setToken(jwtTokenUtils.generateToken(userByUsername));
        return tokenDto;
    }
}

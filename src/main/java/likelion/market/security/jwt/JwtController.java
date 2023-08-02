package likelion.market.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.security.config.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  token Controller class
 */
@RequiredArgsConstructor
//@Controller
@RestController
@Slf4j
@RequestMapping("/token")
public class JwtController {

    private final CustomUserDetailsManager manager;
    private final PasswordEncoderConfig passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;



    /** 토큰을 생성 합니다.
     *
     * @param session
     * @return token 정보를 반환
     */
    @GetMapping("/issue")
    public String issueJwt(HttpSession session, HttpServletResponse response){

        CustomUserDetails user = (CustomUserDetails) session.getAttribute("user");

        // 토큰 생성
        JwtTokenDto jwtToken = new JwtTokenDto();
        jwtToken.setToken(jwtTokenUtils.generateToken(user));

        log.info("tokenDto = {} ",jwtToken.getToken());

        return jwtToken.getToken();
//        return "redirect:/home/users";

    }
}

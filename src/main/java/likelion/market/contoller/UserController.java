package likelion.market.contoller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import likelion.market.dto.UserDto;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.security.jwt.JwtTokenDto;
import likelion.market.security.jwt.JwtTokenUtils;
import likelion.market.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final CustomUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    @PostMapping("/sign")
    public UserDto sign(@RequestBody UserDto userDto ){

        UserDto user = service.createUser(userDto);

        log.info("name = {} ", user.getUsername() );
        log.info("password = {} ", user.getPassword() );
        log.info("Email = {} ", user.getEmail() );
        log.info("PhoneNumber = {} ", user.getPhoneNumber() );

//        return "redirect:/login";
        return user;

    }

    @GetMapping("/sign")
    public String signView(@ModelAttribute UserDto userDto, Model model){

        model.addAttribute("userDto", userDto);
        return "sign";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto dto, HttpServletResponse response, HttpSession session){

        CustomUserDetails user = (CustomUserDetails) service.loginUser(dto.getUsername(), dto.getPassword());

        session.setAttribute("user",user);

        log.info("name = {} ", user.getUsername() );
        log.info("password = {} ", user.getPassword() );
        log.info("Email = {} ", user.getEmail() );
        log.info("PhoneNumber = {} ", user.getPhoneNumber() );//

//        CustomUserDetails user = (CustomUserDetails) session.getAttribute("user");

        // 토큰 생성
        JwtTokenDto jwtToken = new JwtTokenDto();
        jwtToken.setToken(jwtTokenUtils.generateToken(user));
//
//        Cookie cookie = new Cookie("jwtToken", jwtToken.getToken() );
//        cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
//        response.addCookie(cookie);

//        return "redirect:/token/issue";
//        return "redirect:/home/users";
        return jwtToken.getToken();
    }

    @GetMapping("/login")
    public String loginView(Model model, @ModelAttribute UserDto dto){
        model.addAttribute("userDto", dto);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // 쿠키 파기
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }

}

package likelion.market.contoller;


import jakarta.servlet.http.HttpSession;
import likelion.market.dto.UserDto;
import likelion.market.security.CustomUserDetails;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserDetailsManager manager;
    private final UserService service;

//    @GetMapping("/home/users")
    public String homeView (Model model,  Authentication auth){

        CustomUserDetails userDetails = (CustomUserDetails) manager.loadUserByUsername(auth.getName());
        model.addAttribute("username",userDetails.getUsername());
        log.info(auth.getName());
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());

        return "home";
    }
}

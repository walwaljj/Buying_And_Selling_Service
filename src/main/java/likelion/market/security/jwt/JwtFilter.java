package likelion.market.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.market.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if(header == null){
//            if(request.getCookies() == null){
//                filterChain.doFilter(request, response);
//                return ;
//            }
//
//
//            Cookie cookieToken = Arrays.stream(request.getCookies())
//                    .filter(cookie -> cookie.getName().equals("jwtToken"))
//                    .findFirst()
//                    .orElse(null);
//
//            if(cookieToken == null){
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            Date expiration = jwtTokenUtils.parseClaims(cookieToken.getValue()).getExpiration();
//
//
//            if(expiration.before(new Date())) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String jwtTokenValue = cookieToken.getValue();
//            header = "Bearer " + jwtTokenValue;
//
//            log.info("token = {}" ,header);

        if(header != null && header.startsWith("Bearer ")){
            String token = header.split(" ")[1];
            if(jwtTokenUtils.validate(token)){

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                String username = jwtTokenUtils.parseClaims(token).getSubject();

                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        CustomUserDetails.builder().username((username)).build(), token, new ArrayList<>());

                context.setAuthentication(authenticationToken);

                SecurityContextHolder.setContext(context);
            }
            else {
                log.warn("jwt validation failed");
            }


        }
//        }
        filterChain.doFilter(request, response);

    }
}

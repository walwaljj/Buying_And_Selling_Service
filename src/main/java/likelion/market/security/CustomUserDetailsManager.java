package likelion.market.security;

import likelion.market.entity.UserEntity;
import likelion.market.repository.UserRepository;
import likelion.market.security.config.PasswordEncoderConfig;
import likelion.market.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;

    @Override
    public void createUser(UserDetails userDetails) {
//        CustomUserDetails userDto = (CustomUserDetails)  userDetails;
//        userRepository.save(UserEntity.builder()
//                .username(userDto.getUsername())
//                .password(passwordEncoder.passwordEncoder().encode(userDto.getPassword()))
//                .email(userDto.getEmail())
//                .phoneNumber(userDto.getPhoneNumber()).build());
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(username + "을 찾을 수 없습니다.");
        }

        UserEntity userEntity = optionalUser.get();

        return CustomUserDetails.fromEntity(userEntity);
    }
}

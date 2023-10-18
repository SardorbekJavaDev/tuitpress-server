package uz.tuit.press.service.details;

import uz.tuit.press.config.details.CustomUserDetails;
import uz.tuit.press.entity.UserEntity;
import uz.tuit.press.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByEmailAndVisibleTrue(email);
        return optionalUser.map(CustomUserDetails::new).orElse(null);
    }
}

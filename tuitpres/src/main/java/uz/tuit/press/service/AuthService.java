package uz.tuit.press.service;

import uz.tuit.press.dto.UserJWTDTO;
import uz.tuit.press.dto.request.AuthRequestDTO;
import uz.tuit.press.dto.request.UserDTO;
import uz.tuit.press.entity.UserEntity;
import uz.tuit.press.enums.UserRole;
import uz.tuit.press.enums.UserStatus;
import uz.tuit.press.exception.AppBadRequestException;
import uz.tuit.press.exception.AppForbiddenException;
import uz.tuit.press.exception.EmailAlreadyExistsException;
import uz.tuit.press.exception.PasswordOrEmailWrongException;
import uz.tuit.press.repository.UserRepository;
import uz.tuit.press.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${user.verification.url}")
    private String verificationUrl;

    private final UserRepository userRepository;
    private final AttachService attachService;

    public UserDTO login(AuthRequestDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        Optional<UserEntity> optional =
                userRepository.findByEmailAndPassword(dto.getEmail(), pswd);
        if (optional.isEmpty()) {
            log.warn("login: Password or email wrong!");
            throw new PasswordOrEmailWrongException("Password or email wrong!");
        }

        UserEntity entity = optional.get();
        if (!entity.getStatus().equals(UserStatus.ACTIVE)) {
            log.warn("Blocked User try Login in System !");
            throw new AppForbiddenException("Not Access !");
        }

        UserDTO profile = new UserDTO();
        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());
        profile.setEmail(entity.getEmail());
//        profile.setPassword(dto.getPassword()); // TODO: 23.06.2022 Decode password
        profile.setJwt(JwtUtil.encode(entity.getEmail()));
        return profile;
    }

    public String registration(UserDTO dto) {
        Optional<UserEntity> optional = userRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);

        entity.setRole(UserRole.USER);
        entity.setStatus(UserStatus.NOT_ACTIVE);
        entity.setVisible(true);
        userRepository.save(entity);

        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getEmail());
        builder.append(verificationUrl).append(jwt);
        return "Activate Your Registration " + builder;  //TODO xuddi shuni emailga yubor
    }


    public void verification(String jwt) {
        UserJWTDTO user = null;
        try {
            user = JwtUtil.decode(jwt);
        } catch (JwtException e) {
            throw new AppBadRequestException("Verification not completed");
        }
        userRepository.updateStatus(UserStatus.ACTIVE, user.getEmail());
        System.out.println(user.getEmail());
    }
}

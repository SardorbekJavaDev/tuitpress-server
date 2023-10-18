package uz.tuit.press.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.*;
import uz.tuit.press.dto.request.UserDTO;
import org.springframework.stereotype.Service;
import uz.tuit.press.entity.UserEntity;
import uz.tuit.press.enums.UserRole;
import uz.tuit.press.enums.UserStatus;
import uz.tuit.press.exception.EmailAlreadyExistsException;
import uz.tuit.press.exception.ItemNotFoundException;
import uz.tuit.press.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO create(UserDTO dto) {
        checkEmail(dto.getEmail());
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);
        entity.setRole(UserRole.USER);
        entity.setStatus(UserStatus.ACTIVE);
        UserEntity saved = userRepository.save(entity);

        dto.setId(saved.getId());
        dto.setStatus(saved.getStatus().toString());
        dto.setCreatedDate(saved.getCreatedDate());
        dto.setUpdatedDate(saved.getUpdatedDate());
        return dto;
    }

    private void checkEmail(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) throw new EmailAlreadyExistsException("Email already has been taken !");
    }

    public PageImpl<UserDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<UserDTO> dtoList = new ArrayList<>();

        Page<UserEntity> entityPage = userRepository.findAllByVisible(true, pageable);

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    private UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus().toString());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }


    public UserDTO update(String id, UserDTO dto) {
        UserEntity entity = getUser(id);
        checkEmail(dto.getEmail());

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);
        UserEntity saved = userRepository.save(entity);

        dto.setId(saved.getId());
        dto.setStatus(saved.getStatus().toString());
        dto.setCreatedDate(saved.getCreatedDate());
        dto.setUpdatedDate(saved.getUpdatedDate());
        return dto;
    }

    public Boolean delete(String id) {
        UserEntity user = getUser(id);
        userRepository.delete(user);
        return true;
    }

    public Boolean makeActive(String id, boolean isActive) {
        UserEntity currentUser = getUser(id);
        if (isActive)
            currentUser.setStatus(UserStatus.ACTIVE);
        else
            currentUser.setStatus(UserStatus.BLOCK);

        userRepository.save(currentUser);
        return true;
    }


    private UserEntity getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
    }

    public UserDTO getById(String id) {
        return toDTO(getUser(id));
    }

}

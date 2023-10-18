package uz.tuit.press.service;

import uz.tuit.press.dto.request.UserRequestDTO;
import uz.tuit.press.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;
import uz.tuit.press.entity.UserEntity;

@Service
public class UserService {

    public UserResponseDTO create(UserRequestDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(dto.getName());
        return null;
    }

    public Object paginationList(int page, int size) {
        return null;
    }

    public Object getById(Integer id) {
        return null;
    }

    public Object update(Integer id, UserRequestDTO dto) {
        return null;
    }

    public Object delete(Integer id) {
        return null;
    }
}

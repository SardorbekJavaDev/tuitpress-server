package uz.tuit.press.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private String companyName;
    private String street;
    private String zipCode;
    private String country;
    private String website;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private String jwt;

}

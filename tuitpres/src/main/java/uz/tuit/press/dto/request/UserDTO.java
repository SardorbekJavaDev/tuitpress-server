package uz.tuit.press.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @NotBlank
    @Size(max = 15, min = 3, message = "Name not valid")
    private String name;
    @NotBlank
    @Size(max = 15, min = 3, message = "Name not valid")
    private String surname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(max = 12, min = 4, message = "Password not valid")
    private String password;

    // + for response

    private String id;
    private String status;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private String jwt;
}

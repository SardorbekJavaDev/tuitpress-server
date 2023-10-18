package uz.tuit.press.entity;

import uz.tuit.press.enums.UserRole;
import uz.tuit.press.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.NOT_ACTIVE;

}

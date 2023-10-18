package uz.tuit.press.repository;

import uz.tuit.press.entity.UserEntity;
import uz.tuit.press.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmailAndVisibleTrue(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndPassword(String email, String pswd);

    @Transactional
    @Modifying
    @Query("update UserEntity as u set u.status = :status where u.email = :email")
    void updateStatus(@Param("status") UserStatus status, @Param("email") String email);
}

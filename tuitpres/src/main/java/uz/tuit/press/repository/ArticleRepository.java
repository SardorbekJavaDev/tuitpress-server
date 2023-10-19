package uz.tuit.press.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.tuit.press.entity.ArticleEntity;
import uz.tuit.press.entity.UserEntity;
import uz.tuit.press.enums.UserStatus;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {

    Page<ArticleEntity> findAllByVisible(Boolean visible, Pageable pageable);

}

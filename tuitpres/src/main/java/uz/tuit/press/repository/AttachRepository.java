package uz.tuit.press.repository;

import uz.tuit.press.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {
}
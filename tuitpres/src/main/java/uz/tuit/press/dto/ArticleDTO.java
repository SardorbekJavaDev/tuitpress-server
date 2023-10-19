package uz.tuit.press.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.press.entity.AttachEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String slug;
    private String title;
    private String body;
    private Integer favoritesCount;
    private Integer tagList;
    private String description;
    private String photoId;
    private String userId;

    private String id;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;
}

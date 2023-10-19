package uz.tuit.press.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity extends BaseEntity {
    @Column
    private String slug;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    private Integer favoritesCount;
    @Column
    private Integer tagList;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "user_id", nullable = false)
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;


}


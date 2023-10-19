package uz.tuit.press.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.tuit.press.config.details.EntityDetails;
import uz.tuit.press.dto.ArticleDTO;
import uz.tuit.press.entity.ArticleEntity;
import uz.tuit.press.exception.ItemNotFoundException;
import uz.tuit.press.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final EntityDetails entityDetails;

    public ArticleDTO create(ArticleDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setFavoritesCount(dto.getFavoritesCount());
        entity.setTagList(dto.getTagList());
        entity.setDescription(dto.getDescription());
        entity.setPhotoId(dto.getPhotoId());
        entity.setUserId(entityDetails.getUserEntity().getId()); // todo contex holderdan olasan
        entity.setSlug(makeSlug(dto.getTitle()));
        ArticleEntity saved = articleRepository.save(entity);

        dto.setId(saved.getId());
        dto.setSlug(saved.getSlug());
        dto.setUserId(saved.getId());
        dto.setCreatedDate(saved.getCreatedDate());
        dto.setUpdatedDate(saved.getUpdatedDate());
        return dto;
    }

    public ArticleDTO update(String id, ArticleDTO dto) {
        ArticleEntity entity = getArticle(id);

        entity.setSlug(dto.getSlug());
        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setFavoritesCount(dto.getFavoritesCount());
        entity.setTagList(dto.getTagList());
        entity.setDescription(dto.getDescription());
        entity.setPhotoId(dto.getPhotoId());
        ArticleEntity saved = articleRepository.save(entity);

        dto.setId(saved.getId());
        dto.setCreatedDate(saved.getCreatedDate());
        dto.setUpdatedDate(saved.getUpdatedDate());
        return dto;
    }

    public Boolean delete(String id) {
        ArticleEntity user = getArticle(id);
        articleRepository.delete(user);
        return true;
    }

    public ArticleDTO getById(String id) {
        return toDTO(getArticle(id));
    }

    public PageImpl<ArticleDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ArticleDTO> dtoList = new ArrayList<>();

        Page<ArticleEntity> entityPage = articleRepository.findAllByVisible(true, pageable);

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    private ArticleEntity getArticle(String id) {
        return articleRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setSlug(entity.getSlug());
        dto.setTitle(entity.getTitle());
        dto.setBody(entity.getBody());
        dto.setFavoritesCount(entity.getFavoritesCount());
        dto.setTagList(entity.getTagList());
        dto.setDescription(entity.getDescription());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    private String makeSlug(String title) {
        StringBuilder builder = new StringBuilder();

        String[] str = title.toLowerCase().split(" ");
        for (String s : str) {
            builder.append(s).append("-");
        }
        System.out.println(builder);
        return builder.toString();
    }

}

package uz.tuit.press.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.tuit.press.dto.ArticleDTO;
import uz.tuit.press.service.ArticleService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@Api("Article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @GetMapping("/")
    public ResponseEntity<?> getProfileList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(articleService.paginationList(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

}

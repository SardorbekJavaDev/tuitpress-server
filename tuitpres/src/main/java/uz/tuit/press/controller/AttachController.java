package uz.tuit.press.controller;

import uz.tuit.press.dto.response.AttachResponseDTO;
import uz.tuit.press.service.AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attach")
@Api("Attach")
public class AttachController {
    private final AttachService attachService;

    @ApiOperation(value = "upload Attach", notes = "Method used for upload Attach")
    @PostMapping("/upload")
    public ResponseEntity<AttachResponseDTO> create(@RequestParam("file") MultipartFile file) {
        log.warn("Attach upload {}{}", file.getOriginalFilename(), AttachController.class);
        return ResponseEntity.ok(attachService.upload(file));
    }

    @ApiOperation(value = "Update Attach", notes = "Method used for update Attach")
    @PutMapping("/upload/{id}")
    public ResponseEntity<AttachResponseDTO> update(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        log.warn("Attach update {}{}", file.getOriginalFilename(), AttachController.class);
        return ResponseEntity.ok(attachService.update(file, id));
    }

    @ApiOperation(value = "Open general", notes = "Method used for Open by id")
    @GetMapping(value = "/open-general/{id}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("id") String key) {
        log.info("Attach open_general {}{}", key, AttachController.class);
        return attachService.openGeneral(key);
    }

    @ApiOperation(value = "delete by key", notes = "Method used for delete attach by key")
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Boolean> delete(@PathVariable("key") String key) {
        log.warn("Attach delete {}{}", key, AttachController.class);
        return ResponseEntity.ok(attachService.delete(key));
    }

    @ApiOperation(value = "download by key", notes = "Method used for download attach by key")
    @GetMapping("/download/{key}")
    public ResponseEntity<Resource> download(@PathVariable("key") String key) {
        log.info("Attach download {}{}", key, AttachController.class);
        return attachService.download(key);
    }
}

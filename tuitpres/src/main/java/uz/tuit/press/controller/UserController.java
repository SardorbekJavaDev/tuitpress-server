package uz.tuit.press.controller;

import uz.tuit.press.dto.request.UserDTO;
import uz.tuit.press.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Api("User")
public class UserController {
    // *** only for admin
    // Create user
    // Change roll
    // Get AdminLists
    // ***

    // Get active userLists
    // Get block userLists
    // Change user status (active/block)

    // Filter  data, createdDate, size


    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @GetMapping("/")
    public ResponseEntity<?> getProfileList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.paginationList(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.delete(id));
    }

//    *********************************

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> makeActive(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.makeActive(id));
    }


}

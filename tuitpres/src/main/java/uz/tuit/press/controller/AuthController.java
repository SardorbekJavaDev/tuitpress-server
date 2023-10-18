package uz.tuit.press.controller;

import uz.tuit.press.dto.request.AuthRequestDTO;
import uz.tuit.press.dto.request.UserDTO;
import uz.tuit.press.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Api(tags = "Auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "user login", notes = "Method used for user Login")
    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody @Valid AuthRequestDTO dto) {
        log.info("Authorization: {}{}", dto, AuthService.class);
        return ResponseEntity.ok(authService.login(dto));
    }

    @ApiOperation(value = "user registration", notes = "Method used for user Registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid UserDTO dto) {
        log.info("Registration: {}{}", dto, AuthService.class);
        return ResponseEntity.ok(authService.registration(dto));
    }

    @ApiOperation(value = "user verification", notes = "Method used for user verification")
    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        if (jwt.isBlank()) {
            log.warn("verification:  {}{}", jwt.isBlank(), AuthService.class);
        }
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}

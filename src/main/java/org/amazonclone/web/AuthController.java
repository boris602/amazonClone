package org.amazonclone.web;

import lombok.AllArgsConstructor;
import org.amazonclone.entity.User;
import org.amazonclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User incoming) {
        if (incoming.getUserName() == null || incoming.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required"));
        }
        User user = userService.findByName(incoming.getUserName());
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Username does not exist"));
        }
        else if (!incoming.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "password does not match the username"));
        }

        return ResponseEntity.ok(Map.of("id", user.getId().toString()));
    }
}

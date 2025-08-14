package org.amazonclone.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.entity.User;
import org.amazonclone.rest.UserRestController;
import org.amazonclone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserRestControllerImpl implements UserRestController {

    private UserService userService;
    private ObjectMapper objectMapper;


    @Override
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Long userId) {
            User theUser = userService.findById(userId);
            if (theUser == null) {
                throw new RuntimeException("User-Id not found - " + userId);
            }
            return theUser;
        }

    @GetMapping("/users/by-name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        User user = userService.findByName(name);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }


    @Override
    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User incoming) {
        String userName = incoming.getUserName();
        String password = incoming.getPassword();

        // Validate username
        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username is required"));
        }
        if (userName.contains(" ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username cannot contain spaces"));
        }
        if (userName.length() < 5 || userName.length() > 20) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username must be 5-20 characters long"));
        }

        // Validate password
        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
        }
        if (password.contains(" ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password cannot contain spaces"));
        }
        if (password.length() < 5 || password.length() > 30) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password must be 5-30 characters long"));
        }

        // Check if username exists
        if (userService.findByName(userName) != null) {
            return ResponseEntity.status(409).body(Map.of("error", "Username already exists"));
        }

        incoming.setId(null);
        User saved = userService.save(incoming);

        return ResponseEntity.created(URI.create("/api/users/" + saved.getId()))
                .body(Map.of(
                        "id", saved.getId().toString(),
                        "message", "User created successfully"
                ));
    }


    @Override
    @PutMapping("/users")
    public User updateUser(User theUser) {
        return userService.save(theUser);
    }

    @Override
    public User patchUser(@PathVariable Long userId, @RequestBody Map<String, Object> patchPayload) {
        User tempUser = userService.findById(userId);

        // throw exception if null
        if (tempUser == null) {
            throw new RuntimeException("User- Id not found - " + userId);
        }

        // throw exception if request body contains "id" key
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("User-Id not allowed in request body - " + userId);
        }
        User patchedUser = apply(patchPayload, tempUser);
        return userService.save(patchedUser);
    }


    private User apply(Map<String, Object> patchPayload, User tempUser) {
        ObjectNode userNode = objectMapper.convertValue(tempUser, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);
        userNode.setAll(patchNode);
        return objectMapper.convertValue(userNode, User.class);
    }

    @DeleteMapping("/users/{userId}")
    public User deleteUser(@PathVariable Long userId) {
        User tempUser = userService.findById(userId);
        if (tempUser == null) {
            throw new RuntimeException("User-Id not found - " + userId);
        }
        userService.deleteById(userId);

        return tempUser;
    }
}

package org.amazonclone.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.entity.User;
import org.amazonclone.rest.UserRestController;
import org.amazonclone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;


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
    public ResponseEntity<User> addUser(@RequestBody User incoming) {
        System.out.println("Incoming: " + incoming);
        System.out.println("Duplicate check for username: " + incoming.getUserName());

        if (incoming.getUserName() == null || incoming.getUserName().isBlank()
                || incoming.getPassword() == null || incoming.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        User existing = userService.findByName(incoming.getUserName()); // this checks userName
        if (existing != null) {
            return ResponseEntity.status(409).build();
        }

        // ✅ Important: for a new row, the ID must be null
        incoming.setId(null);

        User saved = userService.save(incoming);
        URI location = URI.create("/api/users/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
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

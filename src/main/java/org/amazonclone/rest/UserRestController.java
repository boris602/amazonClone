package org.amazonclone.rest;


import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserRestController {

    public List<User> findAll();

    public User getUser (Long userId);

    public ResponseEntity<Map<String, String>> addUser(User theUser);

    public User updateUser(User theUser);

    public User patchUser(Long userId,
                                   Map<String, Object> patchPayload);

    public User deleteUser(Long theId);
}


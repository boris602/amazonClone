package org.amazonclone.service;

import org.amazonclone.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long theId);

    User save(User theUser);

    void deleteById(Long theId);

    User findByName(String name);
}

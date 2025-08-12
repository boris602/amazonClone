package org.amazonclone.dao;

import org.amazonclone.entity.User;
import java.util.List;


public interface UserDao {

    List<User> findAll();

    User findById(Long theId);

    User findByName(String name);

    User save(User theUser);

    void deleteById(Long theId);
}

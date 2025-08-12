package org.amazonclone.service.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.dao.UserDao;
import org.amazonclone.entity.User;
import org.amazonclone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long theId) {
        return userDao.findById(theId);
    }

    @Override
    @Transactional
    public User save(User theUser) {
        return userDao.save(theUser);
    }

    @Override
    @Transactional
    public void deleteById(Long theId) {
        userDao.deleteById(theId);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }
}



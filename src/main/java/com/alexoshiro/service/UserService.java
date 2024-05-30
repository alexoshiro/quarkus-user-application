package com.alexoshiro.service;

import com.alexoshiro.entity.UserEntity;
import com.alexoshiro.exception.UserNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    public List<UserEntity> findAll(Integer page, Integer pageSize) {
        return UserEntity.findAll().page(page, pageSize).list();
    }

    public UserEntity findById(UUID id) {
        return (UserEntity) UserEntity.findByIdOptional(id)
                .orElseThrow(UserNotFoundException::new);
    }


    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        UserEntity.persist(userEntity);
        return userEntity;
    }

    @Transactional
    public UserEntity updateUser(UUID id, UserEntity userEntity) {
        var user = findById(id);
        user.name = userEntity.name;

        UserEntity.persist(user);
        return user;
    }

    public void deleteById(UUID id) {
        var user = findById(id);
        UserEntity.deleteById(id);
    }
}

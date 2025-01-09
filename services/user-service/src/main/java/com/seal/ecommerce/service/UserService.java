package com.seal.ecommerce.service;

import com.seal.ecommerce.entity.User;

public interface UserService {
    public User create(User user);

    User getById(Integer id);

    void save(User user);

}

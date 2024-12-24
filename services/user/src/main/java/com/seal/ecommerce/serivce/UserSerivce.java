package com.seal.ecommerce.serivce;

import com.seal.ecommerce.entity.User;
import com.seal.ecommerce.entity.UserAddress;
import org.springframework.stereotype.Service;

@Service
public class UserSerivce {
    public String getUser(UserAddress user) {
        return user.getAddressLine();
    }
}

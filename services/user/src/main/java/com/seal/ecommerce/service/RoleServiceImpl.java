package com.seal.ecommerce.service;

import com.seal.ecommerce.entity.Role;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    @Override
    public Role getByTitle(String title) {
        return roleRepository.findByTitle(title)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_TITLE_NOT_FOUND));
    }
}

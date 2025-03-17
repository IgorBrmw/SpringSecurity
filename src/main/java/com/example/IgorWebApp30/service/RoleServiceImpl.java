package com.example.IgorWebApp30.service;

import com.example.IgorWebApp30.model.Role;
import com.example.IgorWebApp30.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
 @Transactional
    public Set<Role> getAllRoles() {
        return new HashSet<>((Collection) roleRepository.findAll());
    }

}
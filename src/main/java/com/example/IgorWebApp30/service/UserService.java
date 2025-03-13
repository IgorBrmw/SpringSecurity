package com.example.IgorWebApp30.service;


import com.example.IgorWebApp30.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void deleteUser(long id);



}

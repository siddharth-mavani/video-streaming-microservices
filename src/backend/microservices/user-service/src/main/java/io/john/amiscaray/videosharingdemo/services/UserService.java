package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.domain.User;
import io.john.amiscaray.videosharingdemo.exceptions.UserAlreadyExistsException;
import io.john.amiscaray.videosharingdemo.exceptions.UserNotFoundException;
import io.john.amiscaray.videosharingdemo.repo.UserRepo;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService{
    // register
    void register(String username, String password);

    // login
    void login(String username, String password);

    // change password
    void changePassword(String username, String oldPassword, String newPassword);
    
    // logout
    void logout();

}



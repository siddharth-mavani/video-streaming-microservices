package io.john.amiscaray.videosharingdemo.controllers;

import io.john.amiscaray.videosharingdemo.services.UserService;


import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    //  register a user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        userService.register(username, password);
        return ResponseEntity.ok("User saved successfully.");

    }

    // login a user
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        userService.login(username, password);
        return ResponseEntity.ok("User login successfull.");
    }

    // change password
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) throws IOException {
        userService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully.");
    }

}

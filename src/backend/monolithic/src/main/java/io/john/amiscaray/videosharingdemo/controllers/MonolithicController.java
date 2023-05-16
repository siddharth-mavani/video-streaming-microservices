package io.john.amiscaray.videosharingdemo.controllers;

import io.john.amiscaray.videosharingdemo.domain.VideoDto;
import io.john.amiscaray.videosharingdemo.services.MonolithicService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.john.amiscaray.videosharingdemo.domain.Subscription;

import java.io.IOException;
import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class MonolithicController {

    private MonolithicService monolithicService;

    // Each parameter annotated with @RequestParam corresponds to a form field where the String argument is the name of the field
    @PostMapping("video")
    public ResponseEntity<String> saveVideo(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {

        monolithicService.saveVideo(file, name);
        return ResponseEntity.ok("Video saved successfully.");

    }

    @GetMapping("video/{name}/{platform}")
    public ResponseEntity<Resource> getVideResponseEntity(@PathVariable("name") String name, @PathVariable("platform") String platform){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(monolithicService.getVideo(name, platform).getData()));

    }

    @GetMapping("video/all")
    public ResponseEntity<List<VideoDto>> getAllVideoDetails(){

        return ResponseEntity
                .ok(monolithicService.getAllVideoDetails());
    }

    @GetMapping("video/all/{platform}")
    public ResponseEntity<List<VideoDto>> getAllVideoDetailsFromPlatform(@PathVariable("platform") String platform){

        return ResponseEntity
                .ok(monolithicService.getAllVideoDetailsFromPlatform(platform));
    }

    @PostMapping("subscription")
    public ResponseEntity<String> saveSubscription(@RequestParam String username, @RequestParam Date subscribedDate, @RequestParam String subscriptionType) {
        
        monolithicService.saveSubscription(username, subscribedDate, subscriptionType);
        return ResponseEntity.ok("Subscription saved successfully.");

    }

    @GetMapping("subscription/{username}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("username") String username){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(monolithicService.getSubscription(username));

    }

    @GetMapping("subscription/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions(){

        return ResponseEntity
                .ok(monolithicService.getAllSubscriptions());

    }

    @PostMapping("subscription/ott")
    public ResponseEntity<String> saveOTTVideoSubscription(@RequestParam String username, @RequestParam String videoname) {
        monolithicService.saveOTTVideoSubscription(username, videoname);
        return ResponseEntity.ok("OTT Video Subscription saved successfully.");

    }

    @GetMapping("subscription/ott/{username}/{videoname}")
    public ResponseEntity<Boolean> isOTTVideoSubscribed(@PathVariable("username") String username, @PathVariable("videoname") String videoname){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(monolithicService.isOTTVideoSubscribed(username, videoname));

    }

    @GetMapping("subscription/ott/{username}")
    public ResponseEntity<List<String>> getAllOTTSubscriptions(@PathVariable("username") String username){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(monolithicService.getAllOTTSubscriptions(username));

    }

    @PostMapping("user/register")
    public ResponseEntity<String> registerUser(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        monolithicService.register(username, password);
        return ResponseEntity.ok("User saved successfully.");

    }

    // login a user
    @PostMapping("user/login")
    public ResponseEntity<String> loginUser(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        monolithicService.login(username, password);
        return ResponseEntity.ok("User login successfull.");
    }

    // change password
    @PostMapping("user/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) throws IOException {
        monolithicService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully.");
    }

}

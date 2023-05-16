package io.john.amiscaray.videosharingdemo.controllers;

import io.john.amiscaray.videosharingdemo.domain.Subscription;
import io.john.amiscaray.videosharingdemo.domain.SubscriptionDto;
import io.john.amiscaray.videosharingdemo.services.SubscriptionService;
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
import java.util.Date;

@RestController
@RequestMapping("subscription")
@AllArgsConstructor
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @PostMapping()
    public ResponseEntity<String> saveSubscription(@RequestParam String username, @RequestParam Date subscribedDate, @RequestParam String subscriptionType) {
        
        subscriptionService.saveSubscription(username, subscribedDate, subscriptionType);
        return ResponseEntity.ok("Subscription saved successfully.");

    }

    @GetMapping("{username}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("username") String username){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(subscriptionService.getSubscription(username));

    }

    @GetMapping("all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions(){

        return ResponseEntity
                .ok(subscriptionService.getAllSubscriptions());

    }

    @PostMapping("ott")
    public ResponseEntity<String> saveOTTVideoSubscription(@RequestParam String username, @RequestParam String videoname) {
        subscriptionService.saveOTTVideoSubscription(username, videoname);
        return ResponseEntity.ok("OTT Video Subscription saved successfully.");

    }

    @GetMapping("ott/{username}/{videoname}")
    public ResponseEntity<Boolean> isOTTVideoSubscribed(@PathVariable("username") String username, @PathVariable("videoname") String videoname){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(subscriptionService.isOTTVideoSubscribed(username, videoname));

    }

    @GetMapping("ott/{username}")
    public ResponseEntity<List<String>> getAllOTTSubscriptions(@PathVariable("username") String username){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(subscriptionService.getAllOTTSubscriptions(username));

    }
}

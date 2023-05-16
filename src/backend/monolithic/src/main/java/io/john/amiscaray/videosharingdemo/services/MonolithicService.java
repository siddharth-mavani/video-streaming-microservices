package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.domain.Video;
import io.john.amiscaray.videosharingdemo.domain.VideoDto;
import io.john.amiscaray.videosharingdemo.domain.Subscription;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Date;

public interface MonolithicService {
    Video getVideo(String name, String platform);

    void saveVideo(MultipartFile file, String name) throws IOException;

    List<VideoDto> getAllVideoDetails();

    List<VideoDto> getAllVideoDetailsFromPlatform(String platform);

    // register
    void register(String username, String password);

    // login
    void login(String username, String password);

    // change password
    void changePassword(String username, String oldPassword, String newPassword);
    
    // logout
    void logout();
    
    Subscription getSubscription(String username);
    void saveSubscription(String username, Date subscribedDate, String subscriptionType);
    List<Subscription> getAllSubscriptions();
    void saveOTTVideoSubscription(String username, String videoname);
    boolean isOTTVideoSubscribed(String username, String videoname);
    List<String> getAllOTTSubscriptions(String username);


}

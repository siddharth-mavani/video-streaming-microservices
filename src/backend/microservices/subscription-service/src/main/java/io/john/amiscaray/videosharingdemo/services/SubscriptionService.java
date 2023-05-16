package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.constants.SubType;
import io.john.amiscaray.videosharingdemo.domain.Subscription;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Date;

public interface SubscriptionService {
    Subscription getSubscription(String username);
    void saveSubscription(String username, Date subscribedDate, String subscriptionType);
    List<Subscription> getAllSubscriptions();
    void saveOTTVideoSubscription(String username, String videoname);
    boolean isOTTVideoSubscribed(String username, String videoname);
    List<String> getAllOTTSubscriptions(String username);
}

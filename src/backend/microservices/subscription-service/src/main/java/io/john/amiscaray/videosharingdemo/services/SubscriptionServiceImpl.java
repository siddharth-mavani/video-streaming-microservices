package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.constants.SubType;

import io.john.amiscaray.videosharingdemo.domain.Subscription;
import io.john.amiscaray.videosharingdemo.exceptions.VideoAlreadyExistsException;
import io.john.amiscaray.videosharingdemo.exceptions.VideoNotFoundException;
import io.john.amiscaray.videosharingdemo.repo.SubscriptionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepo repo;

    @Override
    public Subscription getSubscription(String username) {
        // if(!repo.existsByUsername(username)){
        //     throw new VideoNotFoundException();
        // }
        return repo.findByUsername(username);
    }

    @Override
    public void saveSubscription(String username, Date subscribedDate, String subscriptionType) {
        Subscription sub = repo.findByUsername(username);
        if(sub == null){
            Subscription newSub = new Subscription(username, subscribedDate, subscriptionType);
            repo.save(newSub);
            return;
        }
        sub.setSubscribedDate(subscribedDate);
        sub.setSubscriptionType(subscriptionType);
        repo.save(sub);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return repo.getAllSubscriptions();
    }

    @Override
    public void saveOTTVideoSubscription(String username, String videoname) {
        Subscription sub = repo.findByUsername(username);
        if(sub == null)
        {
            saveSubscription(username, null, "NONE");
        }
        sub = repo.findByUsername(username);
        List<String> existingSub = new ArrayList<>(Arrays.asList(sub.getOTTVideos()));
        existingSub.add(videoname);
        sub.setOTTVideos(existingSub.toArray(new String[0]));
        repo.save(sub);
    }

    @Override
    public boolean isOTTVideoSubscribed(String username, String videoname) {
        Subscription sub = repo.findByUsername(username);
        if(sub == null)
            return false;
        return Arrays.asList(sub.getOTTVideos()).contains(videoname);
    }

    @Override
    public List<String> getAllOTTSubscriptions(String username) {
        Subscription sub = repo.findByUsername(username);
        if(sub == null)
        {
            return new ArrayList<>();
        }
        return Arrays.asList(sub.getOTTVideos());
    }
}

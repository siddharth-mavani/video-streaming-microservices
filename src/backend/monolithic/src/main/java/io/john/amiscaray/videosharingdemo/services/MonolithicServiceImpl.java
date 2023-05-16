package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.constants.platform;
import io.john.amiscaray.videosharingdemo.domain.Video;
import io.john.amiscaray.videosharingdemo.domain.VideoDto;
import io.john.amiscaray.videosharingdemo.exceptions.VideoAlreadyExistsException;
import io.john.amiscaray.videosharingdemo.exceptions.VideoNotFoundException;
import io.john.amiscaray.videosharingdemo.repo.VideoRepo;
import lombok.AllArgsConstructor;
import io.john.amiscaray.videosharingdemo.repo.SubscriptionRepo;
import io.john.amiscaray.videosharingdemo.repo.UserRepo;
import io.john.amiscaray.videosharingdemo.domain.Subscription;
import io.john.amiscaray.videosharingdemo.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.john.amiscaray.videosharingdemo.exceptions.UserAlreadyExistsException;
import io.john.amiscaray.videosharingdemo.exceptions.UserNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
@AllArgsConstructor
public class MonolithicServiceImpl implements MonolithicService {

    private VideoRepo videoRepo;
    private UserRepo userRepo;
    private SubscriptionRepo subscriptionRepo;
    private static String external_provider_path = "/home/ayush/SE_project3_movies/";

    @Override
    public Video getVideo(String name, String platform) {
        if(platform.equals("Local"))
        {
            if(!videoRepo.existsByName(name)){
                throw new VideoNotFoundException();
            }
            return videoRepo.findByName(name);
        } else {
            for (File file : new File(external_provider_path + platform).listFiles())
            {
                // System.out.println(file.getName());
                // System.out.println(name);

                if(file.getName().equals(name))
                {
                    try {
                        FileInputStream fin = new FileInputStream(file);
                        byte[] data = fin.readAllBytes();
                        fin.close();
                        return new Video(name, data);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    // return new Video(name, file.getBytes());
            }
            throw new VideoNotFoundException();
        }
    }

    @Override
    public List<VideoDto> getAllVideoDetails() {
        // return videoRepo.getAllEntryNames();
        List<VideoDto> videoDetails = new ArrayList<>();
        for(String name : videoRepo.getAllEntryNames())
            videoDetails.add(new VideoDto(name, "Local"));
        for (platform p : platform.values())
            for (File file : new File(external_provider_path + p.getName()).listFiles())
            {
                VideoDto video = new VideoDto(file.getName(), p.getName());
                videoDetails.add(video);
            }
        return videoDetails;
    }

    @Override
    public List<VideoDto> getAllVideoDetailsFromPlatform(String plat) {
        // return videoRepo.getAllEntryNames();
        List<VideoDto> videoDetails = new ArrayList<>();
        if(plat.equals("Local"))
        {
            for(String name : videoRepo.getAllEntryNames())
                videoDetails.add(new VideoDto(name, "Local"));
            return videoDetails;
        }
        else {
            for (platform p : platform.values())
                if (p.getName().equals(plat))
                {
                    for (File file : new File(external_provider_path + p.getName()).listFiles())
                    {
                        VideoDto video = new VideoDto(file.getName(), p.getName());
                        videoDetails.add(video);
                    }
                    break;
                }
            return videoDetails;
        }
    }

    @Override
    public void saveVideo(MultipartFile file, String name) throws IOException {
        if(videoRepo.existsByName(name)){
            throw new VideoAlreadyExistsException();
        }
        Video newVid = new Video(name, file.getBytes());
        videoRepo.save(newVid);
    }

    @Override
    public void register(String username, String password) {
        int hashedPassword = password.hashCode();
        if(userRepo.existsByUsername(username)){
            throw new UserAlreadyExistsException("User already exists.");
        }   
        userRepo.save(new User(username, hashedPassword));
    }

    @Override
    public void login(String username, String password) {
        int hashedPassword = password.hashCode();
        if(!userRepo.existsByUsername(username)){
            throw new UserNotFoundException("User not found.");
        }
        User user = userRepo.findByUsername(username);
        if(user.getPassword()!=hashedPassword){
            throw new UserNotFoundException("User not found");
        }
    }

    // change password
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        int oldHashedPassword = oldPassword.hashCode();
        if(!userRepo.existsByUsername(username)){
            throw new UserNotFoundException("User not found.");
        }
        // check if old password is correct
        User user = userRepo.findByUsername(username);
        if(user.getPassword()!=oldHashedPassword){
            throw new UserNotFoundException("Incorrect Password");
        }
        int newHashedPassword = newPassword.hashCode();
        user.setPassword(newHashedPassword);
        userRepo.save(user);
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
    }

    @Override
    public Subscription getSubscription(String username) {
        // if(!subscriptionRepo.existsByUsername(username)){
        //     throw new VideoNotFoundException();
        // }
        return subscriptionRepo.findByUsername(username);
    }

    @Override
    public void saveSubscription(String username, Date subscribedDate, String subscriptionType) {
        Subscription sub = subscriptionRepo.findByUsername(username);
        if(sub == null){
            Subscription newSub = new Subscription(username, subscribedDate, subscriptionType);
            subscriptionRepo.save(newSub);
            return;
        }
        sub.setSubscribedDate(subscribedDate);
        sub.setSubscriptionType(subscriptionType);
        subscriptionRepo.save(sub);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepo.getAllSubscriptions();
    }

    @Override
    public void saveOTTVideoSubscription(String username, String videoname) {
        Subscription sub = subscriptionRepo.findByUsername(username);
        if(sub == null)
        {
            saveSubscription(username, null, "NONE");
        }
        sub = subscriptionRepo.findByUsername(username);
        List<String> existingSub = new ArrayList<>(Arrays.asList(sub.getOTTVideos()));
        existingSub.add(videoname);
        sub.setOTTVideos(existingSub.toArray(new String[0]));
        subscriptionRepo.save(sub);
    }

    @Override
    public boolean isOTTVideoSubscribed(String username, String videoname) {
        Subscription sub = subscriptionRepo.findByUsername(username);
        if(sub == null)
            return false;
        return Arrays.asList(sub.getOTTVideos()).contains(videoname);
    }

    @Override
    public List<String> getAllOTTSubscriptions(String username) {
        Subscription sub = subscriptionRepo.findByUsername(username);
        if(sub == null)
        {
            return new ArrayList<>();
        }
        return Arrays.asList(sub.getOTTVideos());
    }

}

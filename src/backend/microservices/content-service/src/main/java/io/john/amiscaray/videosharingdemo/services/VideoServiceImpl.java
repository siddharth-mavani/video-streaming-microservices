package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.constants.platform;
import io.john.amiscaray.videosharingdemo.domain.Video;
import io.john.amiscaray.videosharingdemo.domain.VideoDto;
import io.john.amiscaray.videosharingdemo.exceptions.VideoAlreadyExistsException;
import io.john.amiscaray.videosharingdemo.exceptions.VideoNotFoundException;
import io.john.amiscaray.videosharingdemo.repo.VideoRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoRepo repo;
    private static String external_provider_path = "/home/ayush/SE_project3_movies/";

    @Override
    public Video getVideo(String name, String platform) {
        if(platform.equals("Local"))
        {
            if(!repo.existsByName(name)){
                throw new VideoNotFoundException();
            }
            return repo.findByName(name);
        } else {
            for (File file : new File(external_provider_path + platform).listFiles())
            {
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
        // return repo.getAllEntryNames();
        List<VideoDto> videoDetails = new ArrayList<>();
        for(String name : repo.getAllEntryNames())
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
        // return repo.getAllEntryNames();
        List<VideoDto> videoDetails = new ArrayList<>();
        if(plat.equals("Local"))
        {
            for(String name : repo.getAllEntryNames())
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
        if(repo.existsByName(name)){
            throw new VideoAlreadyExistsException();
        }
        Video newVid = new Video(name, file.getBytes());
        repo.save(newVid);
    }

}

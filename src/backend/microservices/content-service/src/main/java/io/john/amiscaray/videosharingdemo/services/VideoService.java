package io.john.amiscaray.videosharingdemo.services;

import io.john.amiscaray.videosharingdemo.domain.Video;
import io.john.amiscaray.videosharingdemo.domain.VideoDto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    Video getVideo(String name, String platform);

    void saveVideo(MultipartFile file, String name) throws IOException;

    List<VideoDto> getAllVideoDetails();

    List<VideoDto> getAllVideoDetailsFromPlatform(String platform);
}

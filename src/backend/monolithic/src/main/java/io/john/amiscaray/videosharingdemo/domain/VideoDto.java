package io.john.amiscaray.videosharingdemo.domain;

public class VideoDto {
    private String name;
    private String platform;

    public VideoDto(String name, String platform) {
        this.name = name;
        this.platform = platform;
    }

    public VideoDto() {
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}

package io.john.amiscaray.videosharingdemo.domain;

public class SubscriptionDto {
    private String name;
    private String platform;

    public SubscriptionDto(String name, String platform) {
        this.name = name;
        this.platform = platform;
    }

    public SubscriptionDto() {
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

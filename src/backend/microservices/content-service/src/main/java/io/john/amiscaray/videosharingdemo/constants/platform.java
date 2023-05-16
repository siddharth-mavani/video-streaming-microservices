package io.john.amiscaray.videosharingdemo.constants;

public enum platform {
    NETFLIX("Netflix"),
    PRIME_VIDEO("Prime Video"),
    HOTSTAR("Hotstar");

    private final String name;

    platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

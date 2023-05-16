package io.john.amiscaray.videosharingdemo.constants;

public enum SubType {
    MONTHLY("Monthly Subscription"),
    HALF_YEARLY("Half Yearly Subscription"),
    ANNUAL("Annual Subscription");

    private final String description;

    SubType(String description) {
        this.description = description;
    }

    public String getName() {
        return description;
    }
}

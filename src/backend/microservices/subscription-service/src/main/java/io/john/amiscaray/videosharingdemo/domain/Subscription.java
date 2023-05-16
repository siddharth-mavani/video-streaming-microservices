package io.john.amiscaray.videosharingdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Subscription{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private Date subscribedDate;

    @Column
    private String subscriptionType;

    @Column(length = 100000)
    private String[] OTTVideos;

    public Subscription(String username, Date subscribedDate, String subscriptionType) {
        this.username = username;
        this.subscribedDate = subscribedDate;
        this.subscriptionType = subscriptionType;
        this.OTTVideos = new String[0];
    }
}

package io.john.amiscaray.videosharingdemo.repo;

import io.john.amiscaray.videosharingdemo.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Query(nativeQuery = true, value="SELECT * FROM subscription WHERE username = ?1")
    Subscription findByUsername(String username);

    @Query(nativeQuery = true, value="SELECT * FROM subscription")
    List<Subscription> getAllSubscriptions();
}

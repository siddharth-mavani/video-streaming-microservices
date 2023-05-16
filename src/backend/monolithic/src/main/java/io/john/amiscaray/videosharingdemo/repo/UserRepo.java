package io.john.amiscaray.videosharingdemo.repo;

import io.john.amiscaray.videosharingdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(nativeQuery = true, value="SELECT username FROM user")
    List<String> getAllEntryNames();
}


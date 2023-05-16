package io.john.amiscaray.videosharingdemo.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
    
        @Column(unique = true)
        private String username;

        @Column
        private int password;
    
        // @Lob
        // private byte[] data;
    
        public User(String username, int password) {
            this.username = username;
            this.password = password;
            // this.data = data;
        }

        public String getUsername() {
            return username;
        }
    
        public int getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    
        public void setPassword(int password) {
            this.password = password;
        }
}

package org.setana.treenity.security.message;

import lombok.Data;
import org.setana.treenity.security.model.CustomUser;

@Data
public class UserInfo {

    private Long userId;
    private String uid;
    private String email;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Integer buckets;

    public UserInfo(CustomUser user) {
        this.userId = user.getUserId();
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.point = user.getPoint();
        this.dailyWalks = user.getDailyWalks();
        this.buckets = user.getBuckets();
    }

}

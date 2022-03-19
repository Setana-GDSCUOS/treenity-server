package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.setana.treenity.entity.User;

@Data
public class UserFetchDto {

    private Long userId;
    private String uid;
    private String email;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Integer totalWalks;
    private Integer buckets;

    public UserFetchDto(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserFetchDto(User user) {
        this.userId = user.getId();
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.point = user.getPoint();
        this.dailyWalks = user.getDailyWalks();
        this.totalWalks = user.getTotalWalks();
    }

    @QueryProjection
    public UserFetchDto(Long userId, String uid, String email, String username, Integer point,
        Integer dailyWalks, Integer totalWalks, Integer buckets) {
        this.userId = userId;
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.point = point;
        this.dailyWalks = dailyWalks;
        this.totalWalks = totalWalks;
        this.buckets = buckets;
    }
}

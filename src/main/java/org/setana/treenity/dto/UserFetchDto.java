package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

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

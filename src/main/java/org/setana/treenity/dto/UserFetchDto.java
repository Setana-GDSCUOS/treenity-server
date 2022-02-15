package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserFetchDto {

    private Long userId;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Integer buckets;

    @QueryProjection
    public UserFetchDto(Long userId, String username, Integer point, Integer dailyWalks,
        Integer buckets) {
        this.userId = userId;
        this.username = username;
        this.point = point;
        this.dailyWalks = dailyWalks;
        this.buckets = buckets;
    }
}

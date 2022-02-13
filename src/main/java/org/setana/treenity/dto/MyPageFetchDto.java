package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Data;

@Data
public class MyPageFetchDto {

    private Long userId;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Integer totalWalks;
    private Integer bucket;
    private List<TreeFetchDto> trees;
    private List<WalkLogFetchDto> walkLogs;

    @QueryProjection
    public MyPageFetchDto(Long userId, String username, Integer point, Integer dailyWalks,
        Integer totalWalks, Integer bucket) {
        this.userId = userId;
        this.username = username;
        this.point = point;
        this.dailyWalks = dailyWalks;
        this.totalWalks = totalWalks;
        this.bucket = bucket;
    }

}

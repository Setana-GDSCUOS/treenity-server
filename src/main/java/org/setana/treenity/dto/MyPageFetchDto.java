package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.setana.treenity.entity.User;

@Data
public class MyPageFetchDto {

    private Long userId;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Long buckets;

    @QueryProjection
    public MyPageFetchDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.point = user.getPoint();
        this.dailyWalks = user.getDailyWalks();
    }

}

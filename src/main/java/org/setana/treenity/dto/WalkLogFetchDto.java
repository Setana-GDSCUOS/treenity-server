package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Data;

@Data
public class WalkLogFetchDto {

    private Long walkLogId;
    private Integer walks;
    private LocalDate date;

    @QueryProjection
    public WalkLogFetchDto(Long walkLogId, Integer walks, LocalDate date) {
        this.walkLogId = walkLogId;
        this.walks = walks;
        this.date = date;
    }

}

package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Data;

@Data
public class WalkLogFetchDto {

    private Integer walks;
    private LocalDate date;

    @QueryProjection
    public WalkLogFetchDto(Integer walks, LocalDate date) {
        this.walks = walks;
        this.date = date;
    }

}

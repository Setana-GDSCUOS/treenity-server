package org.setana.treenity.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TreeListFetchDto {

    private Long treeId;
    private Double longitude;
    private Double latitude;
    private String treeName;
    private LocalDate createdDate;

    private Double distance;

    // user
    private Long userId;
    private String username;

    public TreeListFetchDto(Long treeId, Double longitude, Double latitude, String treeName,
        LocalDate createdDate, Long userId, String username, Double distance) {
        this.treeId = treeId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.treeName = treeName;
        this.createdDate = createdDate;
        this.userId = userId;
        this.username = username;
        this.distance = distance;

    }

}

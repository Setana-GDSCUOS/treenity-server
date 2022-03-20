package org.setana.treenity.dto;

import lombok.Data;

@Data
public class TreeSaveDto {

    private Double longitude;
    private Double latitude;
    private String cloudAnchorId;
    private String treeName;
    private Long userId;
    private Long userItemId;

    protected TreeSaveDto() {
    }

    public TreeSaveDto(Double longitude, Double latitude, String cloudAnchorId, String treeName,
        Long userId, Long userItemId) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cloudAnchorId = cloudAnchorId;
        this.treeName = treeName;
        this.userId = userId;
        this.userItemId = userItemId;
    }

    public TreeSaveDto(String cloudAnchorId, String treeName, Long userId, Long userItemId) {
        this(null, null, cloudAnchorId, treeName, userId, userItemId);
    }
}

package org.setana.treenity.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TreeListDto {

    private Long treeId;
    private String cloudAnchorId;
    private String treeName;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private LocalDateTime createdDate;

    private Double distance;
    private Boolean bookmark;

    // relation
    private User user;

    public TreeListDto(Long treeId, String cloudAnchorId, String treeName, Double longitude,
        Double latitude, Integer level, LocalDateTime createdDate, Double distance,
        Boolean bookmark, Long userId, String username) {
        this.treeId = treeId;
        this.cloudAnchorId = cloudAnchorId;
        this.treeName = treeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.level = level;
        this.createdDate = createdDate;
        this.distance = distance;
        this.bookmark = bookmark != null && bookmark;

        this.user = new User(userId, username);
    }

    @Data
    private static class User {

        private Long userId;
        private String username;

        public User(Long userId, String username) {
            this.userId = userId;
            this.username = username;
        }
    }

}

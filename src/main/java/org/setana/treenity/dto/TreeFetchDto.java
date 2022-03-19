package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import org.setana.treenity.entity.Tree;

@Data
public class TreeFetchDto {

    private Long treeId;
    private String cloudAnchorId;
    private String treeName;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private Integer bucket;
    private LocalDateTime createdDate;
    private Double distance;

    // relation
    private UserFetchDto user;
    private ItemFetchDto item;

    @QueryProjection
    public TreeFetchDto(Tree tree) {
        this.treeId = tree.getId();
        this.cloudAnchorId = tree.getCloudAnchorId();
        this.treeName = tree.getName();
        this.longitude = tree.getLocation().getLongitude();
        this.latitude = tree.getLocation().getLatitude();
        this.level = tree.getLevel();
        this.bucket = tree.getBucket();
        this.createdDate = tree.getCreatedDate();

        this.user = new UserFetchDto(tree.getUser());
        this.item = new ItemFetchDto(tree.getItem());
    }

    public TreeFetchDto(Long treeId, String cloudAnchorId, String treeName, Double longitude,
        Double latitude, Integer level, LocalDateTime createdDate, Double distance, Long userId,
        String username) {
        this.treeId = treeId;
        this.cloudAnchorId = cloudAnchorId;
        this.treeName = treeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.level = level;
        this.createdDate = createdDate;
        this.distance = distance;

        this.user = new UserFetchDto(userId, username);
    }

}

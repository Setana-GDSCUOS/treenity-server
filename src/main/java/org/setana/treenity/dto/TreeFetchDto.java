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
    private Boolean bookmark;

    // relation
    private UserFetchDto user;
    private ItemFetchDto item;

    @QueryProjection
    public TreeFetchDto(Tree tree, Boolean bookmark) {
        this.treeId = tree.getId();
        this.cloudAnchorId = tree.getCloudAnchorId();
        this.treeName = tree.getName();
        this.longitude = tree.getLocation().getLongitude();
        this.latitude = tree.getLocation().getLatitude();
        this.level = tree.getLevel();
        this.bucket = tree.getBucket();
        this.createdDate = tree.getCreatedDate();
        this.bookmark = bookmark != null && bookmark;

        this.user = new UserFetchDto(tree.getUser());
        this.item = new ItemFetchDto(tree.getItem());
    }

}

package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;

@Data
public class TreeFetchDto {

    private Long treeId;
    private String cloudAnchorId;
    private String treeName;
    private String treeDescription;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private Integer bucket;
    private LocalDateTime createdDate;
    private Boolean bookmark;

    // relation
    private UserFetchDto user;
    private ItemFetchDto item;


    public TreeFetchDto(Tree tree) {
        this.treeId = tree.getId();
        this.cloudAnchorId = tree.getCloudAnchorId();
        this.treeName = tree.getName();
        this.treeDescription = tree.getDescription();
        this.longitude = tree.getLocation().getLongitude();
        this.latitude = tree.getLocation().getLatitude();
        this.level = tree.getLevel();
        this.bucket = tree.getBucket();
        this.createdDate = tree.getCreatedDate();
    }

    @QueryProjection
    public TreeFetchDto(Tree tree, Boolean bookmark) {
        this(tree);
        this.bookmark = bookmark != null && bookmark;
        this.item = new ItemFetchDto(tree.getItem());
    }

    @QueryProjection
    public TreeFetchDto(Tree tree, User user, Boolean bookmark) {
        this(tree, bookmark);
        this.user = new UserFetchDto(user);
    }

}

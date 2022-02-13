package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.Tree;

@Data
public class TreeFetchDto {

    private Long treeId;
    private Double longitude;
    private Double latitude;
    private String description;
    private String imagePath;
    private Integer level;
    private Integer exp;

    // item
    private ItemFetchDto item;

    @QueryProjection
    public TreeFetchDto(Tree tree) {
        this.treeId = tree.getId();
        this.longitude = tree.getLocation().getLongitude();
        this.latitude = tree.getLocation().getLatitude();
        this.description = tree.getDescription();
        this.imagePath = tree.getImagePath();
        this.level = tree.getLevel();
        this.exp = tree.getExp();

        this.item = new ItemFetchDto(tree.getItem());
    }
}

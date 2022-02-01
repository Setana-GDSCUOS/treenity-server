package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import org.setana.treenity.entity.ItemType;

@Data
public class ItemFetchDto {

    // item
    private Long itemId;
    private String itemName;
    private ItemType itemType;
    private Integer cost;
    private String imagePath;

    @QueryProjection
    public ItemFetchDto(Long id, String itemName, ItemType itemType, Integer cost, String imagePath) {
        this.itemId = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.cost = cost;
        this.imagePath = imagePath;
    }

}

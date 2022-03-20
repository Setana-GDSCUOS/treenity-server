package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;

@Data
public class ItemFetchDto {

    // item
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private ItemType itemType;
    private Integer cost;
    private String imagePath;

    @QueryProjection
    public ItemFetchDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.itemDescription = item.getDescription();
        this.itemType = item.getItemType();
        this.cost = item.getCost();
        this.imagePath = item.getImagePath();
    }

}

package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;

@Data
public class UserItemFetchDto {

    // item
    private Long userItemId;
    private Long itemId;
    private String itemName;
    private ItemType itemType;
    private String imagePath;

    // base entity
    private LocalDateTime createdDate;

    @QueryProjection
    public UserItemFetchDto(Long userItemId, Item item, LocalDateTime createdDate) {
        this.userItemId = userItemId;
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.itemType = item.getItemType();
        this.imagePath = item.getImagePath();
        this.createdDate = createdDate;
    }

}

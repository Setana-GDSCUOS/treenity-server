package org.setana.treenity.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.UserItem;

@Data
public class UserItemFetchDto {

    // item
    private Long userItemId;
    private Long itemId;
    private String itemName;
    private ItemType itemType;
    private String imagePath;
    private Integer count;

    // base entity
    private LocalDateTime createdDate;

    @QueryProjection
    public UserItemFetchDto(UserItem userItem) {
        this.userItemId = userItem.getId();
        this.count = userItem.getTotalCount();
        this.createdDate = userItem.getCreatedDate();

        this.itemId = userItem.getItem().getId();
        this.itemName = userItem.getItem().getName();
        this.itemType = userItem.getItem().getItemType();
        this.imagePath = userItem.getItem().getImagePath();
    }

}

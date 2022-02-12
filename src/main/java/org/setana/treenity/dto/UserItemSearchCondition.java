package org.setana.treenity.dto;

import lombok.Data;
import org.setana.treenity.entity.ItemType;

@Data
public class UserItemSearchCondition {

    private Long userId;
    private String itemName;
    private ItemType itemType;

}

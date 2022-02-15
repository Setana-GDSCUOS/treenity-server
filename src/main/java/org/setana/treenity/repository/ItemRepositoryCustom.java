package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.ItemFetchDto;

public interface ItemRepositoryCustom {

    List<ItemFetchDto> findAllItems();

    ItemFetchDto findByItemId(Long itemId);

}

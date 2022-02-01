package org.setana.treenity.repository;

import java.util.List;
import java.util.Optional;
import org.setana.treenity.dto.ItemFetchDto;

public interface ItemRepositoryCustom {

    List<ItemFetchDto> findAllItems();

    ItemFetchDto findItemById(Long itemId);

}

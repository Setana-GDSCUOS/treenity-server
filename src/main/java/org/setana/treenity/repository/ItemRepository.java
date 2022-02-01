package org.setana.treenity.repository;

import java.util.List;
import java.util.Optional;
import org.setana.treenity.dto.ItemFetchDto;
import org.setana.treenity.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Optional<Item> findByItemName(String itemName);

    List<ItemFetchDto> findAllItems();

    ItemFetchDto findItemById(Long itemId);
}

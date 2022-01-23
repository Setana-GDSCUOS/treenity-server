package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemName(String itemName);

}

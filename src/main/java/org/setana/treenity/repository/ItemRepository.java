package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Optional<Item> findByName(String name);

}

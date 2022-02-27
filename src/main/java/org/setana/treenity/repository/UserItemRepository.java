package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long>,
    UserItemRepositoryCustom {

    Optional<UserItem> findByUserAndItem_Name(User user, String itemName);
}

package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserItemRepository extends JpaRepository<UserItem, Long>, UserItemRepositoryCustom {

    Optional<UserItem> findById(Long userItemId);
}

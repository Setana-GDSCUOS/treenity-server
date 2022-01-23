package org.setana.treenity.repository;

import org.setana.treenity.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

}

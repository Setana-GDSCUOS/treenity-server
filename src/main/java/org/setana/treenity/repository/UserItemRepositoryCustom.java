package org.setana.treenity.repository;

import java.util.List;
import java.util.Optional;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.entity.UserItem;

public interface UserItemRepositoryCustom {

    List<UserItemFetchDto> findByUserId(Long userId);

    Optional<UserItem> searchByItemNameAndUserId(String itemName, Long userId);

}

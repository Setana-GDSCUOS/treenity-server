package org.setana.treenity.repository;

import java.util.List;
import java.util.Optional;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSearchCondition;
import org.setana.treenity.entity.UserItem;
import org.springframework.data.domain.Pageable;

public interface UserItemRepositoryCustom {

    List<UserItemFetchDto> findByUserId(Long userId, Pageable pageable);

    Optional<UserItem> search(UserItemSearchCondition condition);


}

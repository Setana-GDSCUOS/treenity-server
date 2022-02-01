package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.UserItemFetchDto;

public interface UserItemRepositoryCustom {

    List<UserItemFetchDto> findByUserId(Long userId);

}

package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserSearchCondition;

public interface UserRepositoryCustom {

    MyPageFetchDto searchMyPageById(Long userId);

    Optional<UserFetchDto> searchUserByCondition(UserSearchCondition condition);


}

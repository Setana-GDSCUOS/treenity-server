package org.setana.treenity.repository;

import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.UserFetchDto;

public interface UserRepositoryCustom {

    MyPageFetchDto searchMyPageById(Long userId);

    UserFetchDto searchUserById(Long userId);

}

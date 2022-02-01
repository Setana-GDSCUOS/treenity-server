package org.setana.treenity.repository;

import org.setana.treenity.dto.MyPageFetchDto;

public interface UserRepositoryCustom {

    MyPageFetchDto findMyPageById(Long userId);

}

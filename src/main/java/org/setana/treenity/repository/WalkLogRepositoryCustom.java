package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.springframework.data.domain.Pageable;

public interface WalkLogRepositoryCustom {

    List<WalkLogFetchDto> searchByUserId(Long userId, Pageable pageable);
}

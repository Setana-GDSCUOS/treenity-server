package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.entity.Location;
import org.setana.treenity.model.TreeCluster;
import org.springframework.data.domain.Pageable;

public interface TreeRepositoryCustom {

    TreeCluster searchByLocation(Location location);

    List<TreeFetchDto> findByUserId(Long userId, Pageable pageable);
}

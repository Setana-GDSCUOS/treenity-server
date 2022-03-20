package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeListDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;
import org.springframework.data.domain.Pageable;

public interface TreeRepositoryCustom {

    List<TreeListDto> searchByLocation(Long userId, Location location);

    TreeCluster searchTreeCluster(Long userId, Location location);

    List<TreeFetchDto> searchByUserId(Long userId, Pageable pageable);

    TreeFetchDto searchByTreeId(Long userId, Long treeId);
}

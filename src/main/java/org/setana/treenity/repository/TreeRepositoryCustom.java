package org.setana.treenity.repository;

import java.util.List;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeListFetchDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;

public interface TreeRepositoryCustom {

    List<TreeListFetchDto> searchByLocation(Location location);

    TreeCluster searchTreeCluster(Location location);

    List<TreeFetchDto> findByUserId(Long userId);
}

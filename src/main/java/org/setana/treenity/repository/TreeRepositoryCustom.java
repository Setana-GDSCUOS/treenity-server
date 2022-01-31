package org.setana.treenity.repository;

import org.setana.treenity.entity.Location;
import org.setana.treenity.model.TreeCluster;

public interface TreeRepositoryCustom {

    TreeCluster searchByLocation(Location location);
}

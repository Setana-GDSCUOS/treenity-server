package org.setana.treenity.repository;

import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;

public interface TreeRepositoryCustom {

    TreeCluster searchByLocation(Location location);
}

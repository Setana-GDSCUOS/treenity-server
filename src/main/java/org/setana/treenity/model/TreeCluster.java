package org.setana.treenity.model;

import java.util.List;
import lombok.Getter;
import org.setana.treenity.dto.TreeFetchDto;

@Getter
public class TreeCluster {

    private final List<TreeFetchDto> trees;
    private final Location location;

    public TreeCluster(List<TreeFetchDto> trees, Location location) {
        this.trees = trees;
        this.location = location;
    }

    public void validatePlant() {
        for (TreeFetchDto tree : trees) {
            if (tree.getDistance() < 1) {
                throw new IllegalStateException();
            }
        }
    }

}

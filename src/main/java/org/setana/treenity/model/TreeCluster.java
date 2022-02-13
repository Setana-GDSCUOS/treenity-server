package org.setana.treenity.model;

import java.util.List;
import lombok.Getter;
import org.setana.treenity.dto.TreeListFetchDto;

@Getter
public class TreeCluster {

    private final List<TreeListFetchDto> trees;
    private final Location location;

    public TreeCluster(List<TreeListFetchDto> trees, Location location) {
        this.trees = trees;
        this.location = location;
    }

    public void validatePlant() {
        for (TreeListFetchDto tree : trees) {
            if (tree.getDistance() < 1)
                throw new IllegalStateException();
        }
    }

}

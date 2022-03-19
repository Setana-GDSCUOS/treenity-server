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
            // TODO: 나무 사이의 간격을 1M 로 변경 필요
            if (tree.getDistance() < 0) {
                throw new IllegalStateException();
            }
        }
    }

}

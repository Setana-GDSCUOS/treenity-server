package org.setana.treenity.model;

import java.util.List;
import org.setana.treenity.entity.Tree;

public class TreeCluster {

    private final List<Tree> trees;
    private final Location location;

    public TreeCluster(List<Tree> trees, Location location) {
        this.trees = trees;
        this.location = location;
    }

    public void validatePlant() {
        for (Tree tree : trees) {
            tree.validatePlant(location);
        }
    }

}

package org.setana.treenity.model;

import java.util.List;
import lombok.Getter;
import org.setana.treenity.dto.TreeListDto;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;

@Getter
public class TreeCluster {

    private final List<TreeListDto> trees;
    private final Location location;

    public TreeCluster(List<TreeListDto> trees, Location location) {
        this.trees = trees;
        this.location = location;
    }

    public void validatePlant() {
        for (TreeListDto tree : trees) {
            // TODO: 나무 사이의 간격을 1M 로 변경 필요
            if (tree.getDistance() < 0) {
                throw new NotAcceptableException(ErrorCode.LOCATION_NOT_VALID);
            }
        }
    }

}

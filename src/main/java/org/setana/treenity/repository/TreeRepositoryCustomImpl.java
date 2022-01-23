package org.setana.treenity.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;

import static org.setana.treenity.entity.QTree.*;

@RequiredArgsConstructor
public class TreeRepositoryCustomImpl implements TreeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public TreeCluster searchByLocation(Location location) {
        // TODO: 위치를 기반으로 거리 계산 후 반경 5M 이내의 나무를 불러오는 조건 추가
        List<Tree> trees = queryFactory
            .selectFrom(tree)
            .fetch();

        return new TreeCluster(trees, location);
    }
}

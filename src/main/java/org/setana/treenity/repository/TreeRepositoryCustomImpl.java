package org.setana.treenity.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.QTreeFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.Location;
import org.setana.treenity.model.TreeCluster;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QTree.tree;

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

    public List<TreeFetchDto> findByUserId(Long userId) {
        return queryFactory.
            select(new QTreeFetchDto(tree))
            .from(tree)
            .join(tree.item, item).fetchJoin()
            .where(tree.user.id.eq(userId))
            .fetch();
    }
}

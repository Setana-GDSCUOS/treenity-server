package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QTree.tree;
import static org.setana.treenity.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.QTreeFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.entity.QTree;
import org.setana.treenity.model.Direction;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;
import org.setana.treenity.util.GeometryUtil;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TreeRepositoryCustomImpl implements TreeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public List<TreeFetchDto> searchByLocation(Location location) {

        // TODO: 현재 범위를 1km 단위로 설정, 이후 0.005km 로 변경 필요
        Location northEast = GeometryUtil.makeLocation(location, 1,
            Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.makeLocation(location, 1,
            Direction.SOUTHWEST.getBearing());

        String line = String.format("ST_GEOMFROMTEXT('LINESTRING(%f %f, %f %f)')",
            northEast.getLongitude(),
            northEast.getLatitude(),
            southWest.getLongitude(),
            southWest.getLatitude()
        );

        String centre = String.format("ST_GEOMFROMTEXT('POINT(%f %f)')",
            location.getLongitude(),
            location.getLatitude()
        );

        Query query = em.createNativeQuery(
            "SELECT tree.tree_id,"
                + " tree.cloud_anchor_id,"
                + " tree.tree_name,"
                + " ST_X(tree.point),"
                + " ST_Y(tree.point),"
                + " tree.level,"
                + " tree.created_date,"
                + " ST_Distance_Sphere(" + centre + ", tree.point) AS distance,"
                + " user.user_id,"
                + " user.username"
                + " FROM tree"
                + " JOIN user ON tree.user_id = user.user_id"
                + " WHERE MBRContains(" + line + ", tree.point)"
                + " ORDER BY distance");

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
            .map(result -> new TreeFetchDto(
                ((BigInteger) result[0]).longValue(),
                (String) result[1],
                (String) result[2],
                (Double) result[3],
                (Double) result[4],
                (Integer) result[5],
                ((Timestamp) result[6]).toLocalDateTime(),
                (Double) result[7],
                ((BigInteger) result[8]).longValue(),
                (String) result[9]
            )).collect(Collectors.toList());
    }

    public TreeCluster searchTreeCluster(Location location) {
        List<TreeFetchDto> dtos = searchByLocation(location);
        return new TreeCluster(dtos, location);
    }

    public List<TreeFetchDto> searchByUserId(Long userId, Pageable pageable) {
        return queryFactory.
            select(new QTreeFetchDto(tree))
            .from(tree)
            .join(tree.item, item).fetchJoin()
            .where(tree.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public TreeFetchDto searchByTreeId(Long treeId) {
        return queryFactory
            .select(new QTreeFetchDto(tree))
            .from(tree)
            .join(tree.user, user).fetchJoin()
            .join(tree.item, item).fetchJoin()
            .where(tree.id.eq(treeId))
            .fetchOne();
    }
}

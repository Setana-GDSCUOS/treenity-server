package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QTree.tree;

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
import org.setana.treenity.dto.TreeListFetchDto;
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
    public List<TreeListFetchDto> searchByLocation(Location location) {

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
                + " ST_X(tree.point),"
                + " ST_Y(tree.point),"
                + " tree.tree_name,"
                + " tree.created_date,"
                + " user.user_id,"
                + " user.username,"
                + " ST_Distance_Sphere(" + centre + ", tree.point) AS distance"
                + " FROM tree"
                + " JOIN user ON tree.user_id = user.user_id"
                + " WHERE MBRContains(" + line + ", tree.point)");

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
            .map(result -> new TreeListFetchDto(
                ((BigInteger) result[0]).longValue(),
                (String) result[1],
                (Double) result[2],
                (Double) result[3],
                (String) result[4],
                ((Timestamp) result[5]).toLocalDateTime().toLocalDate(),
                ((BigInteger) result[6]).longValue(),
                (String) result[7],
                (Double) result[8]
            )).collect(Collectors.toList());
    }

    public TreeCluster searchTreeCluster(Location location) {
        List<TreeListFetchDto> dtos = searchByLocation(location);
        return new TreeCluster(dtos, location);
    }

    public List<TreeFetchDto> findByUserId(Long userId, Pageable pageable) {
        return queryFactory.
            select(new QTreeFetchDto(tree))
            .from(tree)
            .join(tree.item, item).fetchJoin()
            .where(tree.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}

package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QTree.tree;
import static org.setana.treenity.entity.QUser.user;
import static org.setana.treenity.entity.QUserTree.userTree;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.setana.treenity.dto.QTreeFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeListDto;
import org.setana.treenity.exception.BusinessException;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.model.Direction;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;
import org.setana.treenity.repository.sql.QueryKey;
import org.setana.treenity.repository.sql.QueryProps;
import org.setana.treenity.util.GeometryUtil;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TreeRepositoryCustomImpl implements TreeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final QueryProps queryProps;
    private final WKTReader wktReader = new WKTReader();

    @Override
    public List<TreeListDto> searchByLocation(Long userId, Location location) {
        try {
            // TODO: 현재 범위를 1km 단위로 설정, 이후 0.005km 로 변경 필요
            Location northEast = GeometryUtil.makeLocation(location, 1,
                Direction.NORTHEAST.getBearing());
            Location southWest = GeometryUtil.makeLocation(location, 1,
                Direction.SOUTHWEST.getBearing());

            // https://www.baeldung.com/hibernate-spatial
            Geometry line = wktReader.read(String.format("LINESTRING(%f %f, %f %f)",
                northEast.getLongitude(),
                northEast.getLatitude(),
                southWest.getLongitude(),
                southWest.getLatitude()
            ));

            Geometry centre = wktReader.read(String.format("POINT(%f %f))",
                location.getLongitude(),
                location.getLatitude()
            ));

            Query query = em.createNativeQuery(queryProps.get(QueryKey.SEARCH_BY_LOCATION));
            query.setParameter("line", line);
            query.setParameter("centre", centre);
            query.setParameter("userId", userId);

            List<Object[]> resultList = query.getResultList();
            return resultList.stream()
                .map(result -> new TreeListDto(
                    ((BigInteger) result[0]).longValue(),
                    (String) result[1],
                    (String) result[2],
                    (Double) result[3],
                    (Double) result[4],
                    (Integer) result[5],
                    ((Timestamp) result[6]).toLocalDateTime(),
                    (Double) result[7],
                    (Boolean) result[8],
                    ((BigInteger) result[9]).longValue(),
                    (String) result[10]
                )).collect(Collectors.toList());
        } catch (ParseException e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }
    }

    public TreeCluster searchTreeCluster(Long userId, Location location) {
        List<TreeListDto> dtos = searchByLocation(userId, location);
        return new TreeCluster(dtos, location);
    }

    public List<TreeFetchDto> searchByUserId(Long userId, Pageable pageable) {
        return queryFactory.
            select(new QTreeFetchDto(tree, userTree.bookmark))
            .from(tree)
            .join(tree.item, item)
            .leftJoin(tree.userTrees, userTree).on(userTree.user.id.eq(userId))
            .where(tree.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public TreeFetchDto searchByTreeId(Long userId, Long treeId) {
        return queryFactory
            .select(new QTreeFetchDto(tree, userTree.bookmark))
            .from(tree)
            .join(tree.user, user)
            .join(tree.item, item)
            .leftJoin(tree.userTrees, userTree).on(userTree.user.id.eq(userId))
            .where(tree.id.eq(treeId))
            .fetchOne();
    }
}

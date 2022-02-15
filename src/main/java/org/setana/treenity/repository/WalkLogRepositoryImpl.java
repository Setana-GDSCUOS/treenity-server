package org.setana.treenity.repository;

import static org.setana.treenity.entity.QWalkLog.walkLog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.QWalkLogFetchDto;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class WalkLogRepositoryImpl implements WalkLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WalkLogFetchDto> searchByUserId(Long userId, Pageable pageable) {
        return queryFactory
            .select(new QWalkLogFetchDto(walkLog.id, walkLog.walks, walkLog.date))
            .from(walkLog)
            .where(walkLog.user.id.eq(userId))
            .orderBy(walkLog.date.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}

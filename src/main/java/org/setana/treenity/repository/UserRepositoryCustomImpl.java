package org.setana.treenity.repository;

import static org.setana.treenity.entity.QUser.user;
import static org.setana.treenity.entity.QUserItem.userItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.QMyPageFetchDto;
import org.setana.treenity.entity.ItemType;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MyPageFetchDto findMyPageById(Long userId) {
        MyPageFetchDto dto = queryFactory.select(new QMyPageFetchDto(user))
            .from(user)
            .where(user.id.eq(userId))
            .fetchOne();

        Long buckets = queryFactory.select(userItem.count())
            .from(userItem)
            .where(userItem.user.id.eq(userId)
                .and(userItem.item.itemType.eq(ItemType.WATER))
                .and(userItem.expDate.after(LocalDateTime.now())))
            .fetchOne();

        dto.setBuckets(buckets);
        return dto;
    }
}

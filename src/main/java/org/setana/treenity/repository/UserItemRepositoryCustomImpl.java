package org.setana.treenity.repository;

import static org.setana.treenity.entity.QUserItem.userItem;
import static org.setana.treenity.entity.QUser.user;
import static org.setana.treenity.entity.QItem.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.QUserItemFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.entity.UserItem;

@RequiredArgsConstructor
public class UserItemRepositoryCustomImpl implements UserItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<UserItemFetchDto> findByUserId(Long userId) {
        return queryFactory
            .select(new QUserItemFetchDto(
                userItem.id,
                userItem.item,
                userItem.createdDate
            ))
            .from(userItem)
            .join(userItem.user, user)
            .join(userItem.item, item)
            .where(userItem.user.id.eq(userId))
            .fetch();
    }

    public Optional<UserItem> searchByItemNameAndUserId(String itemName, Long userId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(userItem)
            .join(userItem.user, user)
            .join(userItem.item, item)
            .where(userItem.user.id.eq(userId)
                .and(userItem.item.name.eq(itemName)))
            .fetchOne());
    }
}

package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QUser.user;
import static org.setana.treenity.entity.QUserItem.userItem;
import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.QUserItemFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSearchCondition;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.UserItem;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserItemRepositoryCustomImpl implements UserItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<UserItemFetchDto> findByUserId(Long userId, Pageable pageable) {
        return queryFactory
            .select(new QUserItemFetchDto(userItem))
            .from(userItem)
            .join(userItem.item, item).fetchJoin()
            .where(userItem.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public Optional<UserItem> search(UserItemSearchCondition condition) {
        return Optional.ofNullable(queryFactory
            .selectFrom(userItem)
            .join(userItem.user, user).fetchJoin()
            .join(userItem.item, item).fetchJoin()
            .where(userItemEq(condition.getUserItemId()),
                userIdEq(condition.getUserId()),
                itemIdEq(condition.getItemId()),
                itemTypeEq(condition.getItemType()))
            .fetchOne());
    }

    private BooleanExpression userItemEq(Long userItemId) {
        return userItemId == null ? null : userItem.id.eq(userItemId);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : userItem.user.id.eq(userId);
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return itemId == null ? null : userItem.item.id.eq(itemId);
    }

    private BooleanExpression itemTypeEq(ItemType itemType) {
        return isEmpty(itemType) ? null : userItem.item.itemType.eq(itemType);
    }
}

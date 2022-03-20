package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;
import static org.setana.treenity.entity.QTree.tree;
import static org.setana.treenity.entity.QUser.user;
import static org.setana.treenity.entity.QUserItem.userItem;
import static org.setana.treenity.entity.QUserTree.userTree;
import static org.setana.treenity.entity.QWalkLog.walkLog;
import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.QMyPageFetchDto;
import org.setana.treenity.dto.QTreeFetchDto;
import org.setana.treenity.dto.QUserFetchDto;
import org.setana.treenity.dto.QWalkLogFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserSearchCondition;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.entity.ItemType;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserFetchDto> searchUserByCondition(UserSearchCondition condition) {
        return Optional.ofNullable(queryFactory
            .select(new QUserFetchDto(
                user.id,
                user.uid,
                user.email,
                user.username,
                user.point,
                user.dailyWalks,
                user.totalWalks,
                userItem.totalCount))
            .from(user)
            .leftJoin(user.userItems, userItem)
            .join(userItem.item, item).on(item.itemType.eq(ItemType.WATER))
            .where(userIdEq(condition.getUserId()),
                uidEq(condition.getUid()))
            .fetchOne());
    }

    public MyPageFetchDto searchMyPageById(Long userId) {

        int DEFAULT_PAGE_SIZE = 10;

        // WARNING: 유저 별로 ItemType 이 WATER 인 userItem 이 반드시 1개씩만 존재
        MyPageFetchDto myPageFetchDto = queryFactory
            .select(new QMyPageFetchDto(
                user.id,
                user.username,
                user.point,
                user.dailyWalks,
                user.totalWalks,
                userItem.totalCount))
            .from(user)
            .leftJoin(user.userItems, userItem)
            .join(userItem.item, item).on(item.itemType.eq(ItemType.WATER))
            .where(user.id.eq(userId))
            .fetchOne();

        // TODO: user 데이터 쿼리와 함께 작성 필요
        List<TreeFetchDto> treeFetchDtos = queryFactory
            .select(new QTreeFetchDto(tree, userTree.bookmark))
            .from(tree)
            .join(tree.item, item)
            .leftJoin(tree.userTrees, userTree).on(userTree.user.id.eq(userId))
            .where(tree.user.id.eq(userId))
            .fetch();

        // TODO: user 데이터 쿼리와 함께 작성 필요
        List<WalkLogFetchDto> walkLogs = queryFactory
            .select(new QWalkLogFetchDto(walkLog.id, walkLog.walks, walkLog.date))
            .from(walkLog)
            .where(walkLog.user.id.eq(userId))
            .orderBy(walkLog.date.asc())
            .limit(DEFAULT_PAGE_SIZE)
            .fetch();

        myPageFetchDto.setTrees(treeFetchDtos);
        myPageFetchDto.setWalkLogs(walkLogs);

        return myPageFetchDto;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : user.id.eq(userId);
    }

    private BooleanExpression uidEq(String uid) {
        return isEmpty(uid) ? null : user.uid.eq(uid);
    }

}

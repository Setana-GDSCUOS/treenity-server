package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.ItemFetchDto;
import org.setana.treenity.dto.QItemFetchDto;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ItemFetchDto> findAllItems() {
        return queryFactory
            .select(new QItemFetchDto(item))
            .from(item)
            .fetch();
    }

    public ItemFetchDto findByItemId(Long itemId) {
        return queryFactory
            .select(new QItemFetchDto(item))
            .from(item)
            .where(item.id.eq(itemId))
            .fetchOne();
    }
}

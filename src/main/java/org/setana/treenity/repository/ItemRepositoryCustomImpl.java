package org.setana.treenity.repository;

import static org.setana.treenity.entity.QItem.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.ItemFetchDto;
import org.setana.treenity.dto.QItemFetchDto;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ItemFetchDto> findAllItems() {
        return queryFactory
            .select(new QItemFetchDto(
                item.id,
                item.itemName,
                item.itemType,
                item.cost,
                item.imagePath
            ))
            .from(item)
            .fetch();
    }

    public ItemFetchDto findByItemId(Long itemId) {
        return queryFactory
            .select(new QItemFetchDto(
                item.id,
                item.itemName,
                item.itemType,
                item.cost,
                item.imagePath
            ))
            .from(item)
            .where(item.id.eq(itemId))
            .fetchOne();
    }
}

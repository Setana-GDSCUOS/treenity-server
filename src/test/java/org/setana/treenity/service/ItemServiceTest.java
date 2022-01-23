package org.setana.treenity.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이템 구매")
    public void purchaseItemTest() {
        // given
        String itemName = "나무A";
        Integer itemCost = 100;
        Integer userPoint = 200;

        Item item = new Item(itemName, ItemType.SEED, itemCost);
        User user = new User(100_000L, "userA", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        UserItem userItem = itemService.purchaseItem(itemName, savedUser.getId());
        User findUser = userRepository.findById(savedUser.getId()).get();
        Item findItem = itemRepository.findById(savedItem.getId()).get();

        // then
        assertEquals(savedUser.getId(), userItem.getUser().getId());
        assertEquals(savedItem.getId(), userItem.getItem().getId());
        assertEquals(findUser.getPoint(), userPoint - itemCost);
    }


}
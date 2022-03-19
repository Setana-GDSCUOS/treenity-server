package org.setana.treenity.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.exception.NotAcceptableException;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserItemServiceTest {

    @Autowired
    UserItemService userItemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이템 첫번째 구매")
    public void purchaseItemTest_1() {
        // given
        String itemName = "아이템A";
        Integer itemCost = 100;
        Integer userPoint = 300;

        Item item = new Item(itemName, ItemType.SEED, itemCost, null, 3);
        User user = new User("example_uid", "uid@example.com", "유저A", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        UserItem userItem = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        User findUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertEquals(savedUser.getId(), userItem.getUser().getId());
        assertEquals(savedItem.getId(), userItem.getItem().getId());
        assertEquals(findUser.getPoint(), userPoint - itemCost);

        assertEquals(1, userItem.getTotalCount());
        assertEquals(1, userItem.getPurchaseCount());
        assertEquals(LocalDate.now(), userItem.getPurchaseDate());
    }

    @Test
    @DisplayName("아이템 두번쩨 구매")
    public void purchaseItemTest_2() {
        // given
        String itemName = "아이템A";
        Integer itemCost = 100;
        Integer userPoint = 300;

        Item item = new Item(itemName, ItemType.SEED, itemCost, null, 3);
        User user = new User("example_uid", "uid@example.com", "유저A", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        UserItem userItem_A = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        UserItem userItem_B = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        User findUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertEquals(savedUser.getId(), userItem_B.getUser().getId());
        assertEquals(savedItem.getId(), userItem_B.getItem().getId());
        assertEquals(findUser.getPoint(), userPoint - itemCost * 2);

        assertEquals(2, userItem_B.getTotalCount());
        assertEquals(2, userItem_B.getPurchaseCount());
        assertEquals(LocalDate.now(), userItem_B.getPurchaseDate());
    }

    @Test
    @DisplayName("아이템 제한 초과 구매")
    public void purchaseItemTest_3() {
        // given
        String itemName = "아이템A";
        Integer itemCost = 100;
        Integer userPoint = 400;

        Item item = new Item(itemName, ItemType.SEED, itemCost, null, 3);
        User user = new User("example_uid", "uid@example.com", "유저A", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());

        // then
        assertThrows(NotAcceptableException.class,
            () -> userItemService.purchaseItem(savedUser.getId(), savedItem.getId()));
    }

    @Test
    @DisplayName("구매 제한 없이 구매")
    public void purchaseItemTest_4() {
        // given
        String itemName = "아이템A";
        Integer itemCost = 100;
        Integer userPoint = 400;

        Item item = new Item(itemName, ItemType.SEED, itemCost);
        User user = new User("example_uid", "uid@example.com", "유저A", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        UserItem userItem_A = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        UserItem userItem_B = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        UserItem userItem_C = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        UserItem userItem_D = userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        User findUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertEquals(savedUser.getId(), userItem_D.getUser().getId());
        assertEquals(savedItem.getId(), userItem_D.getItem().getId());
        assertEquals(findUser.getPoint(), userPoint - itemCost * 4);

        assertEquals(4, userItem_D.getTotalCount());
        assertEquals(4, userItem_D.getPurchaseCount());
        assertEquals(LocalDate.now(), userItem_D.getPurchaseDate());

    }

    @Test
    @DisplayName("포인트 초과 구매")
    public void purchaseItemTest_5() {
        // given
        String itemName = "아이템A";
        Integer itemCost = 100;
        Integer userPoint = 300;

        Item item = new Item(itemName, ItemType.SEED, itemCost, null, 5);
        User user = new User("test", "test@example.com", "유저A", userPoint);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        // when
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());
        userItemService.purchaseItem(savedUser.getId(), savedItem.getId());

        // then
        assertThrows(NotAcceptableException.class,
            () -> userItemService.purchaseItem(savedUser.getId(), savedItem.getId()));
    }

}

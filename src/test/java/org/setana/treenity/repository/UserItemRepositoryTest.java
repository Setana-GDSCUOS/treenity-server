package org.setana.treenity.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserItemRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserItemRepository userItemRepository;

    @Test
    @DisplayName("유저아이템 생성하기")
    public void testUserItem() {
        // given
        User user = new User(100_000L, "유저A");
        Item item = new Item("아이템A", ItemType.SEED, 100);

        User savedUser = userRepository.save(user);
        Item savedItem = itemRepository.save(item);

        UserItem userItem = new UserItem(savedUser, savedItem);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        UserItem findUserItem = userItemRepository.findById(savedUserItem.getId()).get();

        // then
        assertEquals(savedUserItem.getId(), findUserItem.getId());
        assertEquals(savedUser.getId(), findUserItem.getUser().getId());
        assertEquals(savedItem.getId(), findUserItem.getItem().getId());
    }

}

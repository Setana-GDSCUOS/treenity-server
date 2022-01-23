package org.setana.treenity.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.model.Location;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class TreeServiceTest {

    @Autowired
    TreeService treeService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserItemRepository userItemRepository;

    @Test
    @DisplayName("트리 생성하기")
    public void plantTreeTest() {
        // given
        Double longitude = 100D;
        Double latitude = 100D;
        Location location = new Location(longitude, latitude);

        Item item = new Item("나무A", ItemType.SEED, 100);
        User user = new User(100_000L, "userA", 200);

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        UserItem userItem = new UserItem(savedUser, savedItem);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.plantTree(location, savedUserItem.getId());

        // then
        assertEquals(longitude, tree.getLongitude());
        assertEquals(latitude, tree.getLatitude());
        assertEquals(savedUser.getId(), tree.getUser().getId());
        assertEquals(savedItem.getId(), tree.getItem().getId());
    }


}
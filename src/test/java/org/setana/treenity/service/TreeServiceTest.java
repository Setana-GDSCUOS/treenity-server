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
import org.setana.treenity.entity.Location;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.TreeRepository;
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
    TreeRepository treeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserItemRepository userItemRepository;

    @Test
    @DisplayName("나무 심기")
    public void plantTreeTest() {
        // given
        Location location = new Location(100.0, 100.0);

        Item item = new Item("나무A", ItemType.SEED, 100);
        User user = new User(100_000L, "userA");

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        UserItem userItem = new UserItem(savedUser, savedItem);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.plantTree(location, savedUserItem.getId());

        // then
        assertEquals(location, tree.getLocation());
        assertEquals(savedUser.getId(), tree.getUser().getId());
        assertEquals(savedItem.getId(), tree.getItem().getId());
        assertEquals(true, savedUserItem.getIsUsed());
    }

    @Test
    @DisplayName("나무 상호작용하기")
    public void interactTreeTest() {
        // given
        Location location = new Location(100.0, 100.0);

        Item item = new Item("양동이", ItemType.WATER, 10);
        User user = new User(100_000L, "userA");

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);
        Tree savedTree = treeRepository.save(new Tree(location, user, null));

        UserItem userItem = new UserItem(savedUser, savedItem);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.interactTree(savedTree.getId(), savedUserItem.getId());
        UserItem findUserItem = userItemRepository.findById(userItem.getId()).get();

        // then
        assertEquals(true, findUserItem.getIsUsed());
        assertEquals(1, tree.getLevel());
    }

}
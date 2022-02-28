package org.setana.treenity.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.dto.TreeListFetchDto;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.model.Location;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.TreeRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
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
        Location location = new Location(Random.randomLong(), Random.randomLat());

        Item item = new Item("아이템A", ItemType.SEED, 100);
        User user = new User("test", "test@example.com", "유저A");

        Item savedItem = itemRepository.save(item);
        User savedUser = userRepository.save(user);

        UserItem userItem = new UserItem(savedUser, savedItem, 1, 0);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.plantTree(location, savedUserItem.getId());

        // then
        assertEquals(location, tree.getLocation());
        assertEquals(savedUser.getId(), tree.getUser().getId());
        assertEquals(savedItem.getId(), tree.getItem().getId());
    }

    @Test
    @DisplayName("나무 상호작용하기 - 레벨업 이전")
    public void interactTreeTest() {
        // given
        Location location = new Location(100.0, 100.0);

        Item water = new Item("아이템A", ItemType.WATER, 100);
        Item seed = new Item("아이템B", ItemType.SEED, 200);
        User user = new User("test", "test@example.com", "유저A");

        Item savedWater = itemRepository.save(water);
        Item savedSeed = itemRepository.save(seed);
        User savedUser = userRepository.save(user);
        Tree savedTree = treeRepository.save(new Tree(location, user, savedSeed));

        UserItem userItem = new UserItem(savedUser, savedWater, 1, 0);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.interactTree(savedTree.getId(), savedUser.getId());
        UserItem findUserItem = userItemRepository.findById(userItem.getId()).get();

        // then
        assertEquals(0, findUserItem.getTotalCount());
        assertEquals(1, tree.getLevel());
        assertEquals(1, tree.getBucket());

    }

    @Test
    @DisplayName("나무 상호작용하기 - 레벨업 성공")
    public void interactTreeTest_2() {
        // given
        Location location = new Location(100.0, 100.0);

        Item water = new Item("아이템A", ItemType.WATER, 100);
        Item seed = new Item("아이템B", ItemType.SEED, 100);
        User user = new User("test", "test@example.com", "유저A");

        Item savedWater = itemRepository.save(water);
        Item savedSeed = itemRepository.save(seed);
        User savedUser = userRepository.save(user);
        Tree savedTree = treeRepository.save(new Tree(location, user, savedSeed));

        UserItem userItem = new UserItem(savedUser, savedWater, 1, 0);
        UserItem savedUserItem = userItemRepository.save(userItem);

        // when
        Tree tree = treeService.interactTree(savedTree.getId(), savedUser.getId());
        UserItem findUserItem = userItemRepository.findById(userItem.getId()).get();

        // then
        assertEquals(0, findUserItem.getTotalCount());
        assertEquals(2, tree.getLevel());
        assertEquals(0, tree.getBucket());
    }

    @Test
    @DisplayName("위치정보로 주변 나무 조회하기")
    public void searchByLocationTest() {

        Location location = new Location(127.02988185288436, 37.55637513168705);

        List<TreeListFetchDto> dtos = treeService.fetchByLocation(location);

        System.out.println("treeListFetchDtos=" + dtos);
    }

}

package org.setana.treenity.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Location;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class TreeRepositoryTest {

    @Autowired
    TreeRepository treeRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("나무 생성하기")
    public void createTreeTest() {
        // given
        User user = new User(100_000L, "유저A");
        User savedUser = userRepository.save(user);

        Location location = new Location(Random.randomLong(), Random.randomLat());
        Tree tree = new Tree(location, user, null);
        Tree savedTree = treeRepository.save(tree);

        // when
        Tree findTree = treeRepository.findById(tree.getId()).get();

        // then
        assertEquals(savedTree.getId(), findTree.getId());
        assertEquals(savedUser.getId(), findTree.getUser().getId());
        assertEquals(1, findTree.getLevel());
        assertEquals(0, findTree.getBucket());
    }

}

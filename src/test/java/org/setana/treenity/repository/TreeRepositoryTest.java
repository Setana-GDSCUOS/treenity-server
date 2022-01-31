package org.setana.treenity.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.Location;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class TreeRepositoryTest {

    @Autowired
    TreeRepository treeRepository;
    @Autowired
    UserRepository userRepository;

    double randomLong() {
        final double START = -90;
        final double END = +90;

        double random = new Random().nextDouble();
        return START + (random * (END - START));
    }

    double randomLat() {
        final double START = -180;
        final double END = +180;

        double random = new Random().nextDouble();
        return START + (random * (END - START));
    }

    @Test
    @DisplayName("나무 생성하기")
    public void createTreeTest() {
        // given
        User user = new User(100_000L, "userA");
        User savedUser = userRepository.save(user);

        Location location = new Location(randomLong(), randomLat());
        Tree tree = new Tree(location, user, null);
        Tree savedTree = treeRepository.save(tree);

        // when
        Tree findTree = treeRepository.findById(tree.getId()).get();

        // then
        assertEquals(savedTree.getId(), findTree.getId());
        assertEquals(savedUser.getId(), findTree.getUser().getId());
    }
}

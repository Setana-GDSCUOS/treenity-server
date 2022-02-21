package org.setana.treenity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.model.Location;
import org.setana.treenity.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class InitDbTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private TreeRepository treeRepository;

    private final Random random = new Random();

    private List<Tree> createRandomTrees(User user, Item item, Location baseLoc, int size) {
        List<Tree> trees = new ArrayList<>();

        double baseLong = baseLoc.getLongitude();
        double baseLat = baseLoc.getLatitude();

        for (int ind = 0; ind < size; ind++) {
            double longitude = baseLong + random.nextDouble() / 1_000;
            double latitude = baseLat + random.nextDouble() / 1_000;

            Location location = new Location(longitude, latitude);
            trees.add(new Tree(location, String.format("트리_%d에 대한 설명", ind), user, item));
        }
        return trees;
    }

    @Test
    @DisplayName("랜덤 좌표 생성")
    @Rollback(false)
    public void randomPointTest() {
        User user = new User(100_000L, "유저A");
        Item item = new Item("아이템A", ItemType.SEED, 100);

        em.persist(user);
        em.persist(item);

        int numTrees = 10;
        Location baseLoc = new Location(127.154, 37.603);

        List<Tree> trees = createRandomTrees(user, item, baseLoc, numTrees);
        trees.forEach(em::persist);

        Pageable pageable = PageRequest.of(0, 10);
        List<TreeFetchDto> dtos = treeRepository.findByUserId(user.getId(), pageable);

        assertTrue(dtos.stream().anyMatch(
            dto -> dto.getLongitude() > baseLoc.getLongitude()
                && dto.getLongitude() < baseLoc.getLongitude() + 0.001
                && dto.getLatitude() > baseLoc.getLatitude()
                && dto.getLatitude() < baseLoc.getLatitude() + 0.001));

        assertEquals(dtos.size(), numTrees);

    }

}

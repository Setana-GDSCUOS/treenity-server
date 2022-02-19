package org.setana.treenity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.entity.WalkLog;
import org.setana.treenity.model.Location;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        private final Random random = new Random();

        public void init() {
            // user
            User user0 = new User(100_000L, "유저0", 1_000);

            em.persist(user0);

            // item
            Item itemA = new Item("water", "water 에 대한 설명", ItemType.WATER, 100, "bucket.jpg",
                3);
            Item itemB = new Item("tutorial", "tutorial 아이템에 대한 설명", ItemType.SEED, 100,
                "tutorial.png", null);
            Item itemC = new Item("basic", "basic 아이템에 대한 설명", ItemType.SEED, 300, "basic.jpg",
                null);

            em.persist(itemA);
            em.persist(itemB);
            em.persist(itemC);

            // userItem (3 buckets, 2 sprout, 1 bud tree)
            UserItem userItemA = new UserItem(user0, itemA);
            UserItem userItemB = new UserItem(user0, itemB);
            UserItem userItemC = new UserItem(user0, itemC);

            em.persist(userItemA);
            em.persist(userItemB);
            em.persist(userItemC);

            // tree
            Location baseLoc = new Location(127.154, 37.603);
            int numTrees = 10;

            List<Tree> trees = createRandomTrees(user0, itemB, baseLoc, numTrees);
            trees.forEach(em::persist);

//            Tree treeA = new Tree(new Location(127.02988185288436, 37.55637513168705),
//                "treeA 나무에 대한 설명", user0, itemB);
//            Tree treeB = new Tree(new Location(127.05661406751057, 37.58468660084109),
//                "treeB 나무에 대한 설명", user0, itemC);
//
//
//            em.persist(treeA);
//            em.persist(treeB);

            // walkLog
            WalkLog walkLogA = new WalkLog(LocalDate.now().minusDays(5), 6_000, user0);
            WalkLog walkLogB = new WalkLog(LocalDate.now().minusDays(4), 7_000, user0);
            WalkLog walkLogC = new WalkLog(LocalDate.now().minusDays(3), 8_000, user0);
            WalkLog walkLogD = new WalkLog(LocalDate.now().minusDays(2), 9_000, user0);
            WalkLog walkLogE = new WalkLog(LocalDate.now().minusDays(1), 10_000, user0);
            WalkLog walkLogF = new WalkLog(LocalDate.now(), 10_000, user0);
            WalkLog walkLogG = new WalkLog(LocalDate.now().plusDays(1), 11_000, user0);
            WalkLog walkLogH = new WalkLog(LocalDate.now().plusDays(2), 12_000, user0);
            WalkLog walkLogI = new WalkLog(LocalDate.now().plusDays(3), 13_000, user0);
            WalkLog walkLogJ = new WalkLog(LocalDate.now().plusDays(4), 14_000, user0);
            WalkLog walkLogK = new WalkLog(LocalDate.now().plusDays(5), 15_000, user0);

            em.persist(walkLogA);
            em.persist(walkLogB);
            em.persist(walkLogC);
            em.persist(walkLogD);
            em.persist(walkLogE);
            em.persist(walkLogF);
            em.persist(walkLogG);
            em.persist(walkLogH);
            em.persist(walkLogI);
            em.persist(walkLogJ);
            em.persist(walkLogK);

        }

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
    }

}

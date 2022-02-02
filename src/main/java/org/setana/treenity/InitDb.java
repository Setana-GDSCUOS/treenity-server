package org.setana.treenity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Location;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
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

        public void init() {
            // user
            User user0 = new User(100_000L, "유저0", 1_000);

            em.persist(user0);

            // item
            Item itemA = new Item("water", ItemType.WATER, 30, "bucket.jpg");
            Item itemB = new Item("sprout", ItemType.SEED, 20, "sprout.png");
            Item itemC = new Item("bud tree", ItemType.SEED, 70, "bud_tree.jpg");

            em.persist(itemA);
            em.persist(itemB);
            em.persist(itemC);

            // userItem (3 buckets, 2 sprout, 1 bud tree)
            UserItem userItemA = new UserItem(user0, itemA);
            UserItem userItemB = new UserItem(user0, itemA);
            UserItem userItemC = new UserItem(user0, itemA);
            UserItem userItemD = new UserItem(user0, itemB);
            UserItem userItemE = new UserItem(user0, itemB);
            UserItem userItemF = new UserItem(user0, itemC);

            em.persist(userItemA);
            em.persist(userItemB);
            em.persist(userItemC);
            em.persist(userItemD);
            em.persist(userItemE);
            em.persist(userItemF);

            // tree
            Tree treeA = new Tree(new Location(37.55637513168705, 127.02988185288436), user0, itemB);
            Tree treeB = new Tree(new Location(37.58468660084109, 127.05661406751057), user0, itemC);

            em.persist(treeA);
            em.persist(treeB);
        }
    }

}

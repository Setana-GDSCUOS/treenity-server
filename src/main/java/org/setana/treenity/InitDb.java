package org.setana.treenity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
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
            User userA = new User(100_000L, "유저A", 1_000);

            em.persist(userA);

            // item
            Item itemA = new Item("water", ItemType.WATER, 30, "bucket.jpg");
            Item itemB = new Item("sprout", ItemType.SEED, 20, "sprout.png");
            Item itemC = new Item("bud tree", ItemType.SEED, 70, "bud_tree.png");

            em.persist(itemA);
            em.persist(itemB);
            em.persist(itemC);

            // userItem (3 buckets, 2 sprout, 1 bud tree)
            UserItem userItemA = new UserItem(userA, itemA);
            UserItem userItemB = new UserItem(userA, itemA);
            UserItem userItemC = new UserItem(userA, itemA);
            UserItem userItemD = new UserItem(userA, itemB);
            UserItem userItemE = new UserItem(userA, itemB);
            UserItem userItemF = new UserItem(userA, itemC);

            em.persist(userItemA);
            em.persist(userItemB);
            em.persist(userItemC);
            em.persist(userItemD);
            em.persist(userItemE);
            em.persist(userItemF);

        }
    }

}

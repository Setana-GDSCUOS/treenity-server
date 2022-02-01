package org.setana.treenity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.ItemType;
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
            // item
            Item itemA = new Item("water", ItemType.WATER, 30, "bucket.jpg");
            Item itemB = new Item("sprout", ItemType.SEED, 20, "sprout.png");
            Item itemC = new Item("bud tree", ItemType.SEED, 70, "bud_tree.png");

            em.persist(itemA);
            em.persist(itemB);
            em.persist(itemC);

        }
    }

}

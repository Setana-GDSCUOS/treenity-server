package org.setana.treenity.repository;

import java.util.Optional;
import javax.transaction.Transactional;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserTree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTreeRepository extends JpaRepository<UserTree, Long> {

    Optional<UserTree> findByUserAndTree(User user, Tree tree);

    @Transactional
    void deleteByUserAndTree(User user, Tree tree);

}

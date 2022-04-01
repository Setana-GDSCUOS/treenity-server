package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TreeRepository extends JpaRepository<Tree, Long>, TreeRepositoryCustom {

    @Query("select t from Tree t join fetch t.user where t.id = :treeId and t.user.id = :userId")
    Optional<Tree> findByIdAndUser_Id(@Param("treeId") Long treeId, @Param("userId") Long userId);

    @Query("select t from Tree t join fetch t.item where t.id = :treeId")
    Optional<Tree> findByIdWithinUser(@Param("treeId") Long treeId);
}

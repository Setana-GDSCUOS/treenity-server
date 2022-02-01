package org.setana.treenity.repository;

import org.setana.treenity.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long>, TreeRepositoryCustom {

}

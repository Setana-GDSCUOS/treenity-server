package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUid(String uid);
}

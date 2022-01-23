package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);

}

package org.setana.treenity.repository;

import java.util.Optional;
import org.setana.treenity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUid(String uid);

    @Modifying
    @Query("update User u set u.dailyWalks = 0")
    void resetDailyWalks();
}

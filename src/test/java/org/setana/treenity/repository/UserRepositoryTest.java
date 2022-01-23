package org.setana.treenity.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 생성하기")
    public void testUser() {
        // given
        User user = new User(100_000L, "userA");
        User savedUser = userRepository.save(user);

        // when
        User findUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertEquals(findUser.getId(), savedUser.getId());
    }

}
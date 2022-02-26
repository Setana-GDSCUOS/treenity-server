package org.setana.treenity.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 생성하기")
    public void testUser() {
        // given
        User user = new User("example_uid", "uid@example.com", "유저A");
        User savedUser = userRepository.save(user);

        // when
        User findUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertEquals(savedUser.getId(), findUser.getId());
    }

}

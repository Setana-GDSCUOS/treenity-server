package org.setana.treenity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.WalkLog;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.repository.WalkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalkLogRepository walkLogRepository;

    @Test
    @DisplayName("포인트 전환 시 걷기 기록 삽입")
    public void convertPointTest_1() {

        // given
        User user = new User(100_000L, "유저A");
        User savedUser = userRepository.save(user);

        Map<LocalDate, Integer> dateWalks = new HashMap<>() {{
            put(LocalDate.now().minusDays(1), 100);
            put(LocalDate.now(), 200);
            put(LocalDate.now().plusDays(1), 300);
        }};

        LocalDate startDate = dateWalks.keySet().stream()
            .min(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        LocalDate endDate = dateWalks.keySet().stream()
            .max(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        // when
        userService.convertToPoint(savedUser.getId(), dateWalks);

        User findUser = userRepository.findById(savedUser.getId()).get();
        List<WalkLog> findWalkLogs = walkLogRepository.findByUser_IdAndDateBetween(
            savedUser.getId(), startDate, endDate);

        // then
        assertEquals(savedUser.getId(), findUser.getId());
        assertEquals(200, findUser.getDailyWalks());
        assertEquals(600, findUser.getTotalWalks());
        assertEquals(6, findUser.getPoint());

        assertThat(findWalkLogs).extracting("date")
            .containsExactly(
                LocalDate.now().minusDays(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
            );

        assertThat(findWalkLogs).extracting("walks")
            .containsExactly(100, 200, 300);

    }

    @Test
    @DisplayName("포인트 전환 시 걷기 기록 업데이트")
    public void convertPointTest_2() {

        // given
        User user = new User(100_000L, "유저A");
        User savedUser = userRepository.save(user);

        // 각 날짜마다 걷기 100 저장
        Map<LocalDate, Integer> baseDateWalks = new HashMap<>() {{
            put(LocalDate.now().minusDays(1), 100);
            put(LocalDate.now(), 100);
            put(LocalDate.now().plusDays(1), 100);
        }};

        userService.convertToPoint(savedUser.getId(), baseDateWalks);

        // 각 날짜마다 아래 만큼 걷기 기록 추가
        Map<LocalDate, Integer> dateWalks = new HashMap<>() {{
            put(LocalDate.now().minusDays(1), 100);
            put(LocalDate.now(), 200);
            put(LocalDate.now().plusDays(1), 300);
        }};

        LocalDate startDate = dateWalks.keySet().stream()
            .min(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        LocalDate endDate = dateWalks.keySet().stream()
            .max(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        // when
        userService.convertToPoint(savedUser.getId(), dateWalks);

        User findUser = userRepository.findById(savedUser.getId()).get();
        List<WalkLog> findWalkLogs = walkLogRepository.findByUser_IdAndDateBetween(
            savedUser.getId(), startDate, endDate);

        // then
        assertEquals(300, findUser.getDailyWalks());
        assertEquals(900, findUser.getTotalWalks());
        assertEquals(9, findUser.getPoint());

        assertThat(findWalkLogs).extracting("date")
            .containsExactly(
                LocalDate.now().minusDays(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
            );

        assertThat(findWalkLogs).extracting("walks")
            .containsExactly(200, 300, 400);

    }

    @Test
    @DisplayName("포인트 전환 시 걷기 기록 삽입 및 업데이트")
    public void convertPointTest_3() {

        // given
        User user = new User(100_000L, "유저A");
        User savedUser = userRepository.save(user);

        // 각 날짜마다 걷기 100 저장
        Map<LocalDate, Integer> baseDateWalks = new HashMap<>() {{
            put(LocalDate.now().minusDays(3), 100);
            put(LocalDate.now().minusDays(2), 100);
            put(LocalDate.now().minusDays(1), 100);
        }};

        userService.convertToPoint(savedUser.getId(), baseDateWalks);

        // 각 날짜마다 아래 만큼 걷기 기록 추가
        Map<LocalDate, Integer> dateWalks = new HashMap<>() {{
            put(LocalDate.now().minusDays(1), 100);
            put(LocalDate.now(), 200);
            put(LocalDate.now().plusDays(1), 300);
        }};

        LocalDate startDate = baseDateWalks.keySet().stream()
            .min(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        LocalDate endDate = dateWalks.keySet().stream()
            .max(LocalDate::compareTo)
            .orElseThrow(IllegalStateException::new);

        // when
        userService.convertToPoint(savedUser.getId(), dateWalks);

        User findUser = userRepository.findById(savedUser.getId()).get();
        List<WalkLog> findWalkLogs = walkLogRepository.findByUser_IdAndDateBetween(
            savedUser.getId(), startDate, endDate);

        // then
        assertEquals(200, findUser.getDailyWalks());
        assertEquals(900, findUser.getTotalWalks());
        assertEquals(9, findUser.getPoint());

        assertThat(findWalkLogs).extracting("date")
            .containsExactly(
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2),
                LocalDate.now().minusDays(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
            );

        assertThat(findWalkLogs).extracting("walks")
            .containsExactly(100, 100, 200, 200, 300);
    }

}

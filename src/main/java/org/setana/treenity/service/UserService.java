package org.setana.treenity.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.WalkLog;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.repository.WalkLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WalkLogRepository walkLogRepository;

    @Transactional
    public void convertToPoint(Long userId, Map<LocalDate, Integer> dateWalks) throws IllegalArgumentException {

        User user = userRepository.findById(userId)
            .orElseThrow(IllegalArgumentException::new);

        upsertWalkLogs(user, dateWalks);

        Integer totalWalks = dateWalks.values().stream()
            .reduce(Integer::sum)
            .orElseThrow(IllegalArgumentException::new);

        user.updateTotalWalksAndPoint(totalWalks);
    }

    @Transactional
    public List<WalkLog> findWalkLogs(Long userId, Map<LocalDate, Integer> dateWalks) throws IllegalArgumentException{

        LocalDate startDate = dateWalks.keySet().stream()
            .min(LocalDate::compareTo)
            .orElseThrow(IllegalArgumentException::new);

        LocalDate endDate = dateWalks.keySet().stream()
            .max(LocalDate::compareTo)
            .orElseThrow(IllegalArgumentException::new);

        return walkLogRepository.findByUser_IdAndDateBetween(
            userId, startDate, endDate);
    }

    @Transactional
    public void updateDailyWalks(User user, WalkLog walkLog) {

        if (walkLog.getDate().equals(LocalDate.now())) {
            user.changeDailyWalks(walkLog);
        }
    }

    @Transactional
    public void upsertWalkLogs(User user, Map<LocalDate, Integer> dateWalks) throws IllegalArgumentException {

        List<WalkLog> walkLogs = findWalkLogs(user.getId(), dateWalks);

        List<LocalDate> dates = dateWalks.keySet().stream()
            .sorted(LocalDate::compareTo)
            .collect(Collectors.toList());

        for (LocalDate date : dates) {
            Optional<WalkLog> optional = walkLogs.stream()
                .filter(walkLog -> walkLog.getDate().equals(date))
                .findAny();

            WalkLog walkLog;

            if (optional.isPresent()) {
                walkLog = optional.get();
                walkLog.addWalks(dateWalks.get(date));
            } else {
                walkLog = new WalkLog(date, dateWalks.get(date), user);
                walkLogRepository.save(walkLog);
            }

            updateDailyWalks(user, walkLog);
        }

    }

    public UserFetchDto fetchUser(Long userId) {
        return userRepository.searchUserById(userId);
    }

    public MyPageFetchDto fetchMyPage(Long userId) {
        return userRepository.searchMyPageById(userId);
    }

}

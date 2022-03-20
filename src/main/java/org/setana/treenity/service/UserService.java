package org.setana.treenity.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserSearchCondition;
import org.setana.treenity.dto.UserUpdateDto;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.WalkLog;
import org.setana.treenity.exception.BusinessException;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotFoundException;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.repository.WalkLogRepository;
import org.setana.treenity.security.model.CustomUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.upload.url:${user.home}}")
    private String imageUrl;

    private final UserRepository userRepository;
    private final WalkLogRepository walkLogRepository;

    @Transactional
    public void convertToPoint(CustomUser customUser, Long userId,
        Map<LocalDate, Integer> dateWalks) {
        // 인증된 유저의 id 와 요청한 userId 가 일치하는지 확인
        customUser.checkUserId(userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        upsertWalkLogs(user, dateWalks);

        Integer totalWalks = dateWalks.values().stream()
            .reduce(Integer::sum)
            .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        user.updateTotalWalksAndPoint(totalWalks);
    }

    @Transactional
    public List<WalkLog> findWalkLogs(Long userId, Map<LocalDate, Integer> dateWalks) {

        LocalDate startDate = dateWalks.keySet().stream()
            .min(LocalDate::compareTo)
            .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        LocalDate endDate = dateWalks.keySet().stream()
            .max(LocalDate::compareTo)
            .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

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
    public void upsertWalkLogs(User user, Map<LocalDate, Integer> dateWalks) {

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

    public UserFetchDto fetchUser(CustomUser customUser, Long userId) {
        // 인증된 유저의 id 와 요청한 userId 가 일치하는지 확인
        customUser.checkUserId(userId);

        UserSearchCondition condition = new UserSearchCondition();
        condition.setUserId(userId);

        return userRepository.searchUserByCondition(condition)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public MyPageFetchDto fetchMyPage(CustomUser customUser, Long userId) {
        // 인증된 유저의 id 와 요청한 userId 가 일치하는지 확인
        customUser.checkUserId(userId);

        MyPageFetchDto myPageDto = userRepository.searchMyPageById(userId);

        for (TreeFetchDto treeDto : myPageDto.getTrees()) {
            treeDto.getItem().setImagePath(imageUrl + treeDto.getItem().getImagePath());
        }
        return myPageDto;
    }

    @Transactional
    public User updateUser(CustomUser customUser, Long userId, UserUpdateDto dto) {
        // 인증된 유저의 id 와 요청한 userId 가 일치하는지 확인
        customUser.checkUserId(userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (StringUtils.hasText(dto.getUsername())) {
            user.setUsername(dto.getUsername());
        }
        return userRepository.save(user);
    }

}

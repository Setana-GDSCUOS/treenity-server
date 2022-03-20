package org.setana.treenity.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.repository.WalkLogRepository;
import org.setana.treenity.security.model.CustomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalkLogService {

    private final WalkLogRepository walkLogRepository;

    public List<WalkLogFetchDto> fetchUserWalkLogs(CustomUser customUser, Long userId,
        Pageable pageable) {
        // 인증된 유저의 id 와 요청한 userId 가 일치하는지 확인
        customUser.checkUserId(userId);

        return walkLogRepository.searchByUserId(userId, pageable);
    }
}

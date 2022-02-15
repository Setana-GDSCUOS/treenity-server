package org.setana.treenity.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.repository.WalkLogRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalkLogService {

    private final WalkLogRepository walkLogRepository;

    public List<WalkLogFetchDto> fetchUserWalkLogs(Long userId, Pageable pageable) {
        return walkLogRepository.searchByUserId(userId, pageable);
    }
}

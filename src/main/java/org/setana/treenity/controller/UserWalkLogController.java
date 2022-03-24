package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.dto.WalkLogSaveDto;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.UserService;
import org.setana.treenity.service.WalkLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserWalkLogController {

    private final UserService userService;
    private final WalkLogService walkLogService;

    // user-walk-logs
    @GetMapping("/{id}/walk-logs")
    public List<WalkLogFetchDto> getUserWalkLogs(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return walkLogService.fetchUserWalkLogs(customUser, userId, pageable);
    }

    @PostMapping("/{id}/walk-logs")
    public void postUserWalkLogs(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody WalkLogSaveDto dto) {
        userService.convertToPoint(customUser, userId, dto.getDateWalks());
    }

}

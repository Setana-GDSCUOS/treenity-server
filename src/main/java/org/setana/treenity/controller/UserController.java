package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSaveDto;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.dto.WalkLogSaveDto;
import org.setana.treenity.service.ItemService;
import org.setana.treenity.service.TreeService;
import org.setana.treenity.service.UserItemService;
import org.setana.treenity.service.UserService;
import org.setana.treenity.service.WalkLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserItemService userItemService;
    private final TreeService treeService;
    private final WalkLogService walkLogService;

    @GetMapping("/{id}")
    public UserFetchDto getUser(@PathVariable(value = "id") Long userId) {
        return userService.fetchUser(userId);
    }

    @GetMapping("/{id}/my-page")
    public MyPageFetchDto getMyPage(@PathVariable(value = "id") Long userId) {
        return userService.fetchMyPage(userId);
    }

    @GetMapping("/{id}/items")
    public List<UserItemFetchDto> getUserItems(
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable
    ) {
        return userItemService.fetchUserItems(userId, pageable);
    }

    @PostMapping("/{id}/items")
    public void postUserItem(
        @PathVariable(value = "id") Long userId,
        @RequestBody UserItemSaveDto dto
    ) {
        userItemService.purchaseItem(dto.getItemName(), userId);
    }

    @GetMapping("/{id}/trees")
    public List<TreeFetchDto> getUserTrees(
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable
    ) {
        return treeService.fetchUserTrees(userId, pageable);
    }

    @GetMapping("/{id}/walk-logs")
    public List<WalkLogFetchDto> getUserWalkLogs(
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable
    ) {
        return walkLogService.fetchUserWalkLogs(userId, pageable);
    }

    @PostMapping("/{id}/walk-logs")
    public void postUserWalkLogs(
        @PathVariable(value = "id") Long userId,
        @RequestBody WalkLogSaveDto dto
    ) {
        userService.convertToPoint(userId, dto.getDateWalks());
    }

}

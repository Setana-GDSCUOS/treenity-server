package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeUpdateDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSaveDto;
import org.setana.treenity.dto.UserUpdateDto;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.dto.WalkLogSaveDto;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.TreeService;
import org.setana.treenity.service.UserItemService;
import org.setana.treenity.service.UserService;
import org.setana.treenity.service.WalkLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public UserFetchDto getUser(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId) {
        return userService.fetchUser(customUser, userId);
    }

    @PutMapping("/{id}")
    public void putUser(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody UserUpdateDto dto) {
        userService.updateUser(customUser, userId, dto);
    }

    // my page
    @GetMapping("/{id}/my-page")
    public MyPageFetchDto getMyPage(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId) {
        return userService.fetchMyPage(customUser, userId);
    }

    // item
    @GetMapping("/{id}/items")
    public List<UserItemFetchDto> getUserItems(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return userItemService.fetchUserItems(customUser, userId, pageable);
    }

    @PostMapping("/{id}/items")
    public void postUserItem(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody UserItemSaveDto dto) {
        userItemService.purchaseItem(customUser, userId, dto.getItemId());
    }

    // tree
    @GetMapping("/{id}/trees")
    public List<TreeFetchDto> getUserTrees(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return treeService.fetchUserTrees(customUser, userId, pageable);
    }

    @PutMapping("/{userId}/trees/{treeId}")
    public void putTree(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "treeId") Long treeId,
        @RequestBody TreeUpdateDto dto) {
        treeService.updateTree(customUser, userId, treeId, dto);
    }

    // walk-logs
    @GetMapping("/{id}/walk-logs")
    public List<WalkLogFetchDto> getUserWalkLogs(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return walkLogService.fetchUserWalkLogs(customUser, userId, pageable);
    }

    @PostMapping("/{id}/walk-logs")
    public void postUserWalkLogs(
        @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody WalkLogSaveDto dto) {
        userService.convertToPoint(customUser, userId, dto.getDateWalks());
    }

}

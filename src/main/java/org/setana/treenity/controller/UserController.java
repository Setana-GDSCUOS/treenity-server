package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeInteractDto;
import org.setana.treenity.dto.TreeSaveDto;
import org.setana.treenity.dto.TreeUpdateDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSaveDto;
import org.setana.treenity.dto.UserUpdateDto;
import org.setana.treenity.dto.WalkLogFetchDto;
import org.setana.treenity.dto.WalkLogSaveDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.TreeService;
import org.setana.treenity.service.UserItemService;
import org.setana.treenity.service.UserService;
import org.setana.treenity.service.WalkLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId) {
        return userService.fetchUser(customUser, userId);
    }

    @PutMapping("/{id}")
    public void putUser(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody UserUpdateDto dto) {
        userService.updateUser(customUser, userId, dto);
    }

    // my page
    @GetMapping("/{id}/my-page")
    public MyPageFetchDto getMyPage(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId) {
        return userService.fetchMyPage(customUser, userId);
    }

    // user-item
    @GetMapping("/{id}/items")
    public List<UserItemFetchDto> getUserItems(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return userItemService.fetchUserItems(customUser, userId, pageable);
    }

    @PostMapping("/{id}/items")
    public void postUserItem(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody UserItemSaveDto dto) {
        userItemService.purchaseItem(customUser, userId, dto.getItemId());
    }

    // user-tree
    @GetMapping("/{id}/trees")
    public List<TreeFetchDto> getUserTrees(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @PageableDefault Pageable pageable) {
        return treeService.fetchUserTrees(customUser, userId, pageable);
    }

    @PostMapping("/{id}/trees")
    public TreeFetchDto postTreePlant(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long userId,
        @RequestBody TreeSaveDto dto) {
        Location location = new Location(dto.getLongitude(), dto.getLatitude());
        return treeService.plantTree(customUser, userId, location, dto);
    }

    @PutMapping("/{userId}/trees/{treeId}")
    public void putTree(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "treeId") Long treeId,
        @RequestBody TreeUpdateDto dto) {
        treeService.updateTree(customUser, userId, treeId, dto);
    }

    @PostMapping("/{userId}/trees/{treeId}/interact")
    public void postTreeInteract(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "treeId") Long treeId,
        @RequestBody TreeInteractDto dto) {
        // TODO: cloud anchor 아이디 포함된 DTO 제거 필요
        treeService.interactTree(customUser, userId, treeId);
    }

    @PostMapping("/{userId}/trees/{treeId}/bookmark")
    public void postTreeBookmark(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "treeId") Long treeId) {
        treeService.addBookmark(customUser, userId, treeId);
    }

    @DeleteMapping("/{userId}/trees/{treeId}/bookmark")
    public void deleteTreeBookmark(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "treeId") Long treeId) {
        treeService.deleteBookmark(customUser, userId, treeId);
    }

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

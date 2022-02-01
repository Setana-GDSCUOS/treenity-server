package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.service.TreeService;
import org.setana.treenity.service.UserItemService;
import org.setana.treenity.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserItemService userItemService;
    private final TreeService treeService;

    @GetMapping("/{id}")
    public MyPageFetchDto getMyPage(@PathVariable(value = "id") Long userId) {
        return userService.fetchMyPage(userId);
    }

    @GetMapping("/{id}/items")
    public List<UserItemFetchDto> getUserItems(@PathVariable(value = "id") Long userId) {
        return userItemService.fetchUserItems(userId);
    }

    @GetMapping("/{id}/trees")
    public List<TreeFetchDto> getUserTrees(@PathVariable(value = "id") Long userId) {
        return treeService.fetchUserTrees(userId);
    }

}

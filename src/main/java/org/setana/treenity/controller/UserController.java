package org.setana.treenity.controller;

import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserUpdateDto;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}

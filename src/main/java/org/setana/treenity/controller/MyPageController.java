package org.setana.treenity.controller;

import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.MyPageFetchDto;
import org.setana.treenity.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;

    @GetMapping()
    public MyPageFetchDto getMyPage(@RequestParam(value = "userId") Long userId) {
        return userService.fetchMyPage(userId);
    }

}

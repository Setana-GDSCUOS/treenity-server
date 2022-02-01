package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.service.UserItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserItemService userItemService;

    @GetMapping("/{id}/items")
    public List<UserItemFetchDto> getUserItems(@PathVariable(value = "id") Long userId) {
        return userItemService.fetchUserItems(userId);
    }

}

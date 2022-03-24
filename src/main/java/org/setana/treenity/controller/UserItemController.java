package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSaveDto;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.UserItemService;
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
public class UserItemController {

    private UserItemService userItemService;

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

}

package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeInteractDto;
import org.setana.treenity.dto.TreeListDto;
import org.setana.treenity.dto.TreeSaveDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.service.TreeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping
    public List<TreeListDto> getTreesByLocation(
        @AuthenticationPrincipal CustomUser customUser,
        @RequestParam Double longitude,
        @RequestParam Double latitude
    ) {
        return treeService.fetchByLocation(customUser, new Location(longitude, latitude));
    }

    @GetMapping("{id}")
    public TreeFetchDto getTree(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @PathVariable(value = "id") Long treeId) {
        return treeService.fetchTree(customUser, treeId);
    }

    @PostMapping
    public void postTreePlant(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @RequestBody TreeSaveDto dto) {
        Location location = new Location(dto.getLongitude(), dto.getLatitude());
        treeService.plantTree(customUser, location, dto);
    }

    @PostMapping("/interact")
    public void postTreeInteract(
        @ApiIgnore @AuthenticationPrincipal CustomUser customUser,
        @RequestBody TreeInteractDto dto) {
        treeService.interactTree(customUser, dto.getUserId(), dto.getTreeId());
    }
}

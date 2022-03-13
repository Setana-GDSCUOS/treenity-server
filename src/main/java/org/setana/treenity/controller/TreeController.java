package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeInteractDto;
import org.setana.treenity.dto.TreeListFetchDto;
import org.setana.treenity.dto.TreeSaveDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.service.TreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping
    public List<TreeListFetchDto> getTreesByLocation(
        @RequestParam Double longitude,
        @RequestParam Double latitude
    ) {
        return treeService.fetchByLocation(new Location(longitude, latitude));
    }

    @GetMapping("{id}")
    public TreeFetchDto getTree(@PathVariable(value = "id") Long treeId) {
        return treeService.fetchTree(treeId);
    }

    @PostMapping
    public void postTreePlant(@RequestBody TreeSaveDto dto) {
        Location location = new Location(dto.getLongitude(), dto.getLatitude());
        treeService.plantTree(location, dto.getCloudAnchorId(), dto.getUserItemId());
    }

    @PostMapping("/{id}/interact")
    public void postTreeInteract(
        @PathVariable(value = "id") Long treeId,
        @RequestBody TreeInteractDto dto) {
        treeService.interactTree(treeId, dto.getCloudAnchorId(), dto.getUserId());
    }
}

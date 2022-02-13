package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeListFetchDto;
import org.setana.treenity.model.Location;
import org.setana.treenity.service.TreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping
    public List<TreeListFetchDto> getTreeByLocation(
        @RequestParam Double longitude,
        @RequestParam Double latitude
    ) {
      return treeService.fetchByLocation(new Location(longitude, latitude));
    }
}

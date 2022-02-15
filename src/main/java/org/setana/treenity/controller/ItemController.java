package org.setana.treenity.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.ItemFetchDto;
import org.setana.treenity.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemFetchDto> getItems() {
        return itemService.fetchItems();
    }

    @GetMapping("/{id}")
    public ItemFetchDto getItem(@PathVariable(value = "id") Long itemId) {
        return itemService.fetchItem(itemId);
    }

}

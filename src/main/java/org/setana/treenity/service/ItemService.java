package org.setana.treenity.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.ItemFetchDto;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${spring.upload.url:${user.home}}")
    private String imageUrl;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public UserItem purchaseItem(String name, Long userId) throws IllegalStateException {
        Item item = itemRepository.findByName(name)
            .orElseThrow(IllegalStateException::new);

        User user = userRepository.findById(userId)
            .orElseThrow(IllegalStateException::new);

        UserItem userItem = user.createUserItem(item);
        return userItemRepository.save(userItem);
    }

    public List<ItemFetchDto> fetchItems() {
        List<ItemFetchDto> dtos = itemRepository.findAllItems();

        for (ItemFetchDto dto: dtos) {
            dto.setImagePath(imageUrl + dto.getImagePath());
        }
        return dtos;
    }

    public ItemFetchDto fetchItem(Long itemId) {
        ItemFetchDto dto = itemRepository.findByItemId(itemId);
        dto.setImagePath(imageUrl + dto.getImagePath());
        return dto;
    }

}

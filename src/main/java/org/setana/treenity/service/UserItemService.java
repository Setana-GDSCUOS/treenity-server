package org.setana.treenity.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.dto.UserItemSearchCondition;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserItemService {

    @Value("${spring.upload.url:${user.home}}")
    private String imageUrl;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public UserItem purchaseItem(String itemName, Long userId) throws IllegalStateException {

        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserId(userId);
        condition.setItemName(itemName);

        Optional<UserItem> findUserItem = userItemRepository.search(condition);

        if (findUserItem.isPresent()) {
            UserItem userItem = findUserItem.get();
            userItem.purchase();
            return userItem;
        } else {
            return createUserItem(itemName, userId);
        }
    }

    private UserItem createUserItem(String itemName, Long userId) throws IllegalStateException {
        Item item = itemRepository.findByName(itemName)
            .orElseThrow(IllegalStateException::new);

        User user = userRepository.findById(userId)
            .orElseThrow(IllegalStateException::new);

        UserItem userItem = user.createUserItem(item);
        return userItemRepository.save(userItem);
    }

    public List<UserItemFetchDto> fetchUserItems(Long userId, Pageable pageable) {
        List<UserItemFetchDto> dtos = userItemRepository.findByUserId(userId, pageable);

        for (UserItemFetchDto dto : dtos) {
            dto.setImagePath(imageUrl + dto.getImagePath());
        }
        return dtos;
    }

}

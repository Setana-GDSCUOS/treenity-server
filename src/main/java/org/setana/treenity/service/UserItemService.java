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
    public UserItem purchaseItem(Long userId, Long itemId) throws IllegalStateException {

        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserId(userId);
        condition.setItemId(itemId);

        Optional<UserItem> findUserItem = userItemRepository.search(condition);
        UserItem userItem = findUserItem.orElseGet(() -> createUserItem(userId, itemId));

        userItem.purchase();
        return userItem;
    }

    private UserItem createUserItem(Long userId, Long itemId) throws IllegalStateException {
        User user = userRepository.findById(userId)
            .orElseThrow(IllegalStateException::new);

        Item item = itemRepository.findById(itemId)
            .orElseThrow(IllegalStateException::new);

        UserItem userItem = new UserItem(user, item);
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

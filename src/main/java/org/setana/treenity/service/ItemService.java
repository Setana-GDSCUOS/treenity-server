package org.setana.treenity.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public UserItem purchaseItem(String itemName, Long userId) throws IllegalStateException {
        Item item = itemRepository.findByItemName(itemName)
            .orElseThrow(IllegalStateException::new);

        User user = userRepository.findById(userId)
            .orElseThrow(IllegalStateException::new);

        UserItem userItem = user.createUserItem(item);
        return userItemRepository.save(userItem);
    }

}
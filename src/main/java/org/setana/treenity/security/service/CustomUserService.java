package org.setana.treenity.security.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Item;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;
import org.setana.treenity.exception.NotFoundException;
import org.setana.treenity.repository.ItemRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.security.model.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final static String WATER_ITEM = "water";
    private final static String TUTORIAL_ITEM = "tutorial";

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        // 데이터베이스에서 user 반환
        User user = userRepository.findByUid(username)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 매일 로그인 시마다 water 아이템 제공
        updateItem(user, WATER_ITEM);

        // last login 업데이트
        user.updateLastLogin();
        return new CustomUser(user);
    }

    @Transactional
    public CustomUser register(String uid, String email, String nickname) {
        // 데이터베이스에서 uid 로 중복 유저 찾기
        userRepository.findByUid(uid)
            .ifPresent((user) -> {
                throw new NotAcceptableException(ErrorCode.USER_DUPLICATE);
            });

        // 가입 시 유저에게 1000 포인트 지급
        User user = new User(uid, email, nickname, 1_000);
        userRepository.save(user);

        // 가입 시 water/tutorial 아이템 제공
        createItem(user, WATER_ITEM);
        createItem(user, TUTORIAL_ITEM);

        // last login 업데이트
        user.updateLastLogin();

        return new CustomUser(user);
    }

    private void createItem(User user, String itemName) {
        Item item = itemRepository.findByName(itemName)
            .orElseThrow(() -> new NotFoundException(ErrorCode.ITEM_NOT_FOUND));

        provide(new UserItem(user, item));
    }

    private void updateItem(User user, String itemName) {
        UserItem userItem = userItemRepository.findByUserAndItem_Name(user, itemName)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ITEM_NOT_FOUND));

        provide(userItem);
    }

    private void provide(UserItem userItem) {
        userItem.provide();
        userItemRepository.save(userItem);
    }
}

package org.setana.treenity.security.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.dto.UserSearchCondition;
import org.setana.treenity.entity.User;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.security.model.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // interface 구현 시 오퍼레이션에서 인자로 username 을 받지만, 실제로는 uid 를 입력
        UserSearchCondition condition = new UserSearchCondition();
        condition.setUid(username);

        UserFetchDto dto = userRepository.searchUserByCondition(condition)
            .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        return new CustomUser(dto);
    }

    @Transactional
    public CustomUser register(String uid, String email, String nickname) {
        User user = new User(uid, email, nickname);
        userRepository.save(user);

        UserSearchCondition condition = new UserSearchCondition();
        condition.setUid(uid);

        UserFetchDto dto = userRepository.searchUserByCondition(condition)
            .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        return new CustomUser(dto);
    }

}

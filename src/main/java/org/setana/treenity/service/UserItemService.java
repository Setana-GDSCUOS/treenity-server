package org.setana.treenity.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.UserItemFetchDto;
import org.setana.treenity.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserItemService {

    @Value("${spring.upload.url:${user.home}}")
    private String imageUrl;

    private final UserItemRepository userItemRepository;

    public List<UserItemFetchDto> fetchUserItems(Long userId) {
        List<UserItemFetchDto> dtos = userItemRepository.findByUserId(userId);

        for (UserItemFetchDto dto : dtos) {
            dto.setImagePath(imageUrl + dto.getImagePath());
        }
        return dtos;
    }

}

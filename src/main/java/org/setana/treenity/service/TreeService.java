package org.setana.treenity.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.UserItemSearchCondition;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;
import org.setana.treenity.repository.TreeRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreeService {

    @Value("${spring.upload.url:${user.home}}")
    private String imageUrl;

    private final TreeRepository treeRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public Tree plantTree(Location location, String cloudAnchorId, String name, Long userItemId)
        throws IllegalArgumentException {

        TreeCluster treeCluster = treeRepository.searchTreeCluster(location);
        treeCluster.validatePlant();

        // 클라이언트에서 유저가 가진 아이템 중 아이템 타입이 SEED 인 아이템 하나를 선택해 나무 심기
        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserItemId(userItemId);
        condition.setItemType(ItemType.SEED);

        UserItem userItem = userItemRepository.search(condition)
            .orElseThrow(IllegalArgumentException::new);
        userItem.consume();

        Tree tree = new Tree(location, cloudAnchorId, name, userItem.getUser(), userItem.getItem());
        return treeRepository.save(tree);
    }

    @Transactional
    public Tree interactTree(Long treeId, String cloudAnchorId, Long userId)
        throws IllegalArgumentException {

        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(IllegalArgumentException::new);
        tree.updateCloudAnchorId(cloudAnchorId);

        // 데이터베이스에서 유저가 가진 아이템 중 아이템 타입이 WATER 인 아이템 가져오기
        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserId(userId);
        condition.setItemType(ItemType.WATER);

        UserItem userItem = userItemRepository.search(condition)
            .orElseThrow(IllegalArgumentException::new);
        userItem.apply(tree);

        return tree;
    }

    public List<TreeFetchDto> fetchByLocation(Location location) {
        return treeRepository.searchByLocation(location);
    }

    public List<TreeFetchDto> fetchUserTrees(Long userId, Pageable pageable) {
        List<TreeFetchDto> dtos = treeRepository.searchByUserId(userId, pageable);

        for (TreeFetchDto dto : dtos) {
            dto.getItem().setImagePath(imageUrl + dto.getItem().getImagePath());
        }
        return dtos;
    }

    public TreeFetchDto fetchTree(Long treeId) {
        TreeFetchDto dto = treeRepository.searchByTreeId(treeId);
        dto.getItem().setImagePath(imageUrl + dto.getItem().getImagePath());
        return dto;
    }

}

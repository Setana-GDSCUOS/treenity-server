package org.setana.treenity.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeListFetchDto;
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
    public Tree plantTree(Location location, String cloudAnchorId, Long userItemId)
        throws IllegalArgumentException {

        TreeCluster treeCluster = treeRepository.searchTreeCluster(location);
        treeCluster.validatePlant();

        // TODO: UserItem 에서 아이템 타입이 SEED 인 아이템 중 하나를 선택해 나무 심기 필요
        UserItem userItem = userItemRepository.findById(userItemId)
            .orElseThrow(IllegalArgumentException::new);
        userItem.consume();

        Tree tree = new Tree(location, cloudAnchorId, userItem.getUser(), userItem.getItem());
        return treeRepository.save(tree);
    }

    @Transactional
    public Tree interactTree(Long treeId, String cloudAnchorId, Long userId)
        throws IllegalArgumentException {

        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(IllegalArgumentException::new);
        tree.updateCloudAnchorId(cloudAnchorId);

        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserId(userId);
        condition.setItemType(ItemType.WATER);

        UserItem userItem = userItemRepository.search(condition)
            .orElseThrow(IllegalArgumentException::new);
        userItem.consume(tree);

        return tree;
    }

    public List<TreeListFetchDto> fetchByLocation(Location location) {
        return treeRepository.searchByLocation(location);
    }

    public List<TreeFetchDto> fetchUserTrees(Long userId, Pageable pageable) {
        List<TreeFetchDto> dtos = treeRepository.findByUserId(userId, pageable);

        for (TreeFetchDto dto : dtos) {
            dto.getItem().setImagePath(imageUrl + dto.getItem().getImagePath());
        }
        return dtos;
    }

}

package org.setana.treenity.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.entity.Location;
import org.setana.treenity.model.TreeCluster;
import org.setana.treenity.repository.TreeRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public Tree plantTree(Location location, Long userItemId) throws IllegalArgumentException {

        TreeCluster treeCluster = treeRepository.searchByLocation(location);
        treeCluster.validatePlant();

        // TODO: UserItem 에서 아이템 타입이 SEED 인 아이템 중 하나를 선택해 나무 심기 필요
        UserItem userItem = userItemRepository.findById(userItemId)
            .orElseThrow(IllegalArgumentException::new);
        userItem.consume();

        Tree tree = new Tree(location, userItem.getUser(), userItem.getItem());
        return treeRepository.save(tree);
    }

    @Transactional
    public Tree interactTree(Long treeId, Long userItemId) throws IllegalArgumentException {

        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(IllegalArgumentException::new);

        // TODO: UserItem 에서 아이템 타입이 WATER 인 아이템 중 하나를 선택해 상호작용 필요
        UserItem userItem = userItemRepository.findById(userItemId)
            .orElseThrow(IllegalArgumentException::new);
        userItem.consume(tree);

        return tree;
    }

}

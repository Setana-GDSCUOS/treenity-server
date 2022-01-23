package org.setana.treenity.service;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.model.Location;
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
    public Tree plantTree(Location location, Long userItemId) {

        TreeCluster treeCluster = treeRepository.searchByLocation(location);
        treeCluster.validatePlant();

        // TODO: 유저가 가진 아이템인 UserItem 을 선택해 나무를 생성하는지, 선택하지 않고 자동으로 사용하는지 조사
        UserItem userItem = userItemRepository.findById(userItemId)
            .orElseThrow(IllegalStateException::new);
        userItem.consume();

        System.out.println("user :" + userItem.getUser());
        Tree tree = new Tree(location, userItem.getUser(), userItem.getItem());
        return treeRepository.save(tree);
    }

}

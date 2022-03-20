package org.setana.treenity.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.dto.TreeFetchDto;
import org.setana.treenity.dto.TreeListDto;
import org.setana.treenity.dto.TreeSaveDto;
import org.setana.treenity.dto.TreeUpdateDto;
import org.setana.treenity.dto.UserItemSearchCondition;
import org.setana.treenity.entity.ItemType;
import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserItem;
import org.setana.treenity.entity.UserTree;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;
import org.setana.treenity.exception.NotFoundException;
import org.setana.treenity.model.Location;
import org.setana.treenity.model.TreeCluster;
import org.setana.treenity.repository.TreeRepository;
import org.setana.treenity.repository.UserItemRepository;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.repository.UserTreeRepository;
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
    private final UserRepository userRepository;
    private final UserTreeRepository userTreeRepository;

    @Transactional
    public Tree plantTree(Location location, TreeSaveDto dto) {
        TreeCluster treeCluster = treeRepository.searchTreeCluster(dto.getUserId(), location);
        treeCluster.validatePlant();

        // 클라이언트에서 유저가 가진 아이템 중 아이템 타입이 SEED 인 아이템 하나를 선택해 나무 심기
        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserItemId(dto.getUserItemId());
        condition.setItemType(ItemType.SEED);

        UserItem userItem = userItemRepository.search(condition)
            .orElseThrow(() -> new NotFoundException(ErrorCode.ITEM_TYPE_CHECK_FAIL));

        // 요청한 아이템이 유저의 소유인지 검증
        userItem.checkUserId(dto.getUserId());
        userItem.consume();

        Tree tree = new Tree(
            location,
            dto.getCloudAnchorId(),
            dto.getTreeName(),
            userItem.getUser(),
            userItem.getItem()
        );
        return treeRepository.save(tree);
    }

    @Transactional
    public Tree interactTree(Long treeId, String cloudAnchorId, Long userId) {

        Tree tree = treeRepository.findByIdWithinUser(treeId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TREE_NOT_FOUND));

        // 나무에 물을 줄 때 새로운 cloud anchor 아이디 업데이트
        updateCloudAnchorId(tree, cloudAnchorId);

        // 데이터베이스에서 유저가 가진 아이템 중 아이템 타입이 WATER 인 아이템 가져오기
        UserItemSearchCondition condition = new UserItemSearchCondition();
        condition.setUserId(userId);
        condition.setItemType(ItemType.WATER);

        UserItem userItem = userItemRepository.search(condition)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ITEM_NOT_FOUND));
        userItem.apply(tree);

        return tree;
    }

    private void updateCloudAnchorId(Tree tree, String cloudAnchorId) {
        if (cloudAnchorId != null) {
            tree.setCloudAnchorId(cloudAnchorId);
        }
    }

    public List<TreeListDto> fetchByLocation(Long userId, Location location) {
        return treeRepository.searchByLocation(userId, location);
    }

    public List<TreeFetchDto> fetchUserTrees(Long userId, Pageable pageable) {
        List<TreeFetchDto> dtos = treeRepository.searchByUserId(userId, pageable);

        for (TreeFetchDto dto : dtos) {
            dto.getItem().setImagePath(imageUrl + dto.getItem().getImagePath());
        }
        return dtos;
    }

    public TreeFetchDto fetchTree(Long userId, Long treeId) {
        TreeFetchDto dto = treeRepository.searchByTreeId(userId, treeId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TREE_NOT_FOUND));

        dto.getItem().setImagePath(imageUrl + dto.getItem().getImagePath());
        return dto;
    }

    public void updateTree(Long userId, Long treeId, TreeUpdateDto dto) {
        Tree tree = treeRepository.findByIdAndUser_Id(treeId, userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TREE_NOT_FOUND));

        if (dto.getBookmark() == Boolean.TRUE) {
            addBookmark(tree.getUser(), tree);
        }
        if (dto.getBookmark() == Boolean.FALSE) {
            deleteBookmark(tree.getUser(), tree);
        }
        if (dto.getTreeName() != null) {
            tree.setName(dto.getTreeName());
        }
        if (dto.getTreeDescription() != null) {
            tree.setDescription(dto.getTreeDescription());
        }
        treeRepository.save(tree);
    }

    private void addBookmark(User user, Tree tree) {
        userTreeRepository.findByUserAndTree(user, tree)
            .ifPresent((userTree) -> {
                throw new NotAcceptableException(ErrorCode.USER_TREE_DUPLICATE);
            });

        userTreeRepository.save(new UserTree(user, tree));
    }

    private void deleteBookmark(User user, Tree tree) {
        userTreeRepository.deleteByUserAndTree(user, tree);
    }

}

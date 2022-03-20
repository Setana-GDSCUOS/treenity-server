package org.setana.treenity.service;

import org.setana.treenity.entity.Tree;
import org.setana.treenity.entity.User;
import org.setana.treenity.entity.UserTree;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;
import org.setana.treenity.exception.NotFoundException;
import org.setana.treenity.repository.TreeRepository;
import org.setana.treenity.repository.UserRepository;
import org.setana.treenity.repository.UserTreeRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTreeService {

    private final UserTreeRepository userTreeRepository;
    private final UserRepository userRepository;
    private final TreeRepository treeRepository;

    public UserTreeService(UserTreeRepository userTreeRepository, UserRepository userRepository,
        TreeRepository treeRepository) {
        this.userTreeRepository = userTreeRepository;
        this.userRepository = userRepository;
        this.treeRepository = treeRepository;
    }

    public UserTree addBookmark(Long userId, Long treeId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TREE_NOT_FOUND));

        userTreeRepository.findByUserAndTree(user, tree)
            .ifPresent((userTree) -> {
                throw new NotAcceptableException(ErrorCode.USER_TREE_DUPLICATE);
            });

        return userTreeRepository.save(new UserTree(user, tree));
    }

    public void deleteBookmark(Long userId, Long treeId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TREE_NOT_FOUND));

        userTreeRepository.deleteByUserAndTree(user, tree);
    }

}

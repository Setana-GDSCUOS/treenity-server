package org.setana.treenity.dto;

import lombok.Data;

@Data
public class TreeInteractDto {

    // TODO: cloudAnchorId 제거 필요
    private String cloudAnchorId;
    private Long userId;
    private Long treeId;
}

package org.setana.treenity.dto;

import lombok.Data;

@Data
public class TreeSaveDto {

    private Double longitude;
    private Double latitude;
    private String cloudAnchorId;
    private String name;
    private Long userItemId;

}

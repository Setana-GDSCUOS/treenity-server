package org.setana.treenity.dto;

import java.util.List;
import lombok.Data;

@Data
public class MyPageFetchDto {

    private UserFetchDto user;
    private List<TreeFetchDto> trees;
    private List<WalkLogFetchDto> walkLogs;

}

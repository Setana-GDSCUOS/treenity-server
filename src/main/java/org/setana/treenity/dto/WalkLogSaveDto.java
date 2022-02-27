package org.setana.treenity.dto;

import java.time.LocalDate;
import java.util.Map;
import lombok.Data;

@Data
public class WalkLogSaveDto {

    private Map<LocalDate, Integer> dateWalks;

}

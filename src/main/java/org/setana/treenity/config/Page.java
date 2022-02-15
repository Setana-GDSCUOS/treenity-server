package org.setana.treenity.config;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class Page {

    private Integer page;
    private Integer size;

}

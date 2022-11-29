package com.example.common.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Pattern(
            regexp = "^\\d*$",
            message = "pageSize应为正整数"
    )
    private String pageSize = "20";

    @Pattern(
            regexp = "^\\d*$",
            message = "pageNumber应为正整数"
    )
    private String pageNumber = "1";
}

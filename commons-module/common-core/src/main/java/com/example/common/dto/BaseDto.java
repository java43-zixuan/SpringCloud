package com.example.common.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Pattern(regexp = "^\\d*$",message = "pageSize应为正整数")
    private String pageSize = "20";

    @Pattern(regexp = "^\\d*$",message = "pageNumber应为正整数")
    private String pageNumber = "1";

    public String getPageSize() {
        if (this.isEmpty(this.pageSize)) {
            this.pageSize = "20";
        }
        return this.pageSize;
    }

    public String getPageNumber() {
        if (this.isEmpty(this.pageNumber)) {
            this.pageNumber = "1";
        }
        return this.pageNumber;
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}

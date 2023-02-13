package com.example.common.dto;

import com.example.common.validatedGroup.DeleteGroup;
import com.example.common.validatedGroup.InsertGroup;
import com.example.common.validatedGroup.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Api(tags = "validated校验入参")
@Data
public class ValidatedDto {

    @ApiModelProperty(value = "主键id",required = true)
    @NotNull(groups = {UpdateGroup.class},message = "主键不能为空")
    @Range(groups = {UpdateGroup.class},min = 1,max=10,message = "主键范围：1~10")
    private Integer id;

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(groups = {InsertGroup.class, DeleteGroup.class},message = "手机号不能为空")
    @Pattern(groups = {InsertGroup.class,DeleteGroup.class},regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$",message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "邮箱",required = true)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "用户登录信息",required = true)
    @Valid //如果需要校验子类里的属性，引入该注解即可
    private LoginDto loginDto;
}

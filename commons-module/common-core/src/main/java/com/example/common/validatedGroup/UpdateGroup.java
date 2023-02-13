package com.example.common.validatedGroup;

import javax.validation.groups.Default;

/**
 * <p>
 * 使用groups的校验
 * </p>
 *
 * 同一个dto对象要复用，在保存的时候不需要校验id,在修改的时候需要校验id,这个时候就可以使用groups了
 * 在需要校验的地方@Validated声明校验组update(@RequestBody @Validated(UpdateGroup.class) ValidatedDto dto)
 * 在dto中的字段上定义好groups={}的分组类型@NotNull(groups = {UpdateGroup.class},message = "主键不能为空")
 * 继承Default 则会默认把所有字段进行判断。InsertGroup跟DeleteGroup 中没有继承Default 则只有配置了分组的字段才会进行相关校验
 */
public interface UpdateGroup extends Default {
}

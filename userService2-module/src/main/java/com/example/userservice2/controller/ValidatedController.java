package com.example.userservice2.controller;

import com.example.common.dto.ValidatedDto;
import com.example.common.validatedGroup.DeleteGroup;
import com.example.common.validatedGroup.InsertGroup;
import com.example.common.validatedGroup.UpdateGroup;
import com.example.common.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Validated校验
 * controller 入参使用@Validated注解，配合全局异常处理类ExceptionAspect（该类需要注入到Spring容器中，启动类需要扫描到ExceptionAspect）
 * 同一个类实现（添加、删除、修改）等不同的入参校验，可以配置UpdateGroup分组
 */

@Api(tags = "Validated校验的测试接口")
@RestController
@RequestMapping("/validated") //路由路径
public class ValidatedController {

    @PostMapping("/add")
    public ResultVO add(@RequestBody @Validated(InsertGroup.class) ValidatedDto dto){
        return ResultVO.success();
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody @Validated(UpdateGroup.class) ValidatedDto dto){
        return ResultVO.success();
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody @Validated(DeleteGroup.class) ValidatedDto dto){
        return ResultVO.success();
    }
}

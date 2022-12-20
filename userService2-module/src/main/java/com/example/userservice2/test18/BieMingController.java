package com.example.userservice2.test18;

import com.example.common.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "别名测试")
@RestController
@RequestMapping("/bieming")
public class BieMingController {

    @PostMapping("/post")
    public ResultVO<Bieming> post(@RequestBody Bieming bieming){
        System.out.println(bieming.getUserName());
        System.out.println(bieming.getUserPwd());
        return ResultVO.success(bieming);
    }

    @PostMapping("/get")
    public ResultVO get(@RequestParam("user_name") String userName){
        System.out.println(userName);
        return ResultVO.success();
    }

    /**
     * 需要使用自定义注解去处理 @RequestParamAlias
     * @param bieming
     * @return
     */
    @PostMapping("/get2")
    public ResultVO get2(@ModelAttribute Bieming bieming){
        return ResultVO.success();
    }
}

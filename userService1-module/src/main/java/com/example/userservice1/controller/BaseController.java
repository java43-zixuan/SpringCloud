package com.example.userservice1.controller;

import com.example.userservice1.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc: 基于BaseService，单表CURD的基础Controller
 * @author: web
 * @date: 2022-07-18
 **/
@Slf4j
public class BaseController<Service extends BaseServiceImpl, Entity> {
    @Autowired
    protected HttpServletRequest request;
    
    @Autowired
    protected Service baseService;

//    @ResponseBody
//    @RequestMapping(value = "",method = RequestMethod.POST)
//    public ResultVO<Object> add(@RequestBody Entity entity){
//        baseService.save(entity);
//        return ResultVO.success();
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
//    public ResultVO<Entity> get(@PathVariable int id){
//        return (ResultVO<Entity>) ResultVO.success(baseService.getById(id));
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
//    public ResultVO<Boolean> update(@RequestBody Entity entity){
//        return ResultVO.success( baseService.saveOrUpdate(entity));
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
//    public ResultVO<Boolean> remove(@PathVariable int id){
//        return ResultVO.success( baseService.removeById(id));
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/all",method = RequestMethod.GET)
//    public ResultVO<List<Entity>> all(){
//        return ResultVO.success(baseService.list());
//    }
//
//    @RequestMapping(value = "/page",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultVO list(@RequestParam Map<String, Object> params){
//        //查询列表数据
//        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(params);
//
//        IPage page = baseService.page(new Page(1,10), queryWrapper);
//        return ResultVO.success(new PageResult(page.getCurrent(),page.getSize(),page.getTotal(),page.getPages(),page.getRecords()));
//    }
//
//    public String getCurrentUserName(){
//        return BaseContextHandler.getUsername();
//    }
}

package com.example.userservice1.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.constant.UnitConstants;
import com.example.common.dto.UserDto;
import com.example.common.exception.ErrorResponse;
import com.example.common.exception.ServiceException;
import com.example.common.utils.ReadExcelUtil;
import com.example.common.vo.OpenResponse;
import com.example.common.vo.Response;
import com.example.common.vo.ReturnCode;
import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.common.constant.UnitConstants.*;

@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/userService1")
public class UserController {

    String EXCEL_SUFFIX = "xls,xlsx,xlsm";

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取用户列表")
    @PostMapping("/getUserList")
    public Response getUserList(@RequestBody @Validated UserDto dto){
        log.info("获取用户列表:{}", JSON.toJSONString(dto));
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda();
        wrapper.orderByDesc(User::getId);
        if(StringUtils.isNotBlank(dto.getUserName())){
            wrapper.like(User::getUserName,dto.getUserName());
        }
        if(StringUtils.isNotBlank(dto.getStudentNum())){
            wrapper.like(User::getStudentNum,dto.getStudentNum());
        }
        if(StringUtils.isNotBlank(dto.getAge())){
            wrapper.like(User::getAge,dto.getAge());
        }
        if(StringUtils.isNotBlank(dto.getClassName())){
            wrapper.like(User::getClassName,dto.getClassName());
        }
        Page page = new Page(Long.valueOf(dto.getPageNumber()),Long.valueOf(dto.getPageSize()));
        IPage result = userService.page(page, wrapper);

        return Response.success(result,SUCCESS_CODE, SUCCESS, BLANK);
    }

    @ApiOperation(value = "添加用户信息")
    @PostMapping("/addUser")
    public Response addUser(@RequestBody @Validated UserDto dto){

        log.info("添加用户信息:{}", JSON.toJSONString(dto));
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setAge(dto.getAge());
        user.setClassName(dto.getClassName());
        user.setStudentNum(dto.getStudentNum());
        user.setUserPwd(dto.getUserPwd());
        return Response.success(userService.save(user),SUCCESS_CODE, SUCCESS, BLANK);
    }
    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateUser")
    public Response updateUser(@RequestBody @Validated UserDto dto){

        log.info("修改用户信息:{}", JSON.toJSONString(dto));

        if (dto == null || dto.getId() == null) {
            throw new ServiceException("缺少重要参数（用户id）");
        }

        LambdaUpdateWrapper<User> wrapper = new UpdateWrapper<User>().lambda().eq(User::getId,dto.getId());
        if(StringUtils.isNotBlank(dto.getUserName())){
            wrapper.set(User::getUserName,dto.getUserName());
        }
        if(StringUtils.isNotBlank(dto.getStudentNum())){
            wrapper.set(User::getStudentNum,dto.getStudentNum());
        }
        if(StringUtils.isNotBlank(dto.getClassName())){
            wrapper.set(User::getClassName,dto.getClassName());
        }
        if(StringUtils.isNotBlank(dto.getAge())){
            wrapper.set(User::getAge,dto.getAge());
        }
        if(StringUtils.isNotBlank(dto.getUserPwd())){
            wrapper.set(User::getUserPwd,dto.getUserPwd());
        }
        Boolean flag = userService.update(new User(),wrapper);
        if(!flag){
            return Response.fail(UnitConstants.SOH_RESULT_ERROR_RESPONSE_CODE, ReturnCode.BATTERY_ERROR_TARGET_ID_ERROR);
        }
        return Response.success(SUCCESS_CODE, SUCCESS, BLANK);
    }

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/getUserDetail")
    public Response getUserDetail(@RequestParam Integer id){
        log.info("查询用户详情:{}", id);

        User user = userService.getById(id);
        if(user == null){
            return Response.fail(UnitConstants.SOH_RESULT_ERROR_RESPONSE_CODE, ReturnCode.BATTERY_ERROR_TARGET_ID_ERROR);
        }
        return Response.success(user,SUCCESS_CODE, SUCCESS, BLANK);
    }
    @ApiOperation(value = "删除用户信息")
    @GetMapping("/delUser")
    public Response delUser(@RequestParam Integer id){
        log.info("删除用户信息:{}", id);
        Boolean flag = userService.update(new User(),new UpdateWrapper<User>().lambda().eq(User::getId,id).set(User::getDelFlag,1));
        if(!flag){
            return Response.fail(UnitConstants.SOH_RESULT_ERROR_RESPONSE_CODE, ReturnCode.BATTERY_ERROR_TARGET_ID_ERROR);
        }
        return Response.success(SUCCESS_CODE, SUCCESS, BLANK);
    }

    @ApiOperation(value = "上传用户信息")
    @PostMapping("/importUser")
    public Response importUser(MultipartFile file){
        if (file == null) {
            throw new ServiceException("文件不能为空");
        }
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!EXCEL_SUFFIX.contains(fileSuffix)) {
            throw new ServiceException("上传的文件格式不符");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ReadExcelUtil excelReader = new ReadExcelUtil(file.getInputStream(), file.getOriginalFilename());
            list = excelReader.readExcelContent(0, 0);
            if (list.size() > 0) {
                List<User> userList = new ArrayList<>();

                for(int i = 0; i < list.size(); i++){
                    Map<String, Object> rowData = list.get(i);
                    String userName = String.valueOf(rowData.get("0")).trim();
                    String age = String.valueOf(rowData.get("1")).trim();
                    String className = String.valueOf(rowData.get("2")).trim();
                    String studentNum = String.valueOf(rowData.get("3")).trim();
                    String userPwd = String.valueOf(rowData.get("4")).trim();
                    User user = new User();
                    user.setUserPwd(userPwd);
                    user.setUserName(userName);
                    user.setAge(age);
                    user.setStudentNum(studentNum);
                    user.setClassName(className);
                    userList.add(user);
                }

                Map<String,Object> retMap = getRepeat(userList);
                if(retMap.get("retStr") != ""){
                    return ErrorResponse.errorResponse(FAIL_CODE, String.valueOf(FAIL_CODE), retMap.get("retStr").toString(),"");
                }
                List<User> insertList = (List<User>)retMap.get("insertList");
                userService.saveBatch(insertList);
            }
        } catch (Exception e) {
            return ErrorResponse.errorResponse(FAIL_CODE, String.valueOf(FAIL_CODE), SYSTEM_EXCEPTION_MESSAGE, e.toString());
        }
        Response response = new Response();
        OpenResponse res = new OpenResponse();
        res.setCode(SUCCESS_CODE);
        response.setResponse(res);
        response.setResponseCode(RESPONSE_SUCCESS_CODE);
        return response;
    }


    public Map<String,Object> getRepeat(List<User> userList){
        User temp;
        Map<String,Object> retMap = new HashMap<>();
        List<User> insertList = new ArrayList<>();
        String retStr = "";
        for (int i = 0; i <= userList.size() - 1; i++)
        {
            temp = userList.get(i);
            //验重，表格中的A不得有重复数据，否则弹窗提示“*******重复！”
            for (int j = i + 1; j < userList.size(); j++)
            {
                if (temp.getStudentNum().equals(userList.get(j).getStudentNum()))
                {
                    retStr = "第" + (i + 1) + "行跟第" + (j + 1) + "行学号重复";
                    break;
                }
            }
            //1）超标，每个字段都不得超过50字符，否则弹窗提示“XXXXX不得超过50字符！”
            //2）必填，所有项都必填，否则弹窗提示“XXXXX不得为空！”
            if(retStr.equals("")){
                //temp.setUserType(command.getUserType());
                insertList.add(temp);
            }else{
                break;
            }
        }
        retMap.put("retStr",retStr);
        retMap.put("insertList",insertList);
        return retMap;
    }


    /**
     * poi和ooxml的版本由3.13升级到3.15，否则报java.lang.NoSuchFieldError: RETURN_NULL_AND_BLANK错误
     * 返回流，页面直接下载
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "下载用户信息")
    @GetMapping("/exportUserExcel")
    public void exportUserExcel(HttpServletResponse response)throws IOException {
        List<User> list = userService.list(new QueryWrapper<User>().lambda().eq(User::getDelFlag,0).ge(User::getId,10));
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 保证下载到本地文件名不乱码
        String fileName = URLEncoder.encode("同学录", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcelFactory.write(response.getOutputStream(), User.class)
                .sheet("sheet")
                .doWrite(list);
    }


    @ApiOperation(value = "下载pdf")
    @GetMapping("/exportUserPdf")
    public void exportUserPdf(HttpServletResponse response)throws IOException {

    }


}

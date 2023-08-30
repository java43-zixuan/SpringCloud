package com.example.userservice1.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.common.exception.ServiceException;
import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
//import io.seata.spring.annotation.GlobalTransactional;
import feign.FeignException;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Service
@Slf4j
public class FeignService {

    @Autowired
    private UserService3Client userService3Client;

    @Autowired
    private IUserService userService;

    /**
     * 测试报错在被调用方
     * @param user
     */
    @GlobalTransactional(name = "seata-server", rollbackFor = ServiceException.class)
    public void addUser(User user) {
        try {
            userService.save(user);
            userService3Client.insertData();
        } catch (FeignException.FeignServerException e){
            log.info("FeignServerException捕获到异常");
            JSONObject jsonObject = fetchException(e.responseBody().get());
            throw new ServiceException(jsonObject.getString("message"));
        } catch (FeignException.FeignClientException e) {
            log.info("FeignClientException捕获到异常");
            JSONObject jsonObject = fetchException(e.responseBody().get());
            throw new ServiceException(jsonObject.getString("message"));
        } catch (FeignException e){
            log.info("FeignException捕获到异常");
            JSONObject jsonObject = fetchException(e.responseBody().get());
            throw new ServiceException(jsonObject.getString("message"));
        }
    }


    public JSONObject fetchException(ByteBuffer byteBuffer){
        Charset charset = Charset.forName("utf-8");
        //将数据流信息转成string
        String json = charset.decode(byteBuffer).toString();
        //将string转成json格式
        JSONObject resultData = JSONObject.parseObject(json);
        log.info("真实异常信息为===========>:{}", resultData);
        return resultData;
    }

    /**
     * 测试报错在调用方
     * @param user
     */
    @GlobalTransactional(name = "seata-server", rollbackFor = ServiceException.class)
    public void addUser2(User user) {
        userService.save(user);
        userService3Client.insertData2();
        if(true){
            throw new ServiceException("报错了！");
        }
    }
}

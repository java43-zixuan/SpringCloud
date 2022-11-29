package com.example.userservice1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constant.Constants;
import com.example.common.dto.LoginDto;
import com.example.common.dto.VerifyCodeDto;
import com.example.common.utils.DateUtil;
import com.example.common.utils.FileUtil;
import com.example.common.utils.StringUtil;
import com.example.common.vo.GetVerifyCodeDto;
import com.example.common.vo.LoginVo;
import com.example.common.vo.ResultVO;
import com.example.userservice1.entity.User;
import com.example.userservice1.service.IRedisService;
import com.example.userservice1.service.IUserService;
import com.example.userservice1.service.LoginService;
import com.example.userservice1.utils.MinioUtils;
import com.example.userservice1.utils.VerifyCodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private static final String VERIFYCODE_KEY = "login:verifyCode:verifyCode_";
    public static final String REDIS_LOCK_ACCOUT_KEY = "login:lock:lock_accout_";
    public static final String ERROR_LOGIN_KEY = "login:error:error_login_key_";

    // 密码错误n次校验验证码
    @Value("${need.check.verify.code.time:7}")
    private long needCheckVerifyCodeTime;
    // 验证码redis有效期
    @Value("${redis.verifyCode.liveTime:600000}")
    private long verifyCodeliveTime;
    // n分钟内连续输入错误m次密码锁定账号
    @Value("${login.error.liveTime:30}")
    private long loginErrorLiveTime;
    // 登陆错误n次后锁定账号
    @Value("${login.error.times:6}")
    private long errorTimes;
    // 登陆错误n次后锁定账号时间
    @Value("${login.error.lock.liveTime:600000}")
    private long errorLockTime;
    // 登陆错误信息redis有效时间
    @Value("${login.error.redis.liveTime:3600000}")
    private long errorRedisLiveTime;
    // token有效时间
    @Value("${redis.token.liveTime:3600000}")
    private long tokenLiveTime;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private MinioUtils minioUtils;


    @Override
    public ResultVO login(LoginDto dto)throws JsonProcessingException  {
        ResultVO result = new ResultVO();
        LoginVo loginVo = new LoginVo();
        loginVo.setUserName(dto.getUserName());

        // redis账号锁定key
        String errorLockAccountKey =REDIS_LOCK_ACCOUT_KEY + dto.getUserName();
        String errorLockAccount= redisService.getKey(errorLockAccountKey);
        if (StringUtil.isNotEmpty(errorLockAccount)) {
            result.setMessage("该账号已被锁定,请等待10分钟后重试");
            result.setStatus(610114);
            return result;
        }
        // redis验证码key:VERIFYCODE_+VerifyCodeId+"_"+"userName"
        String errorKey =ERROR_LOGIN_KEY + dto.getVerifyCodeId() + "_" + dto.getUserName();
        // 登陆错误信息
        Map<String, String> errorMap = getErrorMap(errorKey);
        // 校验验证码
        if (!checkVerifyCode(dto, result, errorMap)) {
            return result;
        }
        //根据用户名查询用户信息
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserName,dto.getUserName()).eq(User::getUserLock,0).eq(User::getDelFlag,0));
        // 校验失败返回错误信息
        if (!checkUser(loginVo,user, dto, result, errorKey, errorMap,errorLockAccountKey)) {
            return result;
        }
        // 判断是否有其他用户登陆，并且确认是否踢掉其他用户,如果返回false返回前端确认登陆提示,如果true清掉redis中之前用户登陆的信息并保存当前也用户登陆的信息
        if (!isSaveTokenToredis(loginVo, dto.getConfirmLoginFlg(), result)) {
            return result;
        }
        // 封装返回结果
        Map map = new HashMap();
        map.put("token", loginVo.getToken());
        result.setData(map);
        return result;
    }

    @Override
    public ResultVO logout(String token) {
        return null;
    }

    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    @Override
    public ResultVO getVerifyCode(VerifyCodeDto dto)throws Exception{
        ResultVO result = new ResultVO();
        // redis 验证码key:VERIFYCODE_+VerifyCodeId
        String key = VERIFYCODE_KEY + dto.getVerifyCodeId();
        // 同一session存储的验证码
        Map<String, String> oldMap = redisService.getJson(key, LinkedHashMap.class);
        // 如果存储过验证码文件则删除文件服务器上传的验证码文件,清空redis
        if (!StringUtil.isEmpty(oldMap)) {
            try {
                this.delMinioVerifyCodeFile(oldMap, key,"test");
            } catch (Exception e) {
                log.error("删除临时验证码文件失败:" + e.getMessage(), e);
            }
        }
        //换用minio 上传
        //List<String> minioUpload = minioUtils.upload(new MultipartFile[]{FileUtil.getMultipartFile(VerifyCodeUtils.createVerifyCodeFile())},"test");
        Map<String, String> map = verifyCodeUtils.uploadVerifyCodeFlieByMinio("test");
        // 上传失败或者验证码为空返回错误
        if (StringUtil.isEmpty(map) || StringUtil.isEmpty(map.get("verifyCode"))
                || StringUtil.isEmpty(map.get("fileName"))) {
            result.setMessage("上传验证码失败");
            result.setStatus(610111);
            return result;
        }
        // 存储验证码
        redisService.saveObjectToJson(key, map, verifyCodeliveTime);
        // 结果集赋值
        GetVerifyCodeDto getVerifyCodeDto = new GetVerifyCodeDto();
        getVerifyCodeDto.setFullPath(map.get("fileName"));
        result.setData(getVerifyCodeDto);
        return result;
    }

    /**
     * 删除文件服务器上传的验证码文件,清空redis
     *
     * @param oldMap
     * @param key
     * @return
     */
    private void delGetVerifyCodeFile(Map<String, String> oldMap, String key) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        String group = oldMap.get("group_name");
        String path = oldMap.get("path");
        String tempFilePath = oldMap.get("tempFilePath");

        // 删除之前的临时文件
        FileUtil.deleteJpg(tempFilePath);

        // 如果有上传过的文件
        if (StringUtil.isNotEmpty(group) && StringUtil.isNotEmpty(path)) {
            param.add("group", group);
            param.add("path", path);
            RestTemplate restTemplate = new RestTemplate();
            // 物理删除文件
            //restTemplate.postForObject(delFileUrl, param, String.class);
        }
        // 清空redis
        redisService.del(key);
    }


    /**
     * 删除minio文件服务器上传的验证码文件,清空redis
     * @param oldMap
     * @param key
     * @return
     */
    private void delMinioVerifyCodeFile(Map<String, String> oldMap, String key,String bucket) {
        // 删除文件服务器文件
        minioUtils.removeObject(bucket, MapUtils.getString(oldMap,"fileName"));
        // 清空redis
        redisService.del(key);
    }




    /**
     * 获取登陆错误信息
     * @param errorKey
     * @return
     */
    private Map getErrorMap(String errorKey) {
        // 获取登陆的错误信息和错误次数
        Map<String, String> errorMap = redisService.getJson(errorKey, LinkedHashMap.class);
        // 如果为空初始化map
        if (StringUtil.isEmpty(errorMap)) {
            errorMap = this.initErrorMap();
        }
        return errorMap;
    }

    /**
     * 初始化错误信息
     * @return
     */
    private Map initErrorMap() {
        Map<String, String> errorMap = new LinkedHashMap<String, String>();
        errorMap.put("errorCount", "0");
        errorMap.put("firstErrorTime", System.currentTimeMillis() + "");// 首次错误时间
        return errorMap;
    }

    /**
     * 获取错误次数
     * @param errorMap
     * @return
     */
    private Long getErrorConunt(Map<String, String> errorMap) {
        return StringUtil.toLong(errorMap.get("errorCount"));
    }

    /**
     * 错误数加1
     * @param errorMap
     * @return
     */
    private void addErrorConunt(Map<String, String> errorMap) {
        Long errorCount = StringUtil.toLong(errorMap.get("errorCount"));
        errorMap.put("errorCount", (errorCount + 1) + "");
    }


    /**
     * 校验验证码合法性
     * @param loginDto
     * @param result
     * @param errorMap
     * @return
     */
    private boolean checkVerifyCode(LoginDto loginDto, ResultVO result, Map<String, String> errorMap) {
        // 错误数
        Long errorConunt = this.getErrorConunt(errorMap);
        // 验证码唯一标识
        String verifyCodeId = loginDto.getVerifyCodeId();
        // 传入的验证码
        String verifyCode = loginDto.getVerifyCode();
        // redis验证码key:VERIFYCODE_+VerifyCodeId
        String key = VERIFYCODE_KEY + verifyCodeId;
        // redis中验证码
        Map<String, String> map = redisService.getJson(key, LinkedHashMap.class);
        // 有验证码就校验
        if (StringUtil.isNotEmpty(verifyCode)) {
            return checkVerifyCode(map, result, verifyCode);
        }
        // 没有验证码时如果不是第一次密码输入错误，进行校验
        else {
            // 如果密码已经错误一次校验验证码
            if (errorConunt >= needCheckVerifyCodeTime) {
                return checkVerifyCode(map, result, verifyCode);
            }
        }
        return true;
    }


    /**
     * @param map
     * @param result
     * @param verifyCode
     * @return
     */
    private boolean checkVerifyCode(Map<String, String> map, ResultVO result, String verifyCode) {
        // 验证码不能为空
        if (StringUtil.isEmpty(verifyCode)) {
            result.setStatus(710115);
            result.setMessage("验证码不能为空");
            return false;
        }
        // redis中没有验证码或者验证码为空或者输入的验证码和生成的验证码不一致返回错误信息
        if (map == null || StringUtil.isEmpty(map.get("verifyCode")) || StringUtil.isNotEq(map.get("verifyCode"), verifyCode.toUpperCase().trim())) {
            result.setStatus(710112);
            result.setMessage("验证码错误");
            return false;
        }
        return true;
    }


    /**
     * 检查用户
     *
     **/

    public Boolean checkUser(LoginVo loginVo,User user, LoginDto loginDto, ResultVO result, String errorKey, Map<String, String> errorMap,String errorLockAccountKey) throws JsonProcessingException {
        Boolean flag= false;
        if(user == null){
            result.setMessage("用户名或者密码错误");
            result.setStatus(710101);
            return false;
        }
        loginVo.setUserId(user.getId());
        // 如果密码不正确返回:密码错误+错误次数
        if (StringUtil.isNotEq(user.getUserPwd(), loginDto.getUserPwd()) || flag) {
            setErrorTimes(result,Long.valueOf(user.getId()), errorKey, errorMap,errorLockAccountKey);
            // 给前端返回是否显示验证码
            setIsShowVerifyCode(result, errorMap);
            return false;
        }
        // 用户名密码正确清空登陆错误信息
        else {
            redisService.del(errorKey);
        }
        return true;
    }

    /**
     * 校验密码错误次数
     *
     * @param result
     * @param errorKey
     * @throws JsonProcessingException
     */
    private void setErrorTimes(ResultVO result, Long userId, String errorKey,
                               Map<String, String> errorMap,String errorLockAccountKey)
            throws JsonProcessingException {
        // 30分钟内错误次数
        Long errorCount = this.getErrorConunt(errorMap);
        // 首次错误时间
        Long firstErrorTime = StringUtil.toLong(errorMap.get("firstErrorTime"));
        // 如果超过30分钟清空错误信息
        if (DateUtil.dateDiff(firstErrorTime) >= loginErrorLiveTime) {
            // 初始化错误信息
            errorMap = this.initErrorMap();
            addErrorConunt(errorMap);
            // 存入reids
            redisService.saveObjectToJson(errorKey, errorMap, errorRedisLiveTime);
            this.setErrorLoginResult(result, errorMap);
            return;
        }
        // 错误小于5次 错误次数+1(errorCount从1开始计数，所以+1 )
        if (errorCount + 1 < errorTimes) {
            // 错误次数+1
            addErrorConunt(errorMap);
            this.setErrorLoginResult(result, errorMap);
            // 存入reids
            redisService.saveObjectToJson(errorKey, errorMap, errorRedisLiveTime);
        }
        // 错误大于等于5次 锁定用户，清空redis
        else {
            // 锁定用户
            redisService.saveObjectToJson(errorLockAccountKey,"lock",errorLockTime);
            //可以使用userId对数据库数据进行锁定

            // 清空redis
            redisService.del(errorKey);
            result.setMessage("该账号已被锁定,请等待10分钟后重试");
            result.setStatus(610114);
        }
    }



    /**
     * @param result
     * @param errorMap
     */
    private void setErrorLoginResult(ResultVO result, Map<String, String> errorMap) {
        long errorCount = this.getErrorConunt(errorMap);
        result.setMessage("用户名或密码错误,请重试,剩余 " + (errorTimes - errorCount)  + " 次");
        result.setStatus(710191);
    }
    /**
     * 给前端返回是否显示验证码
     * @param result
     * @param errorMap
     */
    private void setIsShowVerifyCode(ResultVO result, Map<String, String> errorMap) {
        // 30分钟内错误次数
        Long errorCount = this.getErrorConunt(errorMap);
        if (errorCount >= needCheckVerifyCodeTime) {
            // 返回前端是否显示验证码
            Map map = new LinkedHashMap();
            map.put("isShowVerifyCode", true);
            result.setData(map);
        }
    }

    /**
     * 判断是否有其他用户登陆，并且是否确认踢掉其他用户
     *
     * @param dto
     * @param confirmLoginFlg
     * @param result
     * @throws JsonProcessingException
     */
    private boolean isSaveTokenToredis(LoginVo dto, String confirmLoginFlg, ResultVO result) throws JsonProcessingException{
        // 生成token
        String token = StringUtil.getUUID();
        dto.setToken(token);
        // redis token key:STOKENS_+UserId+"_"+token
        String keys = Constants.TOKEN_KEY + dto.getUserId() + Constants.SYMBO_UNDERLINE;
        String sessionKeys = Constants.SESSION_KEY + dto.getUserId() + Constants.SYMBO_UNDERLINE;
        // 校验是否确认登陆，并踢掉其他用户,如果返回false返回前端确认登陆提示，否则踢掉之前用户并登陆,并清掉redis
        if (!checkConfirmLogin(keys + Constants.SYMBO_NUMBERSIGN,
                sessionKeys + Constants.SYMBO_NUMBERSIGN, confirmLoginFlg, result)) {
            return false;
        }
        // 存入redis
        redisService.saveObjectToJson(keys + token, dto, tokenLiveTime);
        return true;
    }

    /**
     * 如果有其他用户登陆时校验是否确认登陆
     * @param keys
     * @param confirmLoginFlg
     * @param result
     * @return
     */
    private boolean checkConfirmLogin(String keys, String sessionKeys, String confirmLoginFlg, ResultVO result) {
        boolean isConfirmLogin = true;

        // 遍历redis记录
        Set<String> tb_set = redisService.getKeys(keys);
        Set<String> session_set = redisService.getKeys(sessionKeys);

        // 判空
        if (!StringUtil.isEmpty(session_set)) {
            if (!StringUtil.isEmpty(tb_set)) {
                for (String key : tb_set) {
                    // 已经登陆的用户信息
                    LoginVo dto = redisService.getJson(key, LoginVo.class);
//                    LogOutCommand command = new LogOutCommand();

                    // 如果已经有用户
                    if (dto != null) {
                        // 其他用户token
                        //String token = dto.getToken();
                        // 设置登出参数
                        //command.setToken(token);
                        // 如果确认登陆踢掉之前的用户，并发送消息推送并登陆
                        if (StringUtil.isEq(Constants.ConfirmLoginFlgEnum.CONFIRM_LOGIN.getCode(), confirmLoginFlg)) {
                            // 登出其他用户
                            //this.logout(command);

                            // 消息推送给其他登陆的用户，踢掉其他登陆的用户
                            //this.pushMessage(token);
                            isConfirmLogin = true;
                            continue;
                        }
                        // 否则返回失败并设置提示用户已在其他地方登陆的提示信息，提示用户是否确认登陆
                        else {
                            // 返回前端是否确认登陆提示
                            //result.fillResult(ReturnCode.CONFIRM_LOGIN);
                            isConfirmLogin = false;
                            break;
                        }
                    } else {
                        //this.logout(command);
                        isConfirmLogin = true;
                    }
                }
            }
        }
        return isConfirmLogin;
    }



}

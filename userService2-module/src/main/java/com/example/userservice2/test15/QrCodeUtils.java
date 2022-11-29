package com.example.userservice2.test15;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.example.common.dto.UserDto;
import com.example.common.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.poi.ss.util.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成二维码、验证码工具  https://blog.csdn.net/weixin_46713508/article/details/126395679
 */
public class QrCodeUtils {

    /**
     * 生成自定义图片的二维码
     */
    public static void generateGeneralQrCodeWithBgImg() {
        QrConfig config = new QrConfig(500, 500);
        config.setImg("C:\\Users\\Administrator\\Desktop\\QrCode\\12.jpg");
        config.setErrorCorrection(ErrorCorrectionLevel.Q);
        QrCodeUtil.generate("https://www.baidu.com/", config, FileUtil.file("C:\\Users\\Administrator\\Desktop\\QrCode\\QRCODE2.jpg"));

    }

    /**
     * 生成普通类型的二维码
     */
    public static void generateGeneralQrCode() {
        // 三个参数分别是 扫码内容 宽高
        QrCodeUtil.generate("http://www.baidu.com/",
                500, 500,
                // 生成路径 切记换成自己的
                FileUtil.file("C:\\Users\\Administrator\\Desktop\\QrCode\\QRCODE1.jpg"));

    }


    /**
     * 识别图片二维码里面的内容
     */
    public static void decodeQrCode() {
        String decode = QrCodeUtil.decode(FileUtil.file("C:\\Users\\Administrator\\Desktop\\QrCode\\QRCODE1.jpg"));
        System.out.println("二维码解析的内容=======》"+decode);
    }


    /**
     * 生成自定义图片的二维码
     * @param imgPath
     * @param content
     * @param filePath
     */
    public static void generateGeneralQrCodeWithBgImg(String imgPath,String content,String filePath) {
        QrConfig config = new QrConfig(500, 500);
        config.setImg(imgPath);
        config.setErrorCorrection(ErrorCorrectionLevel.Q);
        QrCodeUtil.generate(content, config, FileUtil.file(filePath));
    }

    /**
     * 生成普通类型的二维码
     * @param content
     * @param filePath
     */
    public static void generateGeneralQrCode(String content,String filePath) {
        QrCodeUtil.generate(content, 500, 500, FileUtil.file(filePath));
    }

    /**
     * 生成普通类型的二维码(自定义宽高跟颜色)
     * @param config
     * @param content
     * @param filePath
     */
    public static void generateGeneralQrCode(QrConfig config, String content,String filePath) {
        QrCodeUtil.generate(content, config, FileUtil.file(filePath));
    }


    /**
     * 识别图片二维码里面的内容
     * @param qrCodePath
     */
    public static Object decodeQrCode(String qrCodePath ) {
        String decode = QrCodeUtil.decode(FileUtil.file(qrCodePath));
        System.out.println("二维码解析的内容=======》"+decode);
        return JSON.parse(decode);
    }

    public static String generateAsBase64(String content, QrConfig qrConfig, String imageType) {
        String str = QrCodeUtil.generateAsBase64(content,qrConfig,imageType);
        System.out.println("使用base64字符串创建二维码=======》"+str);
        return str;
    }



    public static void main(String[] args) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        map.put("userName","lisi");
        map.put("age","25");
        map.put("studentNum","02");

        String content = JsonUtil.toJson(map);
        String filePath = "C:\\Users\\Administrator\\Desktop\\QrCode\\QRCODE11.jpg";
        String logo = "C:\\Users\\Administrator\\Desktop\\QrCode\\12.jpg";

        QrConfig config = new QrConfig();
        // 设置高度
        config.setHeight(250);
        // 设置宽度
        config.setWidth(250);
        // 设置背景色
        config.setBackColor(Color.white);
        // 设置前景色，既二维码颜色
        config.setForeColor(Color.GREEN);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(1);
        // 二维码附带logo
        BufferedImage image = ImgUtil.read("C:\\Users\\Administrator\\Desktop\\QrCode\\12.jpg");//获取图片
        config.setImg(image.getScaledInstance(100, 100, 1));//把压缩后的图片放进去
        //也可以直接放图片地址
        //config.setImg("C:\\Users\\Administrator\\Desktop\\QrCode\\12.jpg")
        //调整纠错级别
        //很多时候，二维码无法识别，这时就要调整纠错级别。纠错级别使用zxing的ErrorCorrectionLevel枚举封装，包括：L、M、Q、H几个参数，由低到高。低级别的像素块更大，可以远距离识别，但是遮挡就会造成无法识别。高级别则相反，像素块小，允许遮挡一定范围，但是像素块更密集。
        config.setErrorCorrection(ErrorCorrectionLevel.H);



        generateGeneralQrCode(content,filePath);
        generateGeneralQrCodeWithBgImg(logo,content,filePath);
        generateGeneralQrCode(config,content,filePath);
        generateAsBase64(content,config, ImgUtil.IMAGE_TYPE_JPG);

        //解析二维码(二维码添加了特殊颜色容易导致解析失败(比如黄色、橘色))
        Object obj = decodeQrCode(filePath);
        System.out.println(JSON.toJSONString(obj));

    }


}

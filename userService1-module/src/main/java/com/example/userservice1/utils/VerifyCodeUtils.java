package com.example.userservice1.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.common.constant.VerifyCodeConstants;
import com.example.common.utils.FileUtil;
import com.example.common.utils.IoUtils;
import com.example.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

/**
 * Created by zhangy on 2017/05/09.
 */
@Slf4j
@Component
public class VerifyCodeUtils {

    @Autowired
    private MinioUtils minioUtils;

    // 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    private static Random random = RandomUtil.getRandom();

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString().toUpperCase().trim();
    }

    /**
     * 生成随机验证码文件,并返回验证码值
     *
     * @param w
     * @param h
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, File outputFile, int verifySize)
            throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        // outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     *
     * @param w
     * @param h
     * @param os
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize)
            throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件并上传文件服务器
     *
     * @param verifyCodeId
     * @param uploadFileUrl
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @return
     * @throws IOException
     */
    public static Map uploadVerifyCodeFlie(String verifyCodeId, String uploadFileUrl, int w, int h, File outputFile,
                                           String code)
            throws IOException {

        // 封装返回结果
        Map<String, String> map = new LinkedHashMap<>();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", verifyCodeId);
        param.add("file", new FileSystemResource(outputFile));
        RestTemplate restTemplate = new RestTemplate();
        JSONObject httpResult = JSONUtil.parseObj(restTemplate.postForObject(uploadFileUrl, param, String.class));

        // 判断是否上传成功
        if (null != httpResult && !StringUtil.isEmpty(httpResult.getStr("data"))) {
            JSONObject uploadJson = JSONUtil.parseObj(httpResult.getStr("data"));

            // 如果上传成功赋值文件地址
            if (!StringUtil.isEmpty(uploadJson.getStr("fullPath"))) {
                map.put("fullPath", uploadJson.getStr("fullPath"));
            }
            // 如果上传成功赋值分组名称
            if (!StringUtil.isEmpty(uploadJson.getStr("group_name"))) {
                map.put("group_name", uploadJson.getStr("group_name"));
            }

            // 如果上传成功赋值文件相对路径
            if (!StringUtil.isEmpty(uploadJson.getStr("path"))) {
                map.put("path", uploadJson.getStr("path"));
            }
        }

        // 保存临时文件的绝对路径
        map.put("tempFilePath", outputFile.getAbsolutePath().replace(outputFile.getName(), ""));

        // 赋值验证码
        map.put("verifyCode", code);
        return map;
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code)
            throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = RandomUtil.getRandom();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces =
                new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                        Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        Random random = RandomUtil.getRandom();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w * 1.0 / verifySize) * i
                    + fontSize / 2.0, h / 2.0);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }

        g2.dispose();
        ImageIO.write(image, VerifyCodeConstants.VERIFYCODE_EXTENSION_WITHOUT_DOT, os);
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d =
                    (double) (period >> 1)
                            * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d =
                    (double) (period >> 1)
                            * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }

    /**
     * @param verifyCodeId
     * @param uploadFileUrl
     * @return
     * @throws IOException
     */
    public Map uploadVerifyCodeFlie(String verifyCodeId, String uploadFileUrl)
            throws IOException {
        // 生成随机验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(VerifyCodeConstants.VERIFYCODE_SIZE);
        FileOutputStream fos = null;
        Map map = new HashMap();
        // 生成临时文件
        try {
            File file = new File(verifyCode + VerifyCodeConstants.VERIFYCODE_EXTENSION_WITH_DOT);
            if(file.createNewFile()){
                fos = new FileOutputStream(file);
                outputImage(VerifyCodeConstants.VERIFYCODE_WEIGHT, VerifyCodeConstants.VERIFYCODE_HEIGHT, fos, verifyCode);
                map =
                        uploadVerifyCodeFlie(verifyCodeId,
                                uploadFileUrl,
                                VerifyCodeConstants.VERIFYCODE_WEIGHT,
                                VerifyCodeConstants.VERIFYCODE_HEIGHT,
                                file,
                                verifyCode);

            };
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            IoUtils.close(fos);
        }
        return map;
    }


    /**
     * 使用minio上传图片到文件服务器
     * @param bucket
     * @return
     * @throws IOException
     */
    public Map uploadVerifyCodeFlieByMinio(String bucket){
        String verifyCode = null;
        FileOutputStream fos = null;
        File file = null;
        Map map = new HashMap();
        try {
            // 生成随机验证码
            verifyCode = VerifyCodeUtils.generateVerifyCode(VerifyCodeConstants.VERIFYCODE_SIZE);
            // 生成临时文件
            file = new File(verifyCode + VerifyCodeConstants.VERIFYCODE_EXTENSION_WITH_DOT);
            if(file.createNewFile()){
                fos = new FileOutputStream(file);
                outputImage(VerifyCodeConstants.VERIFYCODE_WEIGHT, VerifyCodeConstants.VERIFYCODE_HEIGHT, fos, verifyCode);
                //使用minio上传
                List<String> minioUpload = minioUtils.upload(new MultipartFile[]{FileUtil.getMultipartFile(file)},bucket);
                map.put("fileName", minioUpload.get(0));
                map.put("verifyCode", verifyCode);
            };
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            IoUtils.close(fos);
            //删除临时文件
            file.delete();
        }
        return map;
    }



}

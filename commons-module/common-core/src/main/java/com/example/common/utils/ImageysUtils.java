package com.example.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Author zx
 * @Date 2022/11/30 15:03
 * @Description: 图片压缩处理方法类
 * @Version 1.0
 */
@Slf4j
public class ImageysUtils {
    /**
     * 压缩base64编码至70KB以内
     * @param base64Img
     * @return
     */
    public static String resizeImageTo70K(String base64Img) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64Img);
            InputStream stream = new ByteArrayInputStream(bytes1);
            BufferedImage src = ImageIO.read(stream);
            BufferedImage output = Thumbnails.of(src).size(1000, 600).asBufferedImage();//
            String base64 = imageToBase64(output);
            if (base64.length() - base64.length() / 8 * 2 > 70000) {
                System.out.println("继续进行压缩");
                output = Thumbnails.of(output).scale(1 / (base64.length() / 70000)).asBufferedImage();
                base64 = imageToBase64(output);
            }
            return base64;
        } catch (Exception e) {
            return base64Img;
        }
    }

    /**
     * 指定图片压缩大小
     * @param imageBytes
     * @param desFileSize
     * @param imageId
     * @return
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize, String imageId) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            log.debug("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",imageId, srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }
    /**
     * 自动调节精度(经验数值)
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }




    /**
     * BufferedImage转换成base64，在这里需要设置图片格式,因为我需要jpg格式就设置了jpg
     */
    public static String imageToBase64(BufferedImage bufferedImage) {
        Base64 encoder = new Base64();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
        }
        return new String(encoder.encode((baos.toByteArray())));
    }



    /**
     * File转Base64字符串
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            Base64 util = new Base64();
            base64 = util.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * Base64字符串转File
     * @param base64
     * @return
     */
    public static File base64ToFile(String base64) {
        if(base64==null||"".equals(base64)) {
            return null;
        }
        Base64 util = new Base64();
        byte[] buff=util.decode(base64);
        File file=null;
        FileOutputStream fout=null;
        try {
            file = File.createTempFile("tmp", null);
            fout=new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }




    /**
     * base64转图片
     *
     * @param base64str base64码
     * @param savePath  图片路径
     * @param fileName  图片名称
     * @return boolean 判断是否成功下载到本地
     */
    private static boolean downloadImg(String base64str, String savePath, String fileName) throws IOException {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = decoder.decodeBuffer(base64str);
        for (int i = 0; i < b.length; ++i) {
            //调整异常数据
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        // 判断路径是否不存在，不存在就创建文件夹
        File fileDir = new File(savePath);

        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        // 生成一个空文件，自定义图片的名字
        File file = new File(savePath + "\\" + fileName + ".jpg");

        if (!file.exists()) {
            file.createNewFile();
        }
        //生成jpg图片
        OutputStream out = new FileOutputStream(file.getPath());
        out.write(b);
        out.flush();
        out.close();
        return true;
    }


    public static void main(String[] args)throws Exception {
        File picture = new File("D:\\图3.jpg");
        if (!picture.exists()) {
            System.out.println("文件不存在");
            return;
        }
        //查看当前图片大小
        Float size = Float.parseFloat(String.format("%.1f",picture.length() / 1024.0));
        System.out.println("【"+picture.getName()+"】压缩前图片大小:"+picture.length()+"字节；" + size + "KB");
//        //图片转baseStr
//        String baseStr =  fileToBase64(picture);
//        //对图片进行压缩
//        String baseResult = resizeImageTo70K(baseStr);

        byte[] bytes = FileUtils.readFileToByteArray(picture);
        byte[] afterBytes = compressPicForScale(bytes,90,picture.getName());
        Base64 util = new Base64();
        String baseResult = util.encodeToString(afterBytes);
        try{
            //将图片下载到本地
            String savePath = "D:\\";
            downloadImg(baseResult,savePath,"tu3");
        }catch (Exception e){
            e.printStackTrace();
        }
        //转换压缩后的图片文件
        File file2 = base64ToFile(baseResult);
        //查看压缩后图片大小
        Float size1 = Float.parseFloat(String.format("%.1f",file2.length() / 1024.0));
        System.out.println("【"+picture.getName()+"】压缩后图片大小:"+file2.length()+"字节；" + size1 + "KB");
    }



}
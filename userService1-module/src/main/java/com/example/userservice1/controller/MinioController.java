package com.example.userservice1.controller;

import com.example.userservice1.utils.MinioUtils;
import io.minio.Result;
import io.minio.messages.DeleteError;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "minio测试模块")
@RestController
@Slf4j
public class MinioController {

    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传
     * @param file
     * @return
     */
    @PostMapping("/minioUpload")
    public Object upload(MultipartFile file) {
        List<String> upload = minioUtils.upload(new MultipartFile[]{file},null);
        return upload.get(0);
    }

    /**
     * 下载
     * @param fileNme
     * @return
     */
    @GetMapping("/minioDownload")
    public ResponseEntity minioDownload(@RequestParam("fileName")String fileNme) {
        ResponseEntity entity = minioUtils.download(fileNme);
        return entity;
    }

    /**
     * 单个文件删除
     * @param bucketName
     * @param fileNme
     */
    @GetMapping("/minioDelete")
    public void minioDelete(@RequestParam("bucketName")String bucketName,@RequestParam("fileName")String fileNme) {
        minioUtils.removeObject(bucketName,fileNme);
    }

    /**
     * 批量文件删除
     * @param bucketName
     * @param fileNme
     */
    @GetMapping("/minioDeletes")
    public void minioDeletes(@RequestParam("bucketName")String bucketName,@RequestParam("fileName")String fileNme) {
        List<String> files = new ArrayList<>();
        //files.add(fileNme);
        files.add("T9LV_1666881104104.jpg");
        files.add("FEKR_1666880791997.jpg");
        files.add("NH3M_1666928886464.jpg");
        files.add("HVX6_1666930531877.jpg");
        files.add("L5VY_1666929053648.jpg");
        files.add("SW7J_1666880344387.jpg");
        try{
            minioUtils.removeObjects(bucketName,files);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

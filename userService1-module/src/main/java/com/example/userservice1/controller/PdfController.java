package com.example.userservice1.controller;

import com.example.userservice1.entity.AdmissionCard;
import com.example.userservice1.service.PdfCustomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@RestController
@Api(value = "PDF相关操作接口", tags = "PDF相关操作接口")
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfCustomService pdfCustomService;

    @ApiOperation(value = "导出PDF")
    @PostMapping("/admissioncard")
    public void generatorAdmissionCard(@RequestBody AdmissionCard admissionCard, HttpServletResponse response){
        try {
            pdfCustomService.generatorAdmissionCard(admissionCard, response);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package com.example.userservice1.controller;

import com.example.userservice1.entity.Goods;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板变更得需要重新打包，否则不生效。会报一些莫名错误
 *
 * 模板设置
 * 序号的表达式要拆成三个域，如下图，要把这三部分分别设置成域；
 * 设置完成的结果参考上面表格中的序号表达式，表达式中"item?index+1"是因为序号是从0开始的，所以要加1；
 * @before-row[#list goods as item]    需要使用“”包裹起来而不是使用${}  goods 对应context中put的内容
 * item?index
 * @after-row[/#list]
 *
 *
 */
@Api(tags = "word导出模块")
@Slf4j
@RestController
@RequestMapping("/word")
public class WordController {

    @GetMapping("/generateWord")
    public void generateWord(HttpServletResponse response) throws IOException, XDocReportException {
        try{
              //生产环境获取文件方式
//            //获取模板配置文件路径
//            String uploadPath = "template/".concat("EquipmentFile.docx");
//            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(uploadPath);
//            File file = InputStreamToFile(is);
//            String templateFilePath = file.getAbsolutePath();
//            log.info("获取设备信息模板配置路径文件路径"+templateFilePath);

            //获取resources目录下文件的方式：https://www.jb51.net/article/243228.htm
            //本地获取Word模板，模板存放路径在项目的resources目录下(最好英文明明，否则可能导致读取不到文件)
            InputStream ins = this.getClass().getResourceAsStream("/wordTemplate.docx");
            //注册xdocreport实例并加载FreeMarker模板引擎
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,TemplateEngineKind.Freemarker);
            //创建xdocreport上下文对象
            IContext context = report.createContext();

            //创建要替换的文本变量
            context.put("city", "北京市");
            context.put("startDate", "2020-09-17");
            context.put("endDate", "2020-10-16");
            context.put("totCnt", 3638763);
            context.put("totAmt", "6521");

            List<Goods> list = new ArrayList<Goods>();
            Goods goods1 = new Goods();
            goods1.setNum(1);
            goods1.setType("臭美毁肤");
            goods1.setSv(675512);
            goods1.setSa("589");
            Goods goods2 = new Goods();
            goods2.setNum(2);
            goods2.setType("女装");
            goods2.setSv(602145);
            goods2.setSa("651");
            Goods goods3 = new Goods();
            goods3.setNum(3);
            goods3.setType("手机");
            goods3.setSv(587737);
            goods3.setSa("866");
            Goods goods4 = new Goods();
            goods4.setNum(4);
            goods4.setType("家具家装");
            goods4.setSv(551193);
            goods4.setSa("783");
            Goods goods5 = new Goods();
            goods5.setNum(5);
            goods5.setType("食物饮品");
            goods5.setSv(528604);
            goods5.setSa("405");
            list.add(goods1);
            list.add(goods2);
            list.add(goods3);
            list.add(goods4);
            list.add(goods5);
            context.put("goods", list);

            //导出表格，需要设置模板中数据的集合类型
            //创建字段元数据
            FieldsMetadata fm = report.createFieldsMetadata();
            //Word模板中的表格数据对应的集合类型
            fm.load("goods", Goods.class, true);

            //输出到本地目录
            FileOutputStream out = new FileOutputStream(new File("D://商品销售报表.docx"));
            report.process(context, out);

            //浏览器端下载
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "商品销售报表.docx";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            report.process(context, response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    /**
     * 将文件流写入文件中
     * @param is
     */
    private File InputStreamToFile(InputStream is) throws Exception{
        File file = File.createTempFile("EquipmentFile", ".docx");
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        while ((is.read(b)) != -1) {
            fos.write(b);// 写入数据
        }
        is.close();
        fos.close();// 保存数据
        return file;
    }
}

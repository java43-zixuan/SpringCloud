package com.example.userservice1.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author ZH
 */
@Component
@Slf4j
public class XxlJobTest {


    /**
     * @Description 测试
     * @return void
     **/
    @XxlJob("XxlJobTestHandler")
    public void realTimeDataPreparationHandler() throws Exception {
        XxlJobHelper.log("[测试] start ......");
        XxlJobHelper.log("[测试] end ......");
    }


}

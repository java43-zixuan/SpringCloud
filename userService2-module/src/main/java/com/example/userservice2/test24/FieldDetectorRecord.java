package com.example.userservice2.test24;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * attendance_record
 * </p>
 *
 * @author Li Yunzhi
 * @since 2023-01-12
 */
@Data
public class FieldDetectorRecord implements  Serializable {


    /**
     * id
     */
    @Id
    private String id;

    /**
     * 设备编码
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 摄像机ip
     */
    @Column(name = "nvr_ip")
    private String nvrIp;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


    /**
     * 告警类型
     */
    @Column(name = "alarm_type")
    private String alarmType;

    /**
     * 小图路径
     */
    @Column(name = "small_image_url")
    private String smallImageUrl;

    /**
     * 大图路径
     */
    @Column(name = "big_image_url")
    private String bigImageUrl;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

}

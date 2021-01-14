package com.sly.conver.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 中药模板
 */
@Data
public class TemplateCm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**(value = "中药用药模板id*/
    private Integer tcId;

    /**(value = "模板名称*/
    private String tcName;

    /**(value = "医生id*/
    private Integer doctorId;

    /**(value = "类别(1.个人2.公共)*/
    private Integer tcType;

    /**(value = "个人模板类别Id*/
    private Integer personalTypeId;

    /**(value = "状态(1.正常2.删除)*/
    private Integer tcStatus;

    /**(value = "频次*/
    private String tcFrequency;

    /**(value = "用法*/
    private String tcUsage;

    /**(value = "时长*/
    private Integer tcDays;

    /**(value = "总数量*/
    private BigDecimal tcNumber;

    /**(value = "诊所ID*/
    private BigDecimal clinicId;

    /**(value = "每日制药*/
    private BigDecimal tcSingleNumber;

    /**(value = "类型：1.饮片2.颗粒*/
    private Integer cmType;

    /**(value = "是否有效(1-有效,2-无效)*/
    private Integer valid;
}

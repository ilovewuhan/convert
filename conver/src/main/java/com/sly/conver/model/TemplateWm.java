package com.sly.conver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 西药模板
 */
@Data
public class TemplateWm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**(value = "西药用药模板id*/
    private Integer twId;

    /**(value = "模板名称*/
    private String twName;

    /**(value = "医生id*/
    private Integer doctorId;

    /**(value = "类别(1.个人2.公共)*/
    private Integer twType;

    /**(value = "个人模板类别Id*/
    private Integer personalTypeId;

    /**(value = "状态(1.正常2.删除)*/
    private Integer twStatus;

    /**(value = "诊所id*/
    private Integer clinicId;

    /**(value = "模板类型(1.西药2.中成药)*/
    private Integer modalType;

    /** 是否有效(1-有效,2-无效)*/
    private Integer valid;
}

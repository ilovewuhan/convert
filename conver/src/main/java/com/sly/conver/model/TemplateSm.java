package com.sly.conver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 贴敷模板
 */
@Data
public class TemplateSm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**(value = "用药模板id")*/
    private Integer tsId;

    /**(value = "诊所id")*/
    private Integer clinicId;

    /**(value = "医生id")*/
    private Integer doctorId;

    /**(value = "状态(1.正常2.删除)")*/
    private Integer tsStatus;

    /**(value = "类别(1.个人2.公共)")*/
    private Integer tsType;

    /**(value = "个人模板类别Id")*/
    private Integer personalTypeId;

    /**(value = "模板名称")*/
    private String tsName;

    /** 是否有效(1-有效,2-无效)*/
    private Integer valid;
}

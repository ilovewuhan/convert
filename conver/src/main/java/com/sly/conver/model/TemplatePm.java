package com.sly.conver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 体质单模板
 */
@Data
public class TemplatePm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**(value = "体质单用药模板ID*/
    private Integer tpId;

    /**(value = "模板名称*/
    private String tpName;

    /**(value = "医生id*/
    private Integer doctorId;

    /**(value = "类别(1.个人2.公共)*/
    private Integer tpType;

    /**(value = "个人模板类别Id*/
    private Integer personalTypeId;

    /**(value = "状态(1.正常2.删除)*/
    private Integer tpStatus;

    /**(value = "诊所id*/
    private Integer clinicId;

    /**(value = "是否有效(1-有效,2-无效)*/
    private Integer valid;
}

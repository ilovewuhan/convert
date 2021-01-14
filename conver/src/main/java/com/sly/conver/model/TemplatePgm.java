package com.sly.conver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目单模板
 */
@Data
public class TemplatePgm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**(value = "其他项目模板ID*/
    private Integer tpgId;

    /**(value = "模板名称*/
    private String tpgName;

    /**(value = "医生id*/
    private Integer doctorId;

    /**(value = "类别(1.个人2.公共)*/
    private Integer tpgType;

    /**(value = "个人模板类别Id*/
    private Integer personalTypeId;

    /**(value = "状态(1.正常2.删除)*/
    private Integer tpgStatus;

    /**(value = "诊所id*/
    private Integer clinicId;

    /**(value = "是否有效(1-有效,2-无效)*/
    private Integer valid;
}

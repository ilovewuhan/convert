package com.sly.conver.model;

import lombok.Data;

/**
 * 私人模板类别
 * @author zzd
 * @since 2020-07-28
 */
@Data
public class PersonalTemplateType {

    /**(value = "私人模板类别id*/
    private Integer ptId;

    /**(value = "个人模板类别名称*/
    private String ptName;

    /**(value = "个人模板类别状态（1正常 2其他）*/
    private Integer ptStatus;

    /**(value = "诊所Id*/
    private Integer clinicId;

    /**(value = "处方类型(1贴敷,2西药,3中药,体质调理单)*/
    private Integer type;

    /**(value = "模板类型（1个人，2公共*/
    private Integer ptpType;

    /**(value = "医生id*/
    private Integer doctorId;
}

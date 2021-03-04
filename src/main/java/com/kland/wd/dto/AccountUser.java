package com.kland.wd.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wd
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AccountUser对象", description="")
public class AccountUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "个人真实姓名")
    @TableField("grealName")
    private String grealName;

    @ApiModelProperty(value = "个人性别")
    @TableField("gSex")
    private String gSex;

    @ApiModelProperty(value = "个人出生日期")
    @TableField("gbirthDate")
    private Date gbirthDate;

    @ApiModelProperty(value = "个人教育背景")
    @TableField("geducBackGround")
    private String geducBackGround;

    @ApiModelProperty(value = "个人手机号")
    @TableField("gPhoneNumber")
    private String gPhoneNumber;

    @ApiModelProperty(value = "邮箱")
    @TableField("gMailbox")
    private String gMailbox;

    @ApiModelProperty(value = "证件类型")
    @TableField("gIDType")
    private String gIDType;

    @ApiModelProperty(value = "证件号")
    @TableField("gIDNumb")
    private String gIDNumb;

    @ApiModelProperty(value = "省")
    @TableField("gProvince")
    private String gProvince;

    @ApiModelProperty(value = "市")
    @TableField("gCity")
    private String gCity;

    @ApiModelProperty(value = "所在地址")
    @TableField("gAddress")
    private String gAddress;

    @ApiModelProperty(value = "所在公司")
    @TableField("gCompany")
    private String gCompany;

    @ApiModelProperty(value = "个人描述")
    @TableField("gDescribe")
    private String gDescribe;

    @TableField("createdTime")
    private Date createdTime;

    @TableField("updatedTime")
    private Date updatedTime;

    @ApiModelProperty(value = "录入人userId")
    @TableField("createUserId")
    private String createUserId;

    @ApiModelProperty(value = "录入人用户名")
    @TableField("createUserName")
    private String createUserName;

    @ApiModelProperty(value = "录入人部门")
    @TableField("createUserdept")
    private String createUserdept;

    @ApiModelProperty(value = "转世账号条数")
    private Integer zsnum;

    @ApiModelProperty(value = "引擎推荐账号数")
    private Integer ypnum;

    @ApiModelProperty(value = "头像文件id")
    @TableField("accessoryId")
    private Integer accessoryId;

    @ApiModelProperty(value = "手动录入数")
    @TableField("lrNum")
    private Integer lrNum;

    @ApiModelProperty(value = "数据来源：-1是手动添加的普通账号；0，1，2是企业账号，1是企业推送（神通库），0是本地录入，2是地方网信办导入")
    private Integer siteAccFrom;


}

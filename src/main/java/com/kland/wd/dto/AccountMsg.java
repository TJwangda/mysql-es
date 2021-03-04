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
@ApiModel(value="AccountMsg对象", description="")
public class AccountMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "管控类型")
    private String controlType;

    @ApiModelProperty(value = "附件")
    private String attachment;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "网站")
    private String siteName;

    @ApiModelProperty(value = "群ID")
    private String groupId;

    @ApiModelProperty(value = "公众号")
    private String publicId;

    private Date createTime;

    @ApiModelProperty(value = "处置时间")
    private Date disposeTime;

    private Long uploadTaskId;

    @ApiModelProperty(value = "消息ID")
    private String msgId;

    @ApiModelProperty(value = "发布时间")
    private String publishTimeStr;

    @ApiModelProperty(value = "岗位值班员")
    private String disposeUser;

    @ApiModelProperty(value = "原发ID")
    private String orginId;

    private String pageNo;

    private Long updateUserId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "地域")
    private String local;

    @ApiModelProperty(value = "粉丝数")
    private Integer fansNum;

    @ApiModelProperty(value = "查询索引用")
    private String indexMsg;

    @ApiModelProperty(value = "处置时间字符串")
    private String disposeTimeStr;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "消息URL")
    private String url;

    @ApiModelProperty(value = "消息类型")
    private String msgType;

    private String type;

    @ApiModelProperty(value = "处置措施")
    private String dispose;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "平台")
    private String siteChannel;

    @ApiModelProperty(value = "账号基本描述")
    private String basicDesc;

    @ApiModelProperty(value = "违规原因分析")
    private String illegalReason;

    @ApiModelProperty(value = "违规记录")
    private String illegalRecords;

    @ApiModelProperty(value = "推荐账号")
    private String recommendAccount;

    @ApiModelProperty(value = "通行证")
    private String txpass;

    @ApiModelProperty(value = "用户注册IP")
    @TableField("registerIp")
    private String registerIp;

    @ApiModelProperty(value = "用户绑定手机")
    private String bindphone;

    @ApiModelProperty(value = "注册时间")
    @TableField("registerTime")
    private Date registerTime;

    @ApiModelProperty(value = "是否被封号")
    private String isban;

    @ApiModelProperty(value = "基本信息")
    @TableField("baseInfo")
    private String baseInfo;

    @ApiModelProperty(value = "发布内容、消息内容，内容")
    private String content;

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

    @ApiModelProperty(value = "所属单位，账号数据来自哪个局")
    @TableField("fromDepart")
    private String fromDepart;

    @ApiModelProperty(value = "录入人userId")
    @TableField("createUserId")
    private String createUserId;

    @ApiModelProperty(value = "录入人用户名")
    @TableField("createUserName")
    private String createUserName;

    @ApiModelProperty(value = "录入人部门")
    @TableField("createUserdept")
    private String createUserdept;

    @ApiModelProperty(value = "认证信息")
    @TableField("authInfo")
    private String authInfo;

    @ApiModelProperty(value = "关注数")
    @TableField("attentionNum")
    private Integer attentionNum;

    @ApiModelProperty(value = "发帖数")
    @TableField("postNum")
    private Integer postNum;

    @ApiModelProperty(value = "转世推荐原因")
    @TableField("zsRecomRes")
    private String zsRecomRes;

    @TableField("relaParentId")
    private Integer relaParentId;

    @TableField("zsParentId")
    private Integer zsParentId;

    @TableField("isRecommend")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否主账号，1是，2否")
    @TableField("isMasterAccount")
    private Integer isMasterAccount;

    @ApiModelProperty(value = "转世账号条数")
    private Integer zsaccountnum;

    @ApiModelProperty(value = "是否启用,0否，1是")
    private Integer states;

    @ApiModelProperty(value = "引擎推荐账号数")
    private Integer recaccountnum;

    @ApiModelProperty(value = "第三方认证类型及编号")
    private String thirdauthaccount;

    @ApiModelProperty(value = "来源信息")
    private String sourcesinform;

    private Integer logid;

    private String iconpath;

    @TableField("accountUserId")
    private Integer accountUserId;

    @ApiModelProperty(value = "账号主体类别")
    private String zhztlb;

    @ApiModelProperty(value = "账号运营内容类别")
    private String zhyynrlb;

    @ApiModelProperty(value = "账号分级")
    private String zhfj;

    @ApiModelProperty(value = "处置标签")
    private String czbq;

    @ApiModelProperty(value = "是否为企业标注黑名单")
    private String sfhmd;

    @ApiModelProperty(value = "研判时间")
    @TableField("markTime")
    private Date markTime;

    @TableField("recStates")
    private Integer recStates;

    @ApiModelProperty(value = "渠道平台，同步mpp库和神通库字段")
    private String gasp;

    @ApiModelProperty(value = "账号引擎分析时间")
    @TableField("recTime")
    private Date recTime;

    private Date uSnapshotTime;

    private Date uGatherTime;

    private Integer uoStatus;

    private Integer siteAccFrom;


}

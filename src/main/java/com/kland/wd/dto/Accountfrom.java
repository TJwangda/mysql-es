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
@ApiModel(value="Accountfrom对象", description="")
public class Accountfrom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "来源名称")
    private String fromdepart;

    @ApiModelProperty(value = "操作时间，不用传值，自动生成")
    @TableField("creatTime")
    private Date creatTime;

    @TableField("updatedTime")
    private Date updatedTime;

    @TableField("accountMsgId")
    private Integer accountMsgId;


}

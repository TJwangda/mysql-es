package com.kland.wd.vo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * es文档实体
 */
@Data
@Document(indexName = "accountmsg")
public class EsAccMsgDto {
    private String id;
    private String siteName;
    private String nickName;
    private String siteAccFrom;
    private Date createTime;
    private String dispose;
    private String illegalReason;
    private String gasp;
    private String sfhmd;
    private Integer accountUserId;
    @Override
    public String toString() {
        return "AccMsgDto{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

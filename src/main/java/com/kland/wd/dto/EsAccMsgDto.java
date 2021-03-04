package com.kland.wd.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * es文档实体
 */
@Data
@Document(indexName = "accountmsg")
public class EsAccMsgDto {
    private String id;
    private String siteName;
    private String nickName;

    @Override
    public String toString() {
        return "AccMsgDto{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

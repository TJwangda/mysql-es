package com.kland.wd.vo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * es文档实体
 */
@Data
@Document(indexName = "accountuser")
public class EsAccUserDto {
    private String id;
    private String grealName;
    private String gIDNumb;
    private String zsnum;
    private String ypnum;
    private String lrNum;


}

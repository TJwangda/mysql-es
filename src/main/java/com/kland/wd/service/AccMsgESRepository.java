package com.kland.wd.service;

import com.kland.wd.vo.EsAccMsgDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccMsgESRepository extends ElasticsearchRepository<EsAccMsgDto, Integer> {
    public List<EsAccMsgDto> findByNickNameLike(String nickName);
    public List<EsAccMsgDto> findBySiteNameLike(String SiteName);
}

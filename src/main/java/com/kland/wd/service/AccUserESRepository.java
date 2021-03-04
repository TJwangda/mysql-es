package com.kland.wd.service;

import com.kland.wd.vo.EsAccMsgDto;
import com.kland.wd.vo.EsAccUserDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccUserESRepository extends ElasticsearchRepository<EsAccUserDto, Integer> {
//    public List<EsAccUserDto> findByNickNameLike(String nickName);
//    public List<EsAccUserDto> findBySiteNameLike(String SiteName);
}

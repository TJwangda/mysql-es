package com.kland.wd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kland.wd.dto.AccountMsg;
//import com.kland.wd.service.AccountMsgService;
import com.kland.wd.service.AccMsgESRepository;
import com.kland.wd.vo.EsAccMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wd
 * @since 2021-03-04
 */
@Slf4j
@Service
public class AccountMsgServiceImpl<AccountMsgMapper extends BaseMapper<AccountMsg>> extends ServiceImpl<AccountMsgMapper, AccountMsg> {

    @Autowired
    AccountMsgMapper accountMsgMapper;
    @Autowired
    AccMsgESRepository accMsgESRepository;

    public void selectAll(){
        List<AccountMsg> accountMsgs = accountMsgMapper.selectList(null);
        accountMsgs.forEach(System.out::println);
    }

    /**
     * 查询最大账号id
     * @return
     */
    public int selectMaxId(){
        int maxId = 0;
        QueryWrapper<AccountMsg> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id").last(" limit 1");
        List<AccountMsg> accountMsgs = accountMsgMapper.selectList(wrapper);
        maxId = accountMsgs.get(0).getId();
        return maxId;
    }

    /**
     * 分批导入全部mysql数据到es
     */
    public void synMysql2Es(){
        int maxId = this.selectMaxId();
        int onceLimist = (int) Math.floor(maxId/300);//目前生产300万数据
        if(onceLimist<1){
            onceLimist=1;
        }
        int index = 0;
        int sum = 0;
        log.info("开始处理=====》："+index);
        while (index<maxId) {

            int endId = index + onceLimist - 1;
            QueryWrapper<AccountMsg> wrapper = new QueryWrapper();
            wrapper.between("id",index,endId);
            List<AccountMsg> accountMsgs = accountMsgMapper.selectList(wrapper);
            int count = accountMsgs.size();
            sum = sum + count;

            log.info("index===>:"+index);
            log.info("accountMsgs长度======>："+count);
            index = index + onceLimist;

            //查询结束。装配对象导入es
//            List<EsAccMsgDto> esAccMsgDtos = new ArrayList<>();
//            accMsgESRepository.saveAll(esAccMsgDtos);
        }
        log.info("处理结束总长度=====》："+sum);
    }


}

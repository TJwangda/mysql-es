package com.kland.wd.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kland.wd.dto.AccountMsg;
//import com.kland.wd.service.AccountMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wd
 * @since 2021-03-04
 */
@Service
//public class AccountMsgServiceImpl<AccountMsgMapper extends BaseMapper<AccountMsg>> extends ServiceImpl<AccountMsgMapper, AccountMsg> implements AccountMsgService {
public class AccountMsgServiceImpl<AccountMsgMapper extends BaseMapper<AccountMsg>> extends ServiceImpl<AccountMsgMapper, AccountMsg> {

    @Autowired
    AccountMsgMapper accountMsgMapper;

    public void selectAll(){
        List<AccountMsg> accountMsgs = accountMsgMapper.selectList(null);
        accountMsgs.forEach(System.out::println);
    }


}

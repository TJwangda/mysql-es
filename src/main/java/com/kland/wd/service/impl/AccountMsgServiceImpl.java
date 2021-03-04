package com.kland.wd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kland.wd.dto.AccountMsg;

import com.kland.wd.service.AccountMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kland.wd.mapper.AccountMsgMapper;
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
public class AccountMsgServiceImpl extends ServiceImpl<AccountMsgMapper, AccountMsg> implements AccountMsgService {

    @Autowired
    AccountMsgMapper accountMsgMapper;

    public void selectAll(){
        AccountMsg accountMsg = accountMsgMapper.selectById(33);
        System.out.println(accountMsg);
    }


}

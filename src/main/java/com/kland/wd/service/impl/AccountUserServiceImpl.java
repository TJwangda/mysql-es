package com.kland.wd.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kland.wd.dto.AccountUser;
import com.kland.wd.mapper.AccountUserMapper;
//import com.kland.wd.service.AccountUserService;
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
//public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser>{
    @Autowired
    AccountUserMapper accountUserMapper;

    public int findMaxId(){
        int maxId = 0;
        QueryWrapper<AccountUser> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        List<AccountUser> users = accountUserMapper.selectList(wrapper);
        AccountUser user = users.get(0);
        maxId = user.getId();
        System.out.println("maxId:"+maxId);
        return maxId;
    }
}

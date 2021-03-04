package com.kland.wd;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.kland.wd.dto.AccountUser;
import com.kland.wd.mapper.AccountUserMapper;
import com.kland.wd.service.impl.AccountMsgServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WdApplicationTest2 {

    @Autowired
    AccountMsgServiceImpl accountMsgService;
//    @Autowired
//    AccountMsgMapper accountMsgMapper;
    @Autowired
    AccountUserMapper accountUserMapper;

    @Test
    public void test(){
        Page<AccountUser> page = new Page<>(1,1);
        QueryWrapper<AccountUser> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        IPage<AccountUser> accountUserIPage = accountUserMapper.selectPage(page, wrapper);
        List<AccountUser> records = accountUserIPage.getRecords();
        records.forEach((data)->{
            System.out.println("============"+data);
        });
    }

    @Test
    public void test2(){
        QueryWrapper<AccountUser> wrapper = new QueryWrapper<>();
        wrapper.between("id",4096,4730);
        List<AccountUser> accountUsers = accountUserMapper.selectList(wrapper);
        accountUsers.forEach((data)->{
            System.out.println("====="+data);
        });
    }

    @Test
    public void test3(){
        accountMsgService.synMysql2Es();
    }
    /**
     * // 代码自动生成器
     * @param args
     */
    public static void main(String[] args) {


    }

}
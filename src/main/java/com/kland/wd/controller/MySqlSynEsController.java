package com.kland.wd.controller;

import com.kland.wd.service.impl.AccountUserServiceImpl;
import com.kland.wd.vo.EsAccMsgDto;
import com.kland.wd.service.AccMsgESRepository;
import com.kland.wd.service.impl.AccountMsgServiceImpl;
import com.kland.wd.util.SimpleCanalClientExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mysql增量同步es控制器
 */
@RestController
@RequestMapping("/api/mySqlSyn")
public class MySqlSynEsController {
    @Autowired
    AccMsgESRepository accMsgESRepository;
    @Autowired
    AccountMsgServiceImpl accountMsgService;
    @Autowired
    AccountUserServiceImpl accountUserService;
    @Autowired
    SimpleCanalClientExample simpleCanalClientExample;

    /**
     * 增量同步mysql接口
     */
    @GetMapping("/synTable")
    public String SynTable(){
        simpleCanalClientExample.synTable();
        return "";
    }

    /**
     * 全量同步数据到es
     * 分批导入？
     * @return
     */
    @GetMapping("/allSynTable")
    public String allSynTable(){
//        simpleCanalClientExample.allSynTable();
        return "导入成功";
    }

    @GetMapping("/test")
    public String test(){
//        accountMsgService.selectAll();
//        accountUserService.findMaxId();
        accountMsgService.synMysql2Es();
        return "hello world";
    }

    /**
     * 测试添加
     * @return
     */
    @GetMapping("/addEsAccMsg")
    public String addEsAccMsg(){
        System.out.println("=======进入");
        EsAccMsgDto acc = new EsAccMsgDto();
        acc.setId("1");
        acc.setNickName("wd");
        acc.setSiteName("新浪");
        accMsgESRepository.index(acc);
        return "添加成功";
    }

    /**
     * 测试查询
     * @return
     */
    @GetMapping("/findbyName")
    public String findbyName(){
        // 测试是否通过findByBookNameLike的模糊查询找到西游记
        for (EsAccMsgDto acc : accMsgESRepository.findBySiteNameLike("新")) {
            System.out.println(acc);
        }
        return "findbyName";
    }
}

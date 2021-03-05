package com.kland.wd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kland.wd.dto.AccountMsg;
//import com.kland.wd.service.AccountMsgService;
import com.kland.wd.service.AccMsgESRepository;
import com.kland.wd.vo.EsAccMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;


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
        log.info("开始处理时间=====》："+new Date());
        while (index<maxId) {

            int endId = index + onceLimist - 1;
            QueryWrapper<AccountMsg> wrapper = new QueryWrapper();
            wrapper.between("id",index,endId);
            List<AccountMsg> list = new ArrayList<>();
            List<AccountMsg> accountMsgs = Collections.synchronizedList(list);
            accountMsgs = accountMsgMapper.selectList(wrapper);
            int count = accountMsgs.size();
            sum = sum + count;

            log.info("index===>:"+index);
            log.info("accountMsgs长度======>："+count);
            index = index + onceLimist;

            //查询结束。装配对象导入es
            if(0 < accountMsgs.size()){
                try {
                    List<EsAccMsgDto> esAccMsgDtos = accDto2EsAccDto(accountMsgs);
                    accMsgESRepository.saveAll(esAccMsgDtos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error(e.getMessage()+e.getCause());
                }
            }

        }
        log.info("处理结束总长度=====》："+sum);
        log.info("处理结束时间=====》："+new Date());
    }

    /**
     * mysql账号实体拼接es实体
     * @return
     */
    private List<EsAccMsgDto> accDto2EsAccDto(List<AccountMsg> accs) throws InterruptedException {
        ArrayList<EsAccMsgDto> list = new ArrayList<>();
        List<EsAccMsgDto> esAccMsgDtos = Collections.synchronizedList(list);

        System.out.println("cpu核数"+Runtime.getRuntime().availableProcessors());//获取运行是cpu的核数
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),//阻塞队列超过三个时，触发最大容量
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());//最大容量满了尝试和最早的竞争，如果最早线程任务处理结束了，可以执行，否则依然丢掉不报异常
        final CountDownLatch latch = new CountDownLatch(5);//定义多线程数量，一般就是任务数量
        for(int i = 1 ; i <= 5 ; i++ ){
            threadPool.execute(()->{
                try {
                    accs.forEach((acc)->{
                        //实体转换
                        try {
                            EsAccMsgDto esAccMsgDto = new EsAccMsgDto();
                            esAccMsgDto.setId(String.valueOf(acc.getId()));
                            esAccMsgDto.setNickName(acc.getNickName());
                            esAccMsgDto.setSiteName(acc.getSiteName());
                            esAccMsgDto.setCreateTime(acc.getCreateTime());
                            esAccMsgDto.setDispose(acc.getDispose());
                            esAccMsgDto.setGasp(acc.getGasp());
                            esAccMsgDto.setSiteAccFrom(String.valueOf(acc.getSiteAccFrom()));
                            esAccMsgDto.setIllegalReason(acc.getIllegalReason());
                            esAccMsgDto.setSfhmd(acc.getSfhmd());
                            esAccMsgDto.setAccountUserId(acc.getAccountUserId());
                            //....其他字段待定
                            esAccMsgDtos.add(esAccMsgDto);
                        } catch (Exception e) {
                            log.error("==>"+e.getMessage()+e.getCause());
                        }
                    });
                } finally {
                    latch.countDown();//每个任务执行完成后对数量进行减一操作
                    threadPool.shutdown();//用完放回
                }
            });
        }
        latch.await();//等待,不断检测数量是否为0，为零是执行后面的操作
        return esAccMsgDtos;
    }


}

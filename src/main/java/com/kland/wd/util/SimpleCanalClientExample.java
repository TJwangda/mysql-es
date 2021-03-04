package com.kland.wd.util;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.kland.wd.service.AccUserESRepository;
import com.kland.wd.vo.EsAccMsgDto;
import com.kland.wd.service.AccMsgESRepository;
import com.kland.wd.vo.EsAccUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.List;
@Slf4j
@Service
public class SimpleCanalClientExample {

    //监控的表名
    @Value("${syntableName}")
    private static String syntableName;

    @Autowired
    AccMsgESRepository accMsgESRepository;
    @Autowired
    AccUserESRepository accUserESRepository;

    /**
     * mysql增量同步es
     */
    public void synTable() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
//            connector.subscribe(".*\\..*");
            connector.subscribe(syntableName);
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            connector.disconnect();
        }
    }

    private void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            log.info(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));
            //账号表
            if("account_msg".equals(entry.getHeader().getTableName())){
                for (RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == EventType.DELETE) {
                        //删除
                        EsAccMsgDto accMsgDto = splicingEntity(rowData.getBeforeColumnsList());
                        accMsgESRepository.delete(accMsgDto);
                    } else if (eventType == EventType.INSERT) {
                        //添加
                        EsAccMsgDto accMsgDto = splicingEntity(rowData.getAfterColumnsList());
                        accMsgESRepository.save(accMsgDto);
                    } else {
                        //修改
                        log.info("-------&gt; before");
                        EsAccMsgDto accMsgDtoBef = splicingEntity(rowData.getBeforeColumnsList());//修改之前的对象
                        log.info(""+accMsgDtoBef);

                        log.info("-------&gt; after");
                        EsAccMsgDto accMsgDtoAfter = splicingEntity(rowData.getAfterColumnsList());//修改之后的对象
                        log.info(""+accMsgDtoAfter);
                        accMsgESRepository.save(accMsgDtoAfter);
                    }
                }
                //用户表
            }else if("account_user".equals(entry.getHeader().getTableName())){
                for (RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == EventType.DELETE) {
                        //删除
                        EsAccUserDto accUserDto = splicingAccUserEntity(rowData.getBeforeColumnsList());
                        accUserESRepository.delete(accUserDto);
                    } else if (eventType == EventType.INSERT) {
                        //添加
                        EsAccUserDto accUserDto = splicingAccUserEntity(rowData.getAfterColumnsList());
                        accUserESRepository.save(accUserDto);
                    } else {
                        //修改
                        log.info("-------&gt; before");
                        EsAccUserDto accUserDtoBef = splicingAccUserEntity(rowData.getBeforeColumnsList());//修改之前的对象
                        log.info(""+accUserDtoBef);

                        log.info("-------&gt; after");
                        EsAccUserDto accUserDtoAfter = splicingAccUserEntity(rowData.getAfterColumnsList());//修改之后的对象
                        log.info(""+accUserDtoAfter);
                        accUserESRepository.save(accUserDtoAfter);
                    }
                }
            }

        }
    }

    private void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    /**
     * mysql对象拼接账号es实体
     */
    public EsAccMsgDto splicingEntity(List<Column> columns){
        EsAccMsgDto acc = new EsAccMsgDto();
        for (Column column : columns) {
            if("id".equals(column.getName())){
                acc.setId(column.getValue());
            }else if("nick_name".equals(column.getName())){
                acc.setNickName(column.getValue());
            }else if("site_name".equals(column.getName())){
                acc.setSiteName(column.getValue());
            }
        }
        return acc;
    }

    /**
     * mysql对象拼接账号es实体
     */
    public EsAccUserDto splicingAccUserEntity(List<Column> columns){
        EsAccUserDto accUser = new EsAccUserDto();
        for (Column column : columns) {
            if("id".equals(column.getName())){
                accUser.setId(column.getValue());
            }else if("grealName".equals(column.getName())){
                accUser.setGrealName(column.getValue());
            }else if("gIDNumb".equals(column.getName())){
                accUser.setGIDNumb(column.getValue());
            }else if("zsnum".equals(column.getName())){
                accUser.setZsnum(column.getValue());
            }else if("ypnum".equals(column.getName())){
                accUser.setYpnum(column.getValue());
            }else if("lrNum".equals(column.getName())){
                accUser.setLrNum(column.getValue());
            }
        }
        return accUser;
    }

}

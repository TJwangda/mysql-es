package com.kland.wd.util;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.kland.wd.vo.EsAccMsgDto;
import com.kland.wd.service.AccMsgESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.List;

@Service
public class SimpleCanalClientExample {

    //监控的表名
    @Value("${syntableName}")
    private static String syntableName;

    @Autowired
    AccMsgESRepository accMsgESRepository;

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
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

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
                    System.out.println("-------&gt; before");
                    EsAccMsgDto accMsgDtoBef = splicingEntity(rowData.getBeforeColumnsList());//修改之前的对象
                    System.out.println(accMsgDtoBef);

                    System.out.println("-------&gt; after");
                    EsAccMsgDto accMsgDtoAfter = splicingEntity(rowData.getAfterColumnsList());//修改之后的对象
                    System.out.println(accMsgDtoAfter);
                    accMsgESRepository.save(accMsgDtoAfter);
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
     * 拼接账号es实体
     */
    public EsAccMsgDto splicingEntity(List<Column> columns){
        EsAccMsgDto acc = new EsAccMsgDto();
        for (Column column : columns) {
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
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
     * 全量导入mysql数据到es
     */
    public void allSynTable(){

    }
}

# elasticsearch同步mysql数据

1. es不支持多表联查

# canal

> 简介

**canal [kə'næl]**，译意为水道/管道/沟渠，主要用途是基于 MySQL 数据库增量日志解析，提供增量数据订阅和消费

早期阿里巴巴因为杭州和美国双机房部署，存在跨机房同步的业务需求，实现方式主要是基于业务 trigger 获取增量变更。从 2010 年开始，业务逐步尝试数据库日志解析获取增量变更进行同步，由此衍生出了大量的数据库增量订阅和消费业务。

基于日志增量订阅和消费的业务包括

- 数据库镜像
- 数据库实时备份
- 索引构建和实时维护(拆分异构索引、倒排索引等)
- 业务 cache 刷新
- 带业务逻辑的增量数据处理

当前的 canal 支持源端 MySQL 版本包括 5.1.x , 5.5.x , 5.6.x , 5.7.x , 8.0.x

![image-20210222100033954](E:\dev\picture\image-20210222100033954.png)

> 工作原理

1. #### MySQL主备复制原理

![image-20210222101343655](E:\dev\picture\image-20210222101343655.png).

- MySQL master 将数据变更写入二进制日志( binary log, 其中记录叫做二进制日志事件binary log events，可以通过 show binlog events 进行查看)
- MySQL slave 将 master 的 binary log events 拷贝到它的中继日志(relay log)
- MySQL slave 重放 relay log 中事件，将数据变更反映它自己的数据

2. #### canal 工作原理

- canal 模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，向 MySQL master 发送dump 协议
- MySQL master 收到 dump 请求，开始推送 binary log 给 slave (即 canal )
- canal 解析 binary log 对象(原始为 byte 流)



canal 作为 MySQL binlog 增量获取和解析工具，可将变更记录投递到 MQ 系统中，比如 Kafka/RocketMQ，可以借助于 MQ 的多语言能力。https://github.com/alibaba/canal/wiki/Canal-Kafka-RocketMQ-QuickStart

> windows 启动canal

~~~sql
#创建canal用户，密码为canal
create user canal identified by ‘canal’;
#授权
grant select,replication slave,replication client on*.* to ‘canal’@’%’;

flush privileges -- 刷新文件内容到内存中
注：如果授权语句执行报错。需要检查root有没有grant的权限
~~~

![image-20210224095339389](E:\dev\picture\image-20210224095339389.png)

![image-20210224095358975](E:\dev\picture\image-20210224095358975.png)

![image-20210224095414726](E:\dev\picture\image-20210224095414726.png)

~~~xml
# 修改成要连接的mysql的地址
canal.instance.master.address=127.0.0.1:3306 
~~~

![image-20210224105945634](E:\dev\picture\image-20210224105945634.png)

> 相关命令

![image-20210224111215065](E:\dev\picture\image-20210224111215065.png).

![image-20210224112017965](E:\dev\picture\image-20210224112017965.png)

![image-20210224112045744](E:\dev\picture\image-20210224112045744.png)

> 流程图

![image-20210224115202373](E:\dev\picture\image-20210224115202373.png)

> 代码依赖

![image-20210224115411890](E:\dev\picture\image-20210224115411890.png).



# 增量同步 canal读取binlog

> 如果canal挂了或者同步的服务挂了，此时mysql 表中进行数据操作，重启canal还能继续从上次拉取的位置继续吗？

不管是服务停了还是canal挂了，重启以后都可以继续从上次中断的地方继续拉取。



# 全量同步？三百万？
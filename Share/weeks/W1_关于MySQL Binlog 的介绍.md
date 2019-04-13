- ## 关于 MySQL Binlog 的介绍

- MySQL Binlog

- Binlog 的相关概念

- 什么是  Binlog

- Binlog 是 MySQL Server 维护的一种二进制日志，主要是用来记录对  MySQL 数据更新或潜在发生更新的 SQL 语句，并以"事务"的形式保存在磁盘中（文件）

- 主要用途

- - 1. 复制：MySQL 的 Master-Slave 协议，让 Slave 可以通过监听 Binlog 实现数据复制，达到数据一致的目的
  - 2. 数据恢复：通过 mysqlbinlog 工具恢复数据
  - 3. 增量备份

- 支持的格式

- - ROW

- 仅保存记录被修改细节，不记录  SQL 语句上下文相关信息：能非常清晰的记录下每行数据的修改细节，不需要记录上下文相关信息，因此不会发生某些特定情况下的 procedure、function、及  trigger 的调用触发无法被正确复制的问题，任何情况都可以被复制，且能加快从库重放日志的效率，保证从库数据的一致性。

- - STATEMENT

- 每一条会修改数据的 SQL 都会记录在 Binlog 中：只需要记录执行语句的细节和上下文环境，避免了记录每一行的变化，在一些修改记录较多的情况下相比 ROW 类型能大大减少 Binlog 日志量，节约IO

- ，提高性能；还可以用于实时的还原；同时主从版本可以不一样，从服务器版本可以比主服务器版本高。 

- - MIXED

- 以上两种类型的混合使用。经过前面的对比，可以发现 ROW 类型和 STATEMENT 类型各有优势，如能根据 SQL 语句取舍可能会有更好地性能和效果；MIXED 便是以上两种类型的结合

- Binlog 的相关命令

- 相关变量

- -- Binlog 开关变量
     mysql> show variables like 'log_bin';
     +---------------+-------+
     | Variable_name | Value |
     +---------------+-------+
     | log_bin       | ON     |
     +---------------+-------+
     1 row in set  (0.30 sec)

-  

- -- Binlog 日志的格式
     mysql> show variables like 'binlog_format';
     +---------------+-------+
     | Variable_name | Value |
     +---------------+-------+
     | binlog_format | ROW   |
     +---------------+-------+

-  

- 1 row in  set (0.00  sec)

- -- Binlog 设置日志变量

- mysql> set global binlog_format='row';

- Query OK, 0 rows affected

- 

- 常用操作命令

- |                           SQL语句                            |                           语句含义                           |
  | :----------------------------------------------------------: | :----------------------------------------------------------: |
  |                      show master logs;                       |                  查看所有 Binlog 的日志列表                  |
  |                     show master status;                      | 查看最后一个 Binlog    日志的编号名称，及最后一个事件结束的位置（pos） |
  |                         flush logs;                          |   刷新    Binlog，此刻开始产生一个新编号的 Binlog 日志文件   |
  |                        reset master;                         |                    清空所有的 Binlog 日志                    |
  |                     show binlog events;                      |                    查看第一个 Binlog 日志                    |
  |          show binlog events    in ‘binlog.000030’;           |                    查看指定的 Binlog 日志                    |
  |      show binlog events    in ‘binlog.000030’ from 931;      |         从指定的位置开始，查看指定的    Binlog 日志          |
  |  show binlog events    in ‘binlog.000030’ from 931 limit 2;  | 从指定的位置开始，查看指定的    Binlog 日志，限制查询的条数  |
  | show binlog events    in ‘binlog.000030’ from 931 limit 1, 2; | 从指定的位置开始，带有偏移，查看指定的    Binlog 日志，限制查询的条数 |

- Binlog 的 Event 类型

- MySQL Binlog Event 类型有很多种（MySQL 官方定义了 36 种），例如：XID、TABLE_MAP、QUERY 等等。但是，我们需要了解的（也是最常用的）类型不是很多。

- |    Event Type     |                            事件                            | 重要程度 |
  | :---------------: | :--------------------------------------------------------: | :------: |
  |    QUERY_EVENT    | 与数据无关的操作， begin、drop    table、truncate table 等 | 了解即可 |
  |     XID_EVENT     |                        标记事务提交                        | 了解即可 |
  |  TABLE_MAP_EVENT  |     记录下一个操作所对应的表信息，存储了数据库名和表名     | 非常重要 |
  | WRITE_ROWS_EVENT  |                  插入数据，即 insert 操作                  | 非常重要 |
  | UPDATE_ROWS_EVENT |                  更新数据，即 update 操作                  | 非常重要 |
  | DELETE_ROWS_EVENT |                  删除数据，即 delete 操作                  | 非常重要 |

- 示例

- ![5ab9976ly1fzd8s0a190j21ch0kdtqf.jpg (690Ã290)](file:///C:/Users/alex_/AppData/Local/Temp/msohtmlclip1/01/clip_image001.jpg)

- 截取本地两行数据

- mysql>  show binlog events in 'mysql-bin.000024';

- | Log_name         | Pos   | Event_type  | Server_id |  End_log_pos | Info     

-  

-  

- | mysql-bin.000024  |  259 | Query       |         1 |         964 | use `imooc_ad_data`; CREATE  TABLE `ad_user` (

-   `id` bigint(20) NOT NULL AUTO_INCREMENT  COMMENT '自增主键',

-   `username` varchar(128) NOT NULL DEFAULT ''  COMMENT '用户名',

-   `token` varchar(256) NOT NULL DEFAULT ''  COMMENT '给用户生成的 token',

-   `user_status` tinyint(4) NOT NULL DEFAULT  '0' COMMENT '用户状态',

-   `create_time` datetime NOT NULL DEFAULT  '1970-01-01 00:00:00' COMMENT '创建时间',

-   `update_time` datetime NOT NULL DEFAULT  '1970-01-01 00:00:00' COMMENT '更新时间',

-   PRIMARY KEY (`id`),

-   UNIQUE KEY `username` (`username`)

- )  ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户信息表'                                                                                                                                                                                                                                                                                                                                                                                                                                                    |

- |  mysql-bin.000024 |  964 | Query       |         1 |        1805 | use `imooc_ad_data`; -- 推广计划表

- CREATE TABLE  `ad_plan` (

-   `id` bigint(20) NOT NULL AUTO_INCREMENT  COMMENT '自增主键',

-   `user_id` bigint(20) NOT NULL DEFAULT '0'  COMMENT '标记当前记录所属用户',

-   `plan_name` varchar(48) NOT NULL COMMENT '推广计划名称',

-   `plan_status` tinyint(4) NOT NULL DEFAULT  '0' COMMENT '推广计划状态',

-   `start_date` datetime NOT NULL COMMENT '推广计划开始时间；',

-   `end_date` datetime NOT NULL COMMENT '推广计划结束时间；',

-   `create_time` datetime NOT NULL DEFAULT  '1970-01-01 00:00:00' COMMENT '创建时间',

-   `update_time` datetime NOT NULL DEFAULT  '1970-01-01 00:00:00' COMMENT '更新时间',

-   PRIMARY KEY (`id`)

- )  ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='推广计划表'

-  

-  

-  

- ### 使用开源插件的几点说明

- 对于 MySQL  Binlog，我们可以不用过分追究 Binlog 里面到底包含了些什么，对于应用的话，我们最重要要搞清楚 Binlog 的 Event：每个 Event 包含 header 和 data 两个部分；

- （1）header 提供了 Event 的创建时间，哪个服务器等信息；

- （2）data 部分提供的是针对该 Event 的具体信息，如具体数据的修改。

- 我们对 Binlog 的解析，即为对 Event 的解析。

- - Binlog 的 EventType （需要注意，不同版本的 MySQL，EventType 可能会不同）
  - Binlog 中并不会打印数据表的列名

- select * from  information_schema.columns limit 1;

- | TABLE_CATALOG 

- | TABLE_SCHEMA 

- | TABLE_NAME    

- | COLUMN_NAME   

- | ORDINAL_POSITION 

- | COLUMN_DEFAULT

- | IS_NULLABLE 

- | DATA_TYPE 

- |  CHARACTER_MAXIMUM_LENGTH

- |  CHARACTER_OCTET_LENGTH

- | NUMERIC_PRECISION

- | NUMERIC_SCALE

- |  DATETIME_PRECISION 

- |  CHARACTER_SET_NAME 

- | COLLATION_NAME 

- | COLUMN_TYPE

- | COLUMN_KEY 

- | EXTRA 

- | PRIVILEGES                     

- | COLUMN_COMMENT 

- |  GENERATION_EXPRESSION 

- | SRS_ID

- 

- mysql>  select  TABLE_CATALOG, TABLE_SCHEMA,  TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT from  information_schema.columns WHERE TABLE_NAME='ad_creative' limit 2;

- +---------------+---------------+-------------+-------------+------------------+----------------+

- | TABLE_CATALOG |  TABLE_SCHEMA  | TABLE_NAME  | COLUMN_NAME | ORDINAL_POSITION |  COLUMN_DEFAULT |

- +---------------+---------------+-------------+-------------+------------------+----------------+

- | def           | imooc_ad_data | ad_creative |  id          |                1 | NULL           |

- | def           | imooc_ad_data | ad_creative |  name        |                2 | NULL           |

- +---------------+---------------+-------------+-------------+------------------+----------------+

-  

-  

-  

-  

-  
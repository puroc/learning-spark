/*******************************************************************************
 * @(#)NMSConstants.java 2016年12月12日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

/**
 * 常量定义信息表
 * 
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月12日 上午10:45:00
 */
public final class NMSConstants {
    /**
     * 创建一个新的实例 RestURL
     */
    private NMSConstants() {
    }

    /**
     * tagv的过滤规则: 精确匹配多项迭代值，多项迭代值以'|'分隔，大小写敏感
     */
    public static String FILTER_TYPE_LITERAL_OR = "literal_or";

    /**
     * tagv的过滤规则: 通配符匹配，大小写敏感
     */
    public static String FILTER_TYPE_WILDCARD = "wildcard";

    /**
     * tagv的过滤规则: 正则表达式匹配
     */
    public static String FILTER_TYPE_REGEXP = "regexp";

    /**
     * tagv的过滤规则: 精确匹配多项迭代值，多项迭代值以'|'分隔，忽略大小写
     */
    public static String FILTER_TYPE_ILITERAL_OR = "iliteral_or";

    /**
     * tagv的过滤规则: 通配符匹配，忽略大小写
     */
    public static String FILTER_TYPE_IWILDCARD = "iwildcard";

    /**
     * tagv的过滤规则: 通配符取非匹配，大小写敏感
     */
    public static String FILTER_TYPE_NOT_LITERAL_OR = "not_literal_or";

    /**
     * tagv的过滤规则: 通配符取非匹配，忽略大小写
     */
    public static String FILTER_TYPE_NOT_ILITERAL_OR = "not_iliteral_or";

    /**
     * 主机ID
     */
    public static final String NMS_CLUSTER_NODE_QUERYKEY = "cluster.node.id";
    
    /**
     * 集群ID
     */
    public static final String NMS_CLUSTER_ID_QUERYKEY = "cluster.id";

    /**
     * cpu
     */
    public static final String NMS_CPU_QUERYKEY = "cpu";

    /**
     * disk
     */
    public static final String NMS_DISK_QUERYKEY = "disk";

    /**
     * if
     */
    public static final String NMS_IF_QUERYKEY = "if";

    /**
     * K8S.C.IP
     */
    public static final String NMS_K8S_C_IP_QUERYKEY = "K8S.C.IP";

    /**
     * CPU空闲使用率
     */
    public static final String NMS_HOST_CPU_IDLE_METRIC = "system.cpu.idle";

    /**
     * CPU内核态使用率
     */
    public static final String NMS_HOST_CPU_SYS_METRIC = "system.cpu.sys";

    /**
     * CPU总使用率
     */
    public static final String NMS_HOST_CPU_USED_METRIC = "system.cpu.used";

    /**
     * CPU用户态使用率
     */
    public static final String NMS_HOST_CPU_USER_METRIC = "system.cpu.user";

    /**
     * IO等待占比
     */
    public static final String NMS_HOST_IO_WAIT_METRIC = "system.cpu.wait";
    
    /**
     * 分区容量(bytes)
     */
    public static final String NMS_HOST_DISK_TOTLE = "system.disk.totle";

    /**
     * 分区已用容量(bytes)
     */
    public static final String NMS_HOST_DISK_USED = "system.disk.used";

    /**
     * 分区使用率(%)
     */
    public static final String NMS_HOST_DISK_USERPERC = "system.disk.userperc";

    /**
     * 读字节
     */
    public static final String NMS_HOST_DISK_WRITEBYTES = "system.disk.writebytes";

    /**
     * 写字节
     */
    public static final String NMS_HOST_DISK_READBYTES = "system.disk.readbytes";

    /**
     * 内存可用容量
     */
    public static final String NMS_HOST_MEM_FREE = "system.mem.free";

    /**
     * 内存空闲率(%)
     */
    public static final String NMS_HOST_MEM_FREEPERC = "system.mem.freeperc";

    /**
     * 内存总容量(bytes)
     */
    public static final String NMS_HOST_MEM_TOTAL = "system.mem.total";

    /**
     * 内存已用容量(bytes)
     */
    public static final String NMS_HOST_MEM_USED = "system.mem.used";

    /**
     * 内存使用率(%)
     */
    public static final String NMS_HOST_MEM_USEDPERC = "system.mem.usedperc";

    /**
     * 流入数据(bytes)
     */
    public static final String NMS_HOST_RX_BYTES = "system.net.rx.bytes";

    /**
     * 流出数据(bytes)
     */
    public static final String NMS_HOST_TX_BYTES = "system.net.tx.bytes";

    /**
     * 主机平均负载(1分钟统计)
     */
    public static final String NMS_HOST_LOAD_1 = "system.load.1";
    
    /**
     * 主机平均负载(5分钟统计)
     */
    public static final String NMS_HOST_LOAD_5 = "system.load.5";
    
    /**
     * 主机平均负载(15分钟统计)
     */
    public static final String NMS_HOST_LOAD_15 = "system.load.15";
    
    /**
     * CPU使用率-核心态
     */
    public static final String NMS_CONTAINER_CPU_USAGE = "c.cpu.usage.system";

    /**
     * CPU使用率-用户态
     */
    public static final String NMS_CONTAINER_CPU_USER = "c.cpu.usage.user";

    /**
     * 内存-内存-最大可用量
     */
    public static final String NMS_CONTAINER_MEMORY_LIMIT = "c.memory.limit";

    /**
     * 内存-内存-使用量
     */
    public static final String NMS_CONTAINER_MEMORY_USAGE = "c.memory.usage";

    /**
     * 网络-流入吞吐量(每网卡)
     */
    public static final String NMS_CONTAINER_NETWORK_RX_BYTES = "c.network.rx_bytes";

    /**
     * 网络-流出吞吐量(每网卡)
     */
    public static final String NMS_CONTAINER_NETWORK_TX_BYTES = "c.network.tx_bytes";

    /**
     * DB
     */
    public static final String NMS_DB_QUERYKEY = "db";
    
    /**
     * 插入次数
     */
    public static final String NMS_MONGO_OPCOUNTERS_INSERT = "mongo.opcounters.insert";

    /**
     * 查询次数
     */
    public static final String NMS_MONGO_OPCOUNTERS_QUERY = "mongo.opcounters.query";

    /**
     * 更新次数
     */
    public static final String NMS_MONGO_OPCOUNTERS_UPDATE = "mongo.opcounters.update";

    /**
     * 删除次数
     */
    public static final String NMS_MONGO_OPCOUNTERS_DELETE = "mongo.opcounters.delete";

    /**
     * 执行命令数
     */
    public static final String NMS_MONGO_OPCOUNTERS_COMMAND = "mongo.opcounters.command";

    /**
     * 当前连接数
     */
    public static final String NMS_MONGO_CONNECTIONS_CURRENT = "mongo.connections.current";

    /**
     * 可用连接数
     */
    public static final String NMS_MONGO_CONNECTIONS_TOTALCREATED = "mongo.connections.max";
    
    /**
     * 创建的最大连接数
     */
    public static final String NMS_MONGO_CONNECTIONS_AVAILABLE = "mongo.connections.available";

    /**
     * 单个数据库-数据库数据容量
     */
    public static final String NMS_MONGO_DB_DATASIZE = "mongo.db.dataSize";

    /**
     * 单个数据库-占用存储容量
     */
    public static final String NMS_MONGO_DB_STORAGESIZE = "mongo.db.storageSize";
    
    /**
     * 常规断言数
     */
    public static final String NMS_MONGO_ASSERTS_REGULAR = "mongo.asserts.regular";
    
    /**
     * 告警断言数
     */
    public static final String NMS_MONGO_ASSERTS_WARNING = "mongo.asserts.warning";
    
    /**
     * 消息断言数
     */
    public static final String NMS_MONGO_ASSERTS_MSG = "mongo.asserts.msg";
    
    /**
     * 用户断言数
     */
    public static final String NMS_MONGO_ASSERTS_USER = "mongo.asserts.user";
    
    /**
     * 物理内存消耗
     */
    public static final String NMS_MONGO_MEM_RESIDENT = "mongo.mem.resident";
    
    /**
     * 虚拟内存消耗
     */
    public static final String NMS_MONGO_MEM_VIRTUAL = "mongo.mem.virtual";
    
    /**
     * 映射内存消耗
     */
    public static final String NMS_MONGO_MEM_MAPPED = "mongo.mem.mapped";
    
    /**
     * 数据总行数
     */
    public static final String NMS_MONGO_DB_OBJECTS = "mongo.db.objects";
    
    /**
     * 索引占用磁盘大小
     */
    public static final String NMS_MONGO_DB_INDEXSIZE = "mongo.db.indexSize";
    
    /**
     * 客户端中读锁请求数
     */
    public static final String NMS_MONGO_GLOBALLOCK_ACTIVECLIENTS_READERS = "mongo.globalLock.activeClients.readers";
    
    /**
     * 客户端中写锁请求数
     */
    public static final String NMS_MONGO_GLOBALLOCK_ACTIVECLIENTS_WRITERS = "mongo.globalLock.activeClients.writers";
    
    /**
     * 队列中读锁请求数/s
     */
    public static final String NMS_MONGO_GLOBALLOCK_CURRENTQUEUE_READERS = "mongo.globalLock.currentQueue.readers";
    
    /**
     * 队列中写锁请求数/s
     */
    public static final String NMS_MONGO_GLOBALLOCK_CURRENTQUEUE_WRITERS = "mongo.globalLock.currentQueue.writers";
    
    /**
     * 错误页数
     */
    public static final String NMS_MONGO_EXTRAINFO_PAGEFAULTS = "mongo.extraInfo.pageFaults";
    
    /**
     * 连接数（实时）
     */
    public static final String NMS_POSTGRESQL_NUMBACKENDS = "pgdb.numbackends";
    
    /**
     * 提交的事务数量
     */
    public static final String NMS_POSTGRESQL_XACT_COMMIT = "pgdb.xact.commit";
    
    /**
     * 回滚的事务数量
     */
    public static final String NMS_POSTGRESQL_XACT_ROLLBACK = "pgdb.xact.rollback";
    
    /**
     * 数据库查询返回的行数
     */
    public static final String NMS_POSTGRESQL_TUP_RETURNED = "pgdb.tup.returned";
    
    /**
     * 查询获取的行数
     */
    public static final String NMS_POSTGRESQL_TUP_FETCHED = "pgdb.tup.fetched";
    
    /**
     * 插入行数
     */
    public static final String NMS_POSTGRESQL_TUP_INSERTED = "pgdb.tup.inserted";
    
    /**
     * 更新行数
     */
    public static final String NMS_POSTGRESQL_TUP_UPDATED = "pgdb.tup.updated";
    
    /**
     * 删除行数
     */
    public static final String NMS_POSTGRESQL_TUP_DELETED = "pgdb.tup.deleted";
    
    /**
     * 传入信息速率
     */
    public static final String NMS_KAFKA_MESSAGES_IN = "kafka.messages_in";
    
    /**
     * 传入字节速率
     */
    public static final String NMS_KAFKA_NET_BYTES_IN = "kafka.net.bytes_in";
    
    /**
     * 传出字节速率
     */
    public static final String NMS_KAFKA_NET_BYTES_OUT = "kafka.net.bytes_out";
    
    /**
     * 消费失败率
     */
    public static final String NMS_KAFKA_REQUEST_FETCH_FAILED = "kafka.request.fetch.failed_per_second";
    
    /**
     * 生产失败率
     */
    public static final String NMS_KAFKA_REQUEST_PRODUCE_FAILED = "kafka.request.produce.failed_per_second";
}

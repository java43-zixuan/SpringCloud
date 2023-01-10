一、redis 伪集群搭建
Redis官网：https://redis.io/download/

1、在linux指定目录下创建redis文件夹，上传下载好的安装包或者直接在linux 使用wget命令下载。

2、解压 tar -zxvf

3、在redis目录下创建三个文件夹，以便创建三个redis服务节点
mkdir 7001 7002 7003

4、从bin目录拷贝redis-cli、redis.conf、redis-server 到创建的（7001、7002、7003）目录下,以便启动redis

5、修改redis.conf文件

port 7001
#设置后台启动 默认是no
daemonize  yes
# 关闭AOF
appendonly no
# 开启主从关系
replicaof 127.0.0.1 7001

6、启动redis
cd 7001
./redis-server redis.conf 

7、查看主从关系（启动客户端）
#客户端启动需要指定端口
./redis-cli -p 7002
#查询redis状态
info replication

8、搭建哨兵集群
#复制启动文件
cp redis-sentinel 7001
#复制配置文件
cp sentinel.conf 7001
#修改配置文件
vim sentinel.conf

#指定哨兵端口
port 27001
#指定主节点信息（mymaster 指定主节点名称，119.3.221.244 7001 指定主节点ip端口）
sentinel monitor mymaster 119.3.221.244 7001 2		
#指定时间无法访问，被认为主观下线
sentinel down-after-milliseconds mymaster 5000	
#等待所有副本重新配置为新主副本的最长时间。
sentinel failover-timeout mymaster 60000
protected-mode no
daemonize yes

9、启动哨兵节点
./redis-sentinel sentinel.conf

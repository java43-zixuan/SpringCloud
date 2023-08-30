## docker-compose 搭建 redis-cluster集群

创建redis-cluster文件夹

进入文件夹redis-cluster
1、创建 三主:redis-master1、redis-master2、redis-master3 三从:redis-slave1、redis-slave2、redis-slave3 cluster文件夹
2、创建 docker-compose.yml 文件，内容如下

version: "3.0"
services:
  redis-master1:
    image: redis:6.2.1
    container_name: redis-master1
    ports:
    - 6379:6379
    - 16379:16379   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
    - $PWD/redis-master1/redis.conf:/redis.conf
    - $PWD/redis-master1/data:/data
    command: redis-server /redis.conf

  redis-master2:
    image: redis:6.2.1
    container_name: redis-master2
    ports:
      - 6380:6380
      - 16380:16380   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
      - $PWD/redis-master2/redis.conf:/redis.conf
      - $PWD/redis-master2/data:/data
    command: redis-server /redis.conf

  redis-master3:
    image: redis:6.2.1
    container_name: redis-master3
    ports:
      - 6381:6381
      - 16381:16381   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
      - $PWD/redis-master3/redis.conf:/redis.conf
      - $PWD/redis-master3/data:/data
    command: redis-server /redis.conf

  redis-slave1:
    image: redis:6.2.1
    container_name: redis-slave1
    ports:
      - 6382:6382
      - 16382:16382   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
      - $PWD/redis-slave1/redis.conf:/redis.conf
      - $PWD/redis-slave1/data:/data
    command: redis-server /redis.conf

  redis-slave2:
    image: redis:6.2.1
    container_name: redis-slave2
    ports:
      - 6383:6383
      - 16383:16383   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
      - $PWD/redis-slave2/redis.conf:/redis.conf
      - $PWD/redis-slave2/data:/data
    command: redis-server /redis.conf

  redis-slave3:
    image: redis:6.2.1
    container_name: redis-slave3
    ports:
      - 6384:6384
      - 16384:16384   #集群总线端口 默认就是redis端口加1000，每个节点都要打开
    volumes:
      - $PWD/redis-slave3/redis.conf:/redis.conf
      - $PWD/redis-slave3/data:/data
    command: redis-server /redis.conf

3、为三主三从文件夹创建redis.conf文件,端口分别为6379、6380、6381、6382、6383、6384,内容如下:

#节点端口(要改)
port 6379
#集群节点映射端口(要改)
cluster-announce-port 6379
#集群节点总线端口(要改)
cluster-announce-bus-port 16379
#集群节点 IP(要改成自己服务器ip)
cluster-announce-ip 119.3.221.244
#添加访问认证密码
#requirepass 1234
#如果主节点开启了访问认证，从节点访问主节点需要认证
#masterauth 1234
#保护模式，默认值 yes，即开启。开启保护模式以后，需配置 bind ip 或者设置访问密码；关闭保护模式，外部网络可以直接访问
protected-mode no
#是否以守护线程的方式启动
daemonize no
#是否开启AOF持久化模式，默认 no
appendonly yes
#是否开启集群模式，默认no
cluster-enabled yes
#集群节点信息文件
cluster-config-file nodes.conf
#群节点连接超时时间
cluster-node-timeout 5000
#redis生成的日志(docker容器内的目录,不是宿主机的目录)
logfile "/data/redis.log"







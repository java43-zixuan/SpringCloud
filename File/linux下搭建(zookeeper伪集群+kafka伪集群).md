一、zookeeper 伪集群搭建
Zookeeper官网：https://zookeeper.apache.org/

1、在linux指定目录下创建zookeeper文件夹，上传下载好的安装包或者直接在linux 使用wget命令下载。
wget https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz

2、解压 tar -zxvf

3、复制解压后的zookeeper目录，复制出三个zookeeper1、zookeeper2、zookeeper3文件夹。
cp -r apache-zookeeper-3.8.0-bin zookeeper1

4、修改zoo.cfg
每个文件夹下的conf文件夹里都有一个zoo_sample.cfg文件，直接mv或者cp一份文件出来，名字改为zoo.cfg
修改zoo.cfg文件，内容如下：

#存放数据文件夹(修改对应的zk1 zk2 zk3 zk4)
dataDir=/data/zookeeper/data/zk1
#添加日志存放文件夹(修改对应的zk1 zk2 zk3 zk4)
dataLogDir=/data/zookeeper/data/zk1/dataLog
# 修改对应的端⼝ 3181 3182 3183
clientPort=3181
# 2001为集群通信端⼝，3001为集群选举端⼝，observer表示不参与集群选举,ip换自己linuxip
server.1=localhost:2001:3001
server.2=localhost:2002:3002
server.3=localhost:2003:3003


二、通过docker 搭建redis集群
可以参考：https://www.cnblogs.com/mxnote/articles/17195444.html

创建redis-1~redis-6 六个文件夹，同时在没个文件夹创建redis.conf文件
然后通过docker-compose up -d 执行docker-compose.yml文件


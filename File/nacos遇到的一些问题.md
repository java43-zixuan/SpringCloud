## Nacos

### Linux下直接安装单机nacos
`https://blog.csdn.net/qq_46112274/article/details/123117926`

### Docker部署nacos
首先安装docker,然后安装docker-compose,需要安装mysql数据库，然后创建nacos数据库
以下是docker-compose.yml 以上准备结束后直接创建nacos目录，在目录下创建docker-compose.yml
```
version: '3'
services:
  nacos:
    image: nacos/nacos-server:2.0.3
    container_name: nacos
    restart: always
    ports:
      - "8848:8848"
      - "9848:9848"
    environment:
      - MODE=standalone
      - SPRING_DATASOURCE_PLATFORM=mysql
      - MYSQL_SERVICE_HOST=119.3.221.244
      - MYSQL_SERVICE_DB_NAME=nacos
      - MYSQL_SERVICE_PORT=3306
      - MYSQL_SERVICE_USER=root
      - MYSQL_SERVICE_PASSWORD=Zx496267.
    volumes:
      - ${PWD}/nacos/logs/:/home/nacos/logs
      - ${PWD}/nacos/plugins/:/home/nacos/plugins
```
执行docker-compose up -d

## 需要注意的是：

1. docker安装的nacos 版本2.0.3 不支持encrypted_data_key 字段  也就是数据库config_info、his_config_info 表的encrypted_data_key 字段设置为非必填
2. docker版本需要对应，spring-boot.version:2.3.12.RELEASE 对应nacos:2.0.3 
3. pom.xml 文件使用如下配置，否则会导致程序启动不加载bootstrap.yml
```
  <dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
     <version>2.0.3.RELEASE</version>
  </dependency>
```
4. docker部署nacos需要同时开启8848、9848两个端口，否则读取外部配置报错。



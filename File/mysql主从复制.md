参考文献：
https://blog.csdn.net/qq_44212783/article/details/126492559
https://cloud.tencent.com/developer/article/2009379

## 1.准备两台机器，安装配置好mysql

## 2.在master上开启二进制日志
修改配置文件：`vim /etc/my.cnf`
log_bin
server_id = 1
修改配置之后，重启服务:`service mysqld restart`

## 3.统一2台服务器的基础数据
在master上的操作
导出master的数据库：mysqldump -uroot -p'Zx496267.' --all-databases >all_db.sql
上传给slave：scp all_db.sql root@114.116.68.189:/root  或者其他方式上传文件
在slave上的操作
导入到slave的数据库中：mysql -uroot -p'Zx496267.' <all_db.sql

## 4.清除所有的二进制日志
在master上的操作 打开数据库   mysql -uroot -pZx496267.
清除二进制日志：reset master;
查看使用的二进制日志：show master status;

## 5.在master上新建一个授权用户，给slave来复制二进制日志(密码用的Ly496267.) 这块由于数据库密码要求8位并且区分大小写等，可能会报Error:1819的错误。
GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO slave@'192.168.%.%'IDENTIFIED BY'Ly496267.';
创建并授权了slave的复制账号后再执行show master status; 会发现服务器状态Position 变了。


## 6.在从slave mysql上修改主配置文件
vim /etc/my.cnf 指定唯一的servr ID，设置只读权限
server-id=2                    #配置server-id，让从服务器有唯一ID号
relay_log = mysql-relay-log    #打开Mysql日志，日志格式为二进制
read_only =1                   #设置只读权限
log_bin = mysql-bin            #开启从服务器二进制日志，（非必须）
log_slave_updates =1           #使得更新的数据写进二进制日志中
修改配置之后，重启服务:service mysqld restart


## 7.在slave上配置master.info的信息
CHANGE MASTER TO MASTER_HOST='119.3.221.244',
MASTER_USER='slave',
MASTER_PASSWORD='Ly496267.',
MASTER_PORT=3306,
MASTER_LOG_FILE='mysql-bin.000001',
MASTER_LOG_POS=154;

MASTER_HOST : MySQL主机地址
MASTER_USER : 备份账户用户名
MASTER_PORT : 备份账户密码
MASTER_LOG_FILE : bin-log的文件名
MASTER_LOG_POS : bin-log的位置（数字型）
bin-log的文件名和位置可以通过主机执行"show master status"指令查看。
注:如果master.info配置出现问题，需要stop slave ;然后修改配置语句，重新执行CHANGE MASTER;

## 8. start slave;   # 启动复制线程，就是打开I/O线程和SQL线程；实现拉主的bin-log到从的relay-log上；再从relay-log写到数据库内存里

## 9. SHOW SLAVE STATUS\G;  #查看从服务器状态

Mysql主从同步时Slave_IO_Running：Connecting ; Slave_SQL_Running：Yes的情况故障排除
https://blog.csdn.net/mbytes/article/details/86711508


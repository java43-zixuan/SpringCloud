##PostgreSQL

#### 进入postgre
`psql -d root`

### 最大连接数
`show max_connections;`

### 当前连接数
`SELECT count(*) FROM pg_stat_activity;`

### 剩余连接数
`select max_conn-now_conn as resi_conn from (select setting::int8 as max_conn,(select count(*) from pg_stat_activity) as now_conn from pg_settings where name = 'max_connections') t;`

### 设置最大连接数为500
`ALTER SYSTEM SET max_connections TO '500';`

### 获取数据库连接信息
`select datname,pid,application_name,state from pg_stat_activity;`

### 删除自己的数据库连接
`SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE datname='数据库名' AND pid<>pg_backend_pid();`

### 创建序列
```
CREATE SEQUENCE test_c_id_seq
START WITH 1   //从1开始
INCREMENT BY 1   //每次递增1
NO MINVALUE      //无最小值
NO MAXVALUE      //无最大值
CACHE 1;         //设置缓存中序列的个数，建议设置
```

### 设置序列跟自增id
`select setval('表名_id_seq', max(id)) from 表名;`

### 创建超表
`select create_hypertable('表名', 'created_time（时间字段）');`

####如果出现数据库实时插件安装失败,查询版本号（https://www.likecs.com/ask-4034967.html）
`SELECT default_version, installed_version FROM pg_available_extensions where name = 'timescaledb';`

####执行如下语句安装插件（https://blog.csdn.net/youyicc/article/details/124737667）
`CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;`

### 替换字符
```
使用函数REPLACE(s,s1,s2)，将字符串s中的 s1替换成s2，可以实现批量的列操作，同时可以保留原始数据，非常适合我遇到的场景。
update 表 set 列 = replace(列, 待替换的字符串, 替换为的字符串) where 列 like '%待替换的字符串%';;
update table_name set column_name = replace(column_name, str_1 , str_2) where column_name like '%str_1%';
```
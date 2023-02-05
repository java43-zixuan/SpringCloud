package com.example.userservice2.test20;

import java.util.UUID;

/**
 * 数据库自增ID跟UUID的对比
 */
public class test {
    public static void main(String[] args) {
        /**
         * 主键ID  手动自增   自动自增
         * 优点：
         * 1、自动
         * 2、连续
         * 3、空间小（int）
         * 4、查找/比较 性能快
         * 缺点：
         * 1、高并发（冲突）
         * 2、合并表格（冲突）
         * 3、跳号（删除操作会跳号）
         */

        /**
         * UUID（通用唯一标识36位）
         * 优点：
         * 1、不会出现并发冲突
         * 2、合并表格不会出现冲突
         * 3、所谓的跳号不存在
         * 缺点：
         * 1、不连续  存储性能慢（varchar类型）
         * 2、需要主动插入（业务层）
         * 3、查找/比较 性能慢
         */
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);


    }
}

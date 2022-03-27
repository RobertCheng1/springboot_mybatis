https://blog.csdn.net/q736317048/article/details/110284582
https://baomidou.com/pages/226c21/#%E5%BC%80%E5%A7%8B%E4%BD%BF%E7%94%A8, 提到的完整代码

CREATE TABLE teacher
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);


INSERT INTO teacher (id, name, age, email) VALUES
(1, 'Jone', 38, 'test1@baomidou.com'),
(2, 'Jack', 39, 'test2@baomidou.com'),
(3, 'Tom', 40, 'test3@baomidou.com'),
(4, 'Sandy', 41, 'test4@baomidou.com'),
(5, 'Billie', 40, 'test5@baomidou.com');

CREATE TABLE employee (
 id BIGINT(20) PRIMARY KEY NOT NULL COMMENT '主键',
 name VARCHAR(30) DEFAULT NULL COMMENT '姓名',
 age int(11) DEFAULT NULL COMMENT '年龄',
 email VARCHAR(30) DEFAULT NULL COMMENT '邮箱',
 manager_id BIGINT(20) DEFAULT NULL COMMENT '直属上级id',
 create_time DATETIME DEFAULT NULL COMMENT '创建时间',
 CONSTRAINT manager_fk FOREIGN KEY (manager_id) REFERENCES employee(id)
) ENGINE=INNODB CHARSET=UTF8;


插入时：
    1.新建的实体对象没有设置 email 的值（除了id），则在 ORM 转 SQL 时没有出现 email 列，这是因为 MP 的默认规则不会转实体对象中实例变量为 NULL 的字段 from:2-1
    2.新建的实体对象没有设置 id 的值，但是生产的SQL中有id，这是 MP 默认填充的，基于雪花算法生成的。
      如果由于历史原因表的主键不是 id 而是 uesr_id（当然其和实体类中对应的字段就叫 userId）， 这时在插入时（前提还是没有设置 id 值）会报Field 'user_id' doesn't have a default value
      因为 MP 默认：实体中的主键列名是id 即只为 id 字段基于雪花算法生成默认值，这时可以用注解 @TableId 来表明 userId 就是主键列
      对比该例子和 email 的区别：
      为什么 email 例子中不报没有默认值，因为建表语句中 emai 是可为空的字段

    3.实体类中属性的驼峰和数据库中列名下划线的对应. 比如实体类的属性 managerId 对应数据库表 manager_id
      如果由于历史原因不符合默认映射规则，可用 @TableField  来适配
    4.实体类的大小驼峰 User 默认和数据库表名 user 对应；
      如果由于历史原因不符合默认映射规则，可以用注解 @TableName 来适配

    5.有些实体类中某些字段和数据库表中的列都不对应，怎么忽略？如果不处理：会报 unkonwn cloumn 'xxx' in the 'field list'

    6.忽略实体类中的字段即成员变量不参与序列化：a.transient	b.static	c.@TableField(exit=false)




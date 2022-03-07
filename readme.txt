https://blog.csdn.net/q736317048/article/details/110284582

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








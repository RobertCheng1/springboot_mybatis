package com.example.springboot_mybatis;

import com.example.springboot_mybatis.domain.stream.source.KafkaSender;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.springboot_mybatis.dao")
@EnableBinding({KafkaSender.class})
public class SpringbootMybatisApplication {
	// ToDo:
	// 1. 实体类名和表名不对应时：@TableName("db_name")
	// 2. 主键：有时虽然实体类的字段和表的列名符合默认规则，但是名字都不叫Id,
	//         而在插入数据时 MybatisPlus 默认只会给名字为Id的列赋默认值，此时就需要用 @TableId 修饰该字段
	// 3. 实体类中的字段和表的列不符合默认对应规则：@TableField("column_name")
	// 4. 忽略实体类中的字段：a.transient	b.static	c.@TableField(exit=false)
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

}

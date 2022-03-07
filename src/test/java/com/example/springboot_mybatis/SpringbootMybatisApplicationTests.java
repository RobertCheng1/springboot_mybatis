package com.example.springboot_mybatis;

import com.example.springboot_mybatis.dao.StudentMapper;
import com.example.springboot_mybatis.entity.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootMybatisApplicationTests {

	@Autowired
	StudentMapper studentMapper;

	@Test
	void contextLoads() {
		List<Student> stu = studentMapper.selectList(null);
		Assert.assertEquals(4, stu.size());
		System.out.println(stu);
	}

}

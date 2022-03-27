package com.example.springboot_mybatis;

import com.example.springboot_mybatis.dao.EmployeeMapper;
import com.example.springboot_mybatis.entity.Employee;
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
	EmployeeMapper employeeMapper;

	@Test
	void contextLoads() {
		List<Employee> stu = employeeMapper.selectList(null);
		Assert.assertEquals(6, stu.size());
		System.out.println(stu);

		String aa = "hello";
		String bb = "wor'or ' ld";
		System.out.printf(aa + bb);
	}


}

package com.example.springboot_mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot_mybatis.dao.DataShare;
import com.example.springboot_mybatis.dao.EmployeeMapper;
import com.example.springboot_mybatis.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.management.QueryEval;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {
    // 依赖注入的方式：
    // Case1: @Autowired 有告警：Field injection is not recommended
    // 参考： https://blog.csdn.net/zhangjingao/article/details/81094529
    // @Autowired
    // private StudentMapper studentMapper;
    //
    // Case2: 构造方法，这个不用写 @Autowired？难道Spring框架自己做的。如果属性很多的话肯定，这么写肯定不合理
    // private StudentMapper studentMapper;
    // public HelloController(StudentMapper studentMapper){
    //     System.out.println("aaaaaaaaaaaaa");
    //     System.out.println(studentMapper);
    //     this.studentMapper = studentMapper;
    // }
    // Case3: 根据 case2 的实现 和 stardb_cluster 中 domain-service-impl-MyDbServiceImpl 的启发：
    // 猜测 final 修饰符 + 注解 @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    // 参考：https://blog.csdn.net/qq_37256896/article/details/115869717
    private final EmployeeMapper employeeMapper;

    private final DataShare ds;

    @GetMapping("/datashare")
    public String datashare() {
        ds.getTestList().add("name");
        System.out.println(ds.getTestList());
        return "This is from data share Mybatis;";
    }


    @GetMapping("/hello")
    public String hello() {
        List<Employee> StuList = employeeMapper.selectList(null);
        System.out.println(StuList);
        System.out.printf("MockGlobal.country = %s\n", MockGlobal.country);
        return "This is from Springboot Mybatis;";
    }

    @GetMapping("/helloAdv")
    public String helloAdv() {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>();
        // lambdaQueryWrapper.ge(Employee::getAge, 10);
        // 如果 lambdaQueryWrapper 没有初始化， 和 NULL 是一个效果
        List<Employee> StuList = employeeMapper.selectList(lambdaQueryWrapper);
        System.out.println(StuList);
        return "This is from helloAdv of Springboot Mybatis;";
    }

    @GetMapping("/initemployee")
    public String initEmployee() {
        Employee bigBoss = new Employee();
        bigBoss.setName("Stern");
        bigBoss.setAge(50);
        bigBoss.setEmail("stern@nba.com");
        bigBoss.setCreateTime(LocalDateTime.now());
        int rows = employeeMapper.insert(bigBoss);
        System.out.printf("row = %d\n", rows);
        System.out.printf("employee = %d\n", bigBoss.getId());

        Employee krause = new Employee();
        krause.setName("Krause");
        krause.setAge(40);
        krause.setEmail("krause@nba.com");
        krause.setManagerId(bigBoss.getId());
        krause.setCreateTime(LocalDateTime.now());
        employeeMapper.insert(krause);

        Employee jordan = new Employee();
        jordan.setName("jordan");
        jordan.setAge(30);
        jordan.setEmail("jordan@nba.com");
        jordan.setManagerId(krause.getId());
        jordan.setCreateTime(LocalDateTime.now());
        employeeMapper.insert(jordan);

        Employee pippen = new Employee();
        pippen.setName("pippen");
        pippen.setAge(28);
        pippen.setEmail("pipeng@nba.com");
        pippen.setManagerId(krause.getId());
        pippen.setCreateTime(LocalDateTime.now());
        employeeMapper.insert(pippen);

        Employee rodman = new Employee();
        rodman.setName("rodman");
        rodman.setAge(28);
        // rodman.setEmail("luodeman@nba.com");
        rodman.setManagerId(krause.getId());
        rodman.setCreateTime(LocalDateTime.now());
        employeeMapper.insert(rodman);

        Employee kerr = new Employee();
        kerr.setName("kerr");
        kerr.setAge(27);
        kerr.setEmail("kerr@nba.com");
        kerr.setManagerId(krause.getId());
        kerr.setCreateTime(LocalDateTime.now());
        employeeMapper.insert(kerr);
        return "This is from initEmployee Mybatis;";
    }

    @GetMapping("/retrieve")
    public String retrieve() {
        // 基本查询：
        // 1. selectById
        Long id = 1507975088936067073L;
        Employee employee = employeeMapper.selectById(id);
        System.out.println(employee);
        // 2. selectBatchIds
        List<Long> idsList = Arrays.asList(1507975088936067073L, 1507975088998981634L);
        List<Employee> employeeList = employeeMapper.selectBatchIds(idsList);
        employeeList.forEach(System.out::println);
        // 3. selectByMap: where name = "jordan" and age = 30
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "jordan");
        columnMap.put("age", 30);
        employeeList = employeeMapper.selectByMap(columnMap);
        employeeList.forEach(System.out::println);
        return "This is from retrieve Mybatis;";
    }

    @GetMapping("/retrievebywrapper")
    public String retrieveByWrapper() {
        // 条件构造器查询 即 使用 wrapper 作为参数
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        // QueryWrapper<Employee> queryWrapper = Wrappers.<Employee>query(); //两种方法都可以
        //
        // 1.  WHERE name LIKE '%an' AND age < 40
        // queryWrapper.like("name", "an").le("age", 40);
        //
        // 2. WHERE name LIKE '%an' AND age BETWEEN 20 AND 40 AND email IS NOT NULL
        // queryWrapper.like("name", "an").between("age", 20, 40).isNotNull("email");
        //
        // 3.WHERE (name LIKE '%an' OR age >= 28) ORDER BY age DESC,id ASC
        // queryWrapper.like("name", "an").or().ge("age",28).orderByDesc("age").orderByAsc("id");
        //
        // 4. WHERE 中用到“数据库函数” 和 子查询: 创建日期为 2022年03月27日并且直属上级的名字中含有 Krause
        // WHERE (date_format(create_time, '%Y-%m-%d')=? AND manager_id IN (select id from employee where name like '%Krause%'))
        // Case 4-1:
        // queryWrapper.apply("date_format(create_time, '%Y-%m-%d')={0}", "2022-03-27")
        //         .inSql("manager_id", "select id from employee where name like '%Krause%'");
        // Case 4-2: 直接把前端输入拼接到 SQL, 有 SQL 注入风险
        // queryWrapper.apply("date_format(create_time, '%Y-%m-%d')='2022-03-27'")
        //         .inSql("manager_id", "select id from employee where name like '%Krause%'");
        // SQL 注入风险：比如前端输入 2022-03-27' or true or '
        // queryWrapper.apply("date_format(create_time, '%Y-%m-%d')='2022-03-27' or true or ''")
        //         .inSql("manager_id", "select id from employee where name like '%Krause%'");
        // queryWrapper.apply("date_format(create_time, '%Y-%m-%d')={0}", "2022-03-27' or true or '")
        //         .inSql("manager_id", "select id from employee where name like '%Krause%'");
        //
        // 5. 名字中含有 an 并且（年龄小于40或邮箱不为空）:
        // WHERE (name LIKE '%an%' AND (age < 40 OR email IS NOT NULL))
        // queryWrapper.like("name","an").and(qw-> qw.lt("age",40).or().isNotNull("email"));
        //
        // 6. 名字中含有 an 或者（年龄大于20且小于40且邮箱不为空）:
        // WHERE (name LIKE '%an%' OR (age < ? AND age > ? AND email IS NOT NULL))
        // queryWrapper.like("name","an").or(qw->qw.lt("age",40).gt("age", 20).isNotNull("email"));
        //
        // 7.（年龄小于40或邮箱不为空）并且 名字中含有 an:
        // WHERE ((age < 40 AND email IS NOT NULL) AND name LIKE '%an%')
        // queryWrapper.nested(qw->qw.lt("age",40).isNotNull("email")).like("name","an");
        //
        // 8. 年龄为 28， 29:
        // WHERE (age IN (28,29))
        // queryWrapper.in("age", Arrays.asList(28, 29));
        //
        // 9. 只返回一条数据 limit 1
        // WHERE (age IN (28,29)) LIMIT 1
        // queryWrapper.in("age", Arrays.asList(28, 29)).last("LIMIT 1");
        //
        // 10. SELECT 返回指定列 1：
        // SELECT id,name FROM employee WHERE name LIKE '%an' AND age < 40
        // queryWrapper.select("id", "name").like("name", "an").le("age", 40);
        //
        // 11. SELECT 返回指定列 2:
        // 反选法 SELECT id,name,age,email FROM employee WHERE name LIKE '%an' AND age < 40
        // queryWrapper.select(Employee.class, info-> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"))
        //         .like("name", "an").le("age", 40);
        //
        // 12. 条件构造器中condition作用：后面的条件是否加入 WHERE 条件中,
        // 使用场景是根据用户输入内容（name， email）进行过滤，但是name / email 可以都填也可填写任意一项
        // SELECT id,name,age,email,manager_id,create_time FROM employee WHERE (email LIKE '%an%')
        String name = "";
        String email = "nba";
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);

        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapper Mybatis;";
    }

    @GetMapping("/retrievebywrapperentity")
    public String retrieveByWrapperEntity() {
        // 13. 实体作为条件构造器改造方法的参数：
        // 默认 Entity 中不为 NULL 的属性，将作为 where 条件，默认使用“等值”比较符
        // 使用场景是用实体接受前端页面传过来的参数
        Employee whereEmployee = new Employee();
        whereEmployee.setName("jordan");
        whereEmployee.setAge(30);

        // 条件构造器查询:实体作为构造器参数: WHERE name='jordan' AND age=30
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>(whereEmployee);
        // Case 13-2: 实体作为构造器参数 组合 like / le 等操作： WHERE name=? AND age=? AND (age <= ?)
        // queryWrapper.le("age", 20);
        //
        // Case 13-3: 但是前端输入一般都是模糊输入 而 后端是 “等值” 比较， 怎么办？
        // 在实体类中用给 name 列加入注解： @TableField(condition = SqlCondition.LIKE) 即可:
        // SQL 就变成了 WHERE name LIKE CONCAT('%','jordan AND age=30
        // 以此类推给 age 加入注解： @TableField(condition = "%s&lt;#{%s}") 来表达小于的效果
        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapperEntity Mybatis;";
    }

    @GetMapping("/retrievebywrapperalleq")
    public String retrieveByWrapperAllEq() {
        // 14. 条件构造器 allEq 用法：
        // 和 3 中的 employeeMapper.selectByMap 类似
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "jordan");
        columnMap.put("age", 30);
        // Case 14-1:
        queryWrapper.allEq(columnMap);
        // Case 14-2: allEq 默认把 columnMap 中是 NULL 的字段也带到 where 中，如果想忽略 NULL，可以加个 false 参数
        // queryWrapper.allEq(columnMap,false);
        // Case 14-3: allEq 去掉 columnMap 中某些字段：
        // queryWrapper.allEq((k,v)-> !k.equals("name"), columnMap);

        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapperAllEq Mybatis;";
    }


    @GetMapping("/retrievebywrapperselectmap")
    public String retrieveByWrapperSelectMap() {
        // 15. 其他使用条件构造器的方法： 和 case10， 11 有点类似了
        // 应用场景是：有些时候只需要关注某些字段，如果返回实体类，好多字段都是空，不太优雅
        //           返回的是统计数据
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        // Case15-1:
        // queryWrapper.select("id", "name").like("name","an").lt("age", 40);
        // Case15-2: 返回统计数据
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}", 500);
        List<Map<String, Object>> employeeList = employeeMapper.selectMaps(queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapperSelectMap Mybatis;";
    }

    @GetMapping("/retrievebywrapperseleccount")
    public String retrieveByWrapperSelectCount() {
        // 16. 返回总数：
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        queryWrapper.like("name","an").lt("age", 40);
        Long count = employeeMapper.selectCount(queryWrapper);
        System.out.printf("count = %d\n", count);
        return "This is from retrieveByWrapperSelectCount Mybatis;";
    }
    @GetMapping("/retrievebywrapperselecone")
    public String retrieveByWrapperSelectOne() {
        // 17. 只返回0条或一条数据，如果是符合条件的数据是多条则报错
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        queryWrapper.like("name","jordan").lt("age", 40);
        Employee employee = employeeMapper.selectOne(queryWrapper);
        System.out.println(employee);
        return "This is from retrieveByWrapperSelectOne Mybatis;";
    }


    @GetMapping("/retrievebywrapperselectlambda")
    public String retrieveByWrapperSelectLambda() {
        // 18. lambda 条件构造器：三种方法都可以， 它的好处是防误写字段
        // LambdaQueryWrapper<Employee> lambda = new QueryWrapper<Employee>().lambda();
        // LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>();
        LambdaQueryWrapper<Employee> lambdaQuery = Wrappers.<Employee>lambdaQuery();
        // Case 18-1: 类::方法 这种写法称作：方法引用
        // lambdaQuery.like(Employee::getName, "jordan").lt(Employee::getAge, 40);
        // Case 18-2: 名字中含有 an 并且（年龄小于40或邮箱不为空）:
        lambdaQuery.like(Employee::getName, "an")
                .and(lqw->lqw.lt(Employee::getAge, 40).or().isNotNull(Employee::getEmail));
        List<Employee> employeeList = employeeMapper.selectList(lambdaQuery);
        employeeList.forEach(System.out::println);

        // Case 18-3: LambdaQueryChainWrapper  v3.0.7 新增的
        List<Employee> employeeList2 = new LambdaQueryChainWrapper<Employee>(employeeMapper)
                .like(Employee::getName, "an").lt(Employee::getAge, 41).list();
        employeeList2.forEach(System.out::println);
        return "This is from retrieveByWrapperSelectLambda Mybatis;";
    }

    @GetMapping("/retrievebywrapperselectmy")
    public String retrieveByWrapperSelectMy() {
        // 19. 自定义 SQL
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        queryWrapper.like("name","an").lt("age", 41);
        List<Employee> employeeList = employeeMapper.selectMy(queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapperSelectMy Mybatis;";
    }

    @GetMapping("/retrievebywrapperselectmypage")
    public String retrieveByWrapperSelectMyPage() {
        // 19. 分页查询: 需要借助分页插件 MybatisPlusInterceptor；用第三方分页插件也可以 PageHelper
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<Employee>();
        queryWrapper.lt("age", 91);
        // Case19-1: 调用原生的 selectPage,主要是单表分页
        // Page<Employee> page = new Page<Employee>(2,2);
        // IPage<Employee> iPage = employeeMapper.selectPage(page, queryWrapper);
        // System.out.printf("iPage.getTotal() = %d\n", iPage.getTotal());
        // System.out.printf("iPage.getPages() = %d\n", iPage.getPages());
        // System.out.printf("iPage.getSize() = %d\n", iPage.getSize());
        // iPage.getRecords().forEach(System.out::println);

        // Case19-2: 调用自己的分页：可以写任意SQL,所以跨表查询是可以了
        Page<Employee> page = new Page<Employee>(2,4);
        List<Employee> employeeList = employeeMapper.selectMyPage(page, queryWrapper);
        employeeList.forEach(System.out::println);
        return "This is from retrieveByWrapperSelectMyPage Mybatis;";
    }

    @GetMapping("/updateById")
    public String updateById() {
        // 20. 更新方法: updateById
        Employee employee = new Employee();
        employee.setId(1508025451366105089L);
        employee.setAge(51);
        employee.setEmail("stern2022@nba.com");
        int rows = employeeMapper.updateById(employee);
        System.out.printf("rows = %s\n", rows);
        return "This is from updateById Mybatis;";
    }

    @GetMapping("/updateByWrapper")
    public String updateByWrapper() {
        // 21. 更新方法: updateByWrapper
        // Case21-1: 常规做法
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "Stern").eq("age", 51);
        Employee employee = new Employee();
        employee.setAge(52);
        employee.setEmail("stern2021@nba.com");
        int rows = employeeMapper.update(employee, updateWrapper);
        System.out.printf("rows = %s\n", rows);
        // Case21-2:只更新少量字段，不想 new Employee,
        UpdateWrapper<Employee> updateWrapperAdv = new UpdateWrapper<>();
        updateWrapperAdv.eq("name", "Stern").eq("age", 52).set("age", 53);
        int rows2 = employeeMapper.update(null, updateWrapperAdv);
        System.out.printf("rows2 = %s\n", rows2);
        return "This is from updateByWrapper Mybatis;";
    }

    @GetMapping("/updateByLambdaWrapper")
    public String updateByLambdaWrapper() {
        // Case22-1: 参考 18. lambda 条件构造器，它的好处是防误写字段
        LambdaUpdateWrapper<Employee> lambdaUpdate = Wrappers.<Employee>lambdaUpdate();
        lambdaUpdate.eq(Employee::getName, "Stern").eq(Employee::getAge, 53).set(Employee::getAge,54);
        int rows = employeeMapper.update(null, lambdaUpdate);
        System.out.printf("rows = %s\n", rows);

        // Case22-2: LambdaUpdateChainWrapper
        boolean update = new LambdaUpdateChainWrapper<Employee>(employeeMapper)
                        .eq(Employee::getName, "Stern").eq(Employee::getAge, 54).set(Employee::getAge,55).update();
        System.out.printf("update = %s\n", update);
        return "This is from updateByLambdaWrapper Mybatis;";
    }


    @GetMapping("/deleteById")
    public String deleteById() {
        // 23. deleteById
        int rows = employeeMapper.deleteById(1508025451366105089L);
        System.out.printf("rows = %s\n", rows);
        return "This is from deleteById Mybatis;";
    }

    @GetMapping("/deleteByMap")
    public String deleteByMap() {
        // 23. deleteByMap
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "rodman");
        int rows = employeeMapper.deleteByMap(columnMap);
        return "This is from deleteByMap Mybatis;";
    }

    @GetMapping("/deleteBatchIds")
    public String deleteBatchIds() {
        // 23. deleteBatchIds
        int rows = employeeMapper.deleteBatchIds(Arrays.asList(1508025451957501953L, 1508025451705843714L));
        System.out.printf("rows = %s\n", rows);
        return "This is from deleteBatchIds Mybatis;";
    }

    @GetMapping("/deleteByWrapper")
    public String deleteByWrapper() {
        // 23. deleteByWrapper
        LambdaQueryWrapper<Employee> lambdaQuery = Wrappers.<Employee>lambdaQuery();
        lambdaQuery.eq(Employee::getName, "Krause").eq(Employee::getAge, 40);
        int rows = employeeMapper.delete(lambdaQuery);
        System.out.printf("rows = %s\n", rows);
        return "This is from deleteByMap Mybatis;";
    }
}

class MockGlobal{
    // 直接启动服务，下面的这句话也是会被执行的。参考反射中提到的：每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来。
    protected static String country = "china";
}

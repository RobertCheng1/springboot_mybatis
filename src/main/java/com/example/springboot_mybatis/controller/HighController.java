package com.example.springboot_mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot_mybatis.dao.NbaPlayerMapper;
import com.example.springboot_mybatis.entity.Employee;
import com.example.springboot_mybatis.entity.NbaPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/high")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HighController {
    // Mybatis-plus 的高阶用法：https://www.imooc.com/learn/1171
    private final NbaPlayerMapper nbaPlayerMapper;


    // 逻辑删除的场景：
    // 业务表和用户表有主外键关联，删除用户表时，删不掉；
    //      有人会说设置 ondelete set null 设置为 null， 这会让业务数据变成无源数据
    //      有人会说设置 delete_on_cascade 可以级联删除，但是这太危险啦
    // 业务表和用户表没有主外键关联，是通过程序逻辑维护主外键关系的， 删除用户表后，这会让业务数据变成无源数据
    @GetMapping("/")
    public String high() {
        return "This is from high;";
    }


    @GetMapping("/initnbaplayer")
    public String initEmployee() {
        NbaPlayer bigBoss = new NbaPlayer();
        bigBoss.setName("Stern");
        bigBoss.setAge(50);
        bigBoss.setEmail("stern@nba.com");
        bigBoss.setCreateTime(LocalDateTime.now());
        int rows = nbaPlayerMapper.insert(bigBoss);
        System.out.printf("row = %d\n", rows);
        System.out.printf("employee = %d\n", bigBoss.getId());

        NbaPlayer krause = new NbaPlayer();
        krause.setName("Krause");
        krause.setAge(40);
        krause.setEmail("krause@nba.com");
        krause.setManagerId(bigBoss.getId());
        krause.setCreateTime(LocalDateTime.now());
        nbaPlayerMapper.insert(krause);

        NbaPlayer jordan = new NbaPlayer();
        jordan.setName("jordan");
        jordan.setAge(30);
        jordan.setEmail("jordan@nba.com");
        jordan.setManagerId(krause.getId());
        jordan.setCreateTime(LocalDateTime.now());
        nbaPlayerMapper.insert(jordan);

        NbaPlayer pippen = new NbaPlayer();
        pippen.setName("pippen");
        pippen.setAge(28);
        pippen.setEmail("pipeng@nba.com");
        pippen.setManagerId(krause.getId());
        pippen.setCreateTime(LocalDateTime.now());
        nbaPlayerMapper.insert(pippen);

        NbaPlayer rodman = new NbaPlayer();
        rodman.setName("rodman");
        rodman.setAge(28);
        // rodman.setEmail("luodeman@nba.com");
        rodman.setManagerId(krause.getId());
        rodman.setCreateTime(LocalDateTime.now());
        nbaPlayerMapper.insert(rodman);

        NbaPlayer kerr = new NbaPlayer();
        kerr.setName("kerr");
        kerr.setAge(27);
        kerr.setEmail("kerr@nba.com");
        kerr.setManagerId(krause.getId());
        kerr.setCreateTime(LocalDateTime.now());
        nbaPlayerMapper.insert(kerr);
        return "This is from initNbaPlayer Mybatis;";
    }

    @GetMapping("/logicaldelete")
    public String logicalDelete() {
        System.out.println("In the logicalDelete");
        //Case1-1: mybatis-plus 的逻辑删除：把 delete 改为了 update
        int rows =  nbaPlayerMapper.deleteById(1522829183216037889L);
        System.out.printf("rows = %d\n", rows);
        return "This is from logicalDelete Mybatis;";
    }

    @GetMapping("/logicaldelete_select")
    public String logicalDeleteSelect() {
        System.out.println("In the logicalDeleteSelect");
        // Case1-2:
        // 引入逻辑删除后：使用 mp 提供的原生查询和更新方法，会自动在条件中加上 逻辑未删除的 限制即 where deleted = 0; 自定义sql 不加。
        // SELECT id,name,age,email,manager_id,create_time,update_time,version FROM nba_player WHERE deleted=0
        // 另外之所以不输出 deleted 列是因为在 entity NbaPlayer deleted 列上增加注解  @TableField(select = false)
        List<NbaPlayer> nbaPlayerList = nbaPlayerMapper.selectList(null);
        nbaPlayerList.forEach(System.out::println);
        return "This is from logicalDeleteSelect Mybatis;";
    }

    @GetMapping("/logicaldelete_update")
    public String logicalDeleteUpdate() {
        // Case1-3:
        // 引入逻辑删除后：使用 mp 提供的原生查询和更新方法，会自动在条件中加上 逻辑未删除的 限制即 where deleted = 0; 自定义sql 不加。
        // UPDATE nba_player SET age=? WHERE id=? AND deleted=0
        System.out.println("In the logicalDeleteUpdate");
        NbaPlayer nbaPlayer = new NbaPlayer();
        nbaPlayer.setAge(31);
        nbaPlayer.setId(1522829183207649281L);
        int rows = nbaPlayerMapper.updateById(nbaPlayer);
        System.out.printf("rows = %d\n", rows);
        return "This is from logicalDeleteUpdate Mybatis;";
    }



    @GetMapping("/autofill_insert")
    public String autoFillInsert() {
        // Case2-1:mybatis-plus 的自动填充功能:场景比如
        //  创建时间/更新时间：设置未当前时间
        //  创建人/更新人:当前登陆人，从 session 里面获取
        System.out.println("In the autoFillInsert of high controller");
        NbaPlayer nbaPlayer = new NbaPlayer();
        nbaPlayer.setAge(31);
        nbaPlayer.setName("yijianlian");
        int rows = nbaPlayerMapper.insert(nbaPlayer);
        System.out.printf("rows = %d\n", rows);
        return "This is from autoFillInsert Mybatis;";
    }

    @GetMapping("/autofillupdate")
    public String autoFillUpdate() {
        // Case2-1:mybatis-plus 的自动填充功能:
        System.out.println("In the autoFillUpdate of high controller");
        NbaPlayer nbaPlayer = new NbaPlayer();
        nbaPlayer.setAge(31);
        nbaPlayer.setId(1522857815804313601L);
        // updateTime 列上配置了 @TableField(fill = FieldFill.INSERT_UPDATE)
        // 根据 MyMetaObjectHandler 里的实现，如果业务这里设置过 updateTime 的值，则就不会再自动填充了
        nbaPlayer.setUpdateTime(LocalDateTime.now());
        int rows = nbaPlayerMapper.updateById(nbaPlayer);
        System.out.printf("rows = %d\n", rows);
        return "This is from autoFillUpdate Mybatis;";
    }

    @GetMapping("/optimisticlock")
    public String optimisticLock() {
        // Case3-1:mybatis-plus 的乐观锁
        System.out.println("In the optimisticLock of high controller");
        NbaPlayer nbaPlayer = new NbaPlayer();
        nbaPlayer.setAge(31);
        nbaPlayer.setVersion(1);


        UpdateWrapper<NbaPlayer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "jordan");
        int rows = nbaPlayerMapper.update(nbaPlayer, updateWrapper);
        System.out.printf("rows = %d\n", rows);
        return "This is from autoFillUpdate Mybatis;";
    }
}

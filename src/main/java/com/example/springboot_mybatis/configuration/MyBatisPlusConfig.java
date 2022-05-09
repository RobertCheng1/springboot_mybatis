package com.example.springboot_mybatis.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

// SQL 注入器: https://www.imooc.com/video/20101/0
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteBatchByIds;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: MyBatisPlusConfig
 * @Date: 2022/3/29 11:07
 */
@Configuration
public class MyBatisPlusConfig {
    // 由于 mybatis-plus 版本的原因慕课教程中用的类是 PaginationInterceptor, 直接 return new PaginationInterceptor();
    // 在我的这个版本中，用的是 MybatisPlusInterceptor； 直接 return new MybatisPlusInterceptor() 分页没有生效。
    // 后来搜索 “MybatisPlusInterceptor 没有分页”
    // https://www.csdn.net/tags/MtTaMg4sNjc2MTE3LWJsb2cO0O0O.html
    // https://blog.csdn.net/hyk1499449886/article/details/113483133
    // 可知需要调用 addInnerInterceptor 才可以
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 为了测试 mybatis-plus 的多租户，在 paginationInnerInterceptor 增加参数,当然不是必须在分页插件这里，todo
        // https://baomidou.com/pages/aef2f2/#tenantlineinnerinterceptor 有项目 demo
        // https://gitee.com/baomidou/mybatis-plus-samples/tree/master/mybatis-plus-sample-tenant
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor(new TenantLineHandler() {

            // 获取租户 ID 值表达式，只支持单个 ID 值
            // @return 租户 ID 值表达式
            @Override
            public Expression getTenantId() {
                // 在实际项目中，该值需要从请求头中获取，这里为了演示暂时固定一个值，该值是 nba_player 表中的 manager_id 值
                return new LongValue(1522829183186677761L);
            }

            // 获取租户字段名    默认字段名叫: tenant_id
            // @return 租户字段名
            @Override
            public String getTenantIdColumn() {
                return "manager_id";
            }

            // 根据表名判断是否忽略拼接多租户条件   默认都要进行解析并拼接多租户条件
            // @param tableName 表名
            // @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                if (tableName.equals("employee")) {
                    return true;
                } else if (tableName.equals("nba_player")) {
                    return false;
                }
                return false;
            }

        });
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);

        // 动态表名
        // DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        // dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
        //     // 获取参数方法: 为了演示直接固定分表名为 xxx_2022
        //     return tableName + "_2022";
        // });
        // interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);


        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // sql 性能分析: 该版本的 mybatis-plus 把 PerformanceInterceptor 给去掉了
        // https://blog.csdn.net/weixin_43863463/article/details/118191517
        //
        // PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // performanceInterceptor.setFormat(true);
        // performanceInterceptor.setMaxTime(5L);  // 慢查询 sql
        // interceptor.addInnerInterceptor(performanceInterceptor);
        return interceptor;
    }

}

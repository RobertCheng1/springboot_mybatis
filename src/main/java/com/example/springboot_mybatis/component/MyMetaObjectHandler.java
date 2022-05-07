package com.example.springboot_mybatis.component;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // mybatis-plus 的自动填充功能
    @Override
    public void insertFill(MetaObject metaObject) {
        // 只要插入的 entity 中带有 @TableField(fill = FieldFill.INSERT)，在进行插入时就会进入该函数
        // 而 entity 中可能有多个列带有 @TableField(fill = FieldFill.INSERT)，所以进行 if 判断
        System.out.println("---In the insertFill of MyMetaObjectHandler---");
        if (metaObject.hasSetter("createTime")) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter("updateTime")) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("---In the updateFill of MyMetaObjectHandler---");
        Object value = getFieldValByName("updateTime", metaObject);
        // 更新的时候，如果对应的 列 有值，就不再重新赋值
        if (value == null) {
            System.out.println("------updateTime is null of metaObject------");
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }




}

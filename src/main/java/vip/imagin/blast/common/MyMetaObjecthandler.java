package vip.imagin.blast.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import java.util.Date;

/**
 * 公共字段填充（就比如说一些字段例如upadatetime就是公共字段）
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler{


    /**
     * 插入字段的公共填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());

        metaObject.setValue("createtime", new Date(System.currentTimeMillis()));
        metaObject.setValue("updatetime",new Date(System.currentTimeMillis()));
//        metaObject.setValue("createUser", BaseContext.getCurrentId());pi
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    /**
     * 更新字段的填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updatetime",new Date(System.currentTimeMillis()));
    }
}

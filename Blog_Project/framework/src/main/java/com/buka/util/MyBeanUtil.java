package com.buka.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体类转换为返回包装类
 */
public class MyBeanUtil {
    public static <T> T copyBean(Object src,Class<T> tClass){
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(src,t);
            return t;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream().map(o -> copyBean(o,clazz)).collect(Collectors.toList());
    }
}

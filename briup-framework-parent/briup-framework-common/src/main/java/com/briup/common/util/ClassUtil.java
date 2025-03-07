package com.briup.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("all")
public final class ClassUtil {

    public static final Set<Class<?>> BASIC_CLASS = new HashSet<Class<?>>();

    static {
        BASIC_CLASS.add(int.class);
        BASIC_CLASS.add(double.class);
        BASIC_CLASS.add(float.class);
        BASIC_CLASS.add(byte.class);
        BASIC_CLASS.add(short.class);
        BASIC_CLASS.add(char.class);
        BASIC_CLASS.add(long.class);
        BASIC_CLASS.add(boolean.class);
    }

    /**
     * 获取一个类的注解,如果未获取到则获取父类
     *
     * @param clazz      要获取的类
     * @param annotation 注解类型
     * @param <T>        注解类型泛型
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        T ann = clazz.getAnnotation(annotation);
        if (ann != null) {
            return ann;
        } else {
            if (clazz.getSuperclass() != Object.class) {
                //尝试获取父类
                return getAnnotation(clazz.getSuperclass(), annotation);
            }
        }
        return ann;
    }

    /**
     * 获取一个方法的注解,如果未获取则获取父类方法
     *
     * @param method     要获取的方法
     * @param annotation 注解类型
     * @param <T>        注解类型泛型
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
        T ann = method.getAnnotation(annotation);
        if (ann != null) {
            return ann;
        } else {
            Class clazz = method.getDeclaringClass();
            Class superClass = clazz.getSuperclass();
            if (superClass != Object.class) {
                try {
                    //父类方法
                    Method suMethod = superClass.getMethod(method.getName(), method.getParameterTypes());
                    return getAnnotation(suMethod, annotation);
                } catch (NoSuchMethodException e) {
                    return null;
                }
            }
        }
        return ann;
    }

    /**
     * 获取一个类的泛型类型,如果未获取到返回Object.class
     *
     * @param clazz 要获取的类
     * @param index 泛型索引
     * @return 泛型
     */
    public static Class<?> getGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 获取一个类的第一个泛型的类型
     *
     * @param clazz 要获取的类
     * @return 泛型
     */
    public static Class<?> getGenericType(Class clazz) {
        return getGenericType(clazz, 0);
    }

    public static boolean instanceOf(Class clazz, Class target) {
        if (clazz == null) {
            return false;
        }
        if (clazz == target) {
            return true;
        }
        if (target.isInterface()) {
            for (Class aClass : clazz.getInterfaces()) {
                if (aClass == target) {
                    return true;
                }
            }
        }
        if (clazz.getSuperclass() == target) {
            return true;
        } else {
            if (clazz.isInterface()) {
                for (Class aClass : clazz.getInterfaces()) {
                    if (instanceOf(aClass, target)) {
                        return true;
                    }
                }
            }
            return instanceOf(clazz.getSuperclass(), target);
        }
    }

    /**
     * 将对象转为指定的类型
     * 支持日期，数字，boolean类型转换
     *
     * @param value 需要转换的值
     * @param type  目标类型
     * @param <T>   泛型
     * @return retv
     */
    public static final <T> T cast(Object value, Class<T> type) {
        if (value == null) {
            return null;
        }
        Object newVal = null;
        if (ClassUtil.instanceOf(value.getClass(), type)) {
            newVal = value;
        } else if (type == Integer.class || type == int.class) {
            newVal = StringUtil.toInt(value);
        } else if (type == Double.class || type == double.class || type == Float.class || type == float.class) {
            newVal = StringUtil.toDouble(value);
        } else if (type == Long.class || type == long.class) {
            newVal = StringUtil.toLong(value);
        } else if (type == Boolean.class || type == boolean.class) {
            newVal = StringUtil.isTrue(value);
        } else if (type == Date.class) {
            newVal = DateTimeUtil.formatUnknownString2Date(value.toString());
        } else if (type == String.class) {
            if (value instanceof Date) {
                newVal = DateTimeUtil.format(((Date) value), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
            } else {
                newVal = String.valueOf(value);
            }
        }
        return (T) newVal;
    }

    public static boolean isBasicClass(Class<?> clazz) {
        return BASIC_CLASS.contains(clazz);
    }


}

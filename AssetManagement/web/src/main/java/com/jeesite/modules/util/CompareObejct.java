package com.jeesite.modules.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 检测已变更的值
 * @param <T>
 */
public class CompareObejct<T> {

    private CompareStatus status;
    /**
     * 之前原始的值
     */
    private T original;
    /**
     * 当前的值
     */
    private T current;

    /**
     * 已变更的会赋值到此实体
     */
    private T changed;

    /**
     * 需要忽略的参数名称
     */
    private Set<String> ignoreNames;

    public Set<String> getIgnoreNames() {
        return ignoreNames;
    }

    public void setIgnoreNames(Set<String> ignoreNames) {
        this.ignoreNames = ignoreNames;
    }

    public T getChanged() {
        return changed;
    }

    public void setChanged(T changed) {
        this.changed = changed;
    }


    public CompareStatus getStatus() {
        return status;
    }


    public T getOriginal() {
        return original;
    }


    public void setOriginal(T original) {
        this.original = original;
    }


    public T getCurrent() {
        return current;
    }


    public void setCurrent(T current) {
        this.current = current;
    }


    /**
     * 比较方法
     * @param cls 类型
     */
    public void contrastObj(Class<T> cls) {
        if(this.original==null){
            this.status=CompareStatus.NEW;
            return;
        }
        if(this.status==CompareStatus.REMOVE){
            return;
        }
        boolean isChanged=true;
        try {
            //Class clazz = this.original.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if(ignoreNames.contains(field.getName())) continue;
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                    Method getMethod = pd.getReadMethod();
                    Object originalValue = getMethod.invoke(this.original);
                    Object currentValue = getMethod.invoke(this.current);
                    String originalStringValue = originalValue == null ? "" : originalValue.toString();
                    String currentStringValue = currentValue == null ? "" : currentValue.toString();
                    if (!originalStringValue.equals(currentStringValue))
                    {
                        if(this.changed != null){
                            Method setMethod = pd.getWriteMethod();
                            setMethod.invoke(this.changed, currentValue);
                        }
                        isChanged=false;
                        //System.out.println("不一样的属性：" + field.getName() + " 属性值：[" + originalStringValue + "," + currentStringValue + "]");
                    }
                }
                catch (IntrospectionException e){
                    //System.out.println("异常:"+e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.status= isChanged ? CompareStatus.NO_CHANGE : CompareStatus.CHANGE;
    }

}
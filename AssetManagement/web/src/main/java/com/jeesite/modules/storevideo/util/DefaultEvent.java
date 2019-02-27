package com.jeesite.modules.storevideo.util;

import org.springframework.context.ApplicationEvent;

public class DefaultEvent<T> {

    private static final long serialVersionUID = 1L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DefaultEvent(T source) {
        data = source;
    }
}

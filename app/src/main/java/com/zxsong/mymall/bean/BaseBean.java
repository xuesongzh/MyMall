package com.zxsong.mymall.bean;

import java.io.Serializable;

/**
 * Created by zxsong on 2016/2/18.
 */
public class BaseBean implements Serializable {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

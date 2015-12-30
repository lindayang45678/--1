package com.lakala.module.messagebase.model;

import com.lakala.module.comm.ObjectInput;

import java.io.Serializable;

/**
 * Created by HOT.LIU on 2015/3/10.
 */
public class MessageBaseInput extends ObjectInput implements Serializable {
    private static final long serialVersionUID = -7253302466547233558L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

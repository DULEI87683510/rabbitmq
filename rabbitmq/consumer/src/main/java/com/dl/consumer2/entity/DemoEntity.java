package com.dl.consumer2.entity;

import java.io.Serializable;

/**
 *@ClassName DemoEntity
 *@Description TODO
 *@Author DL
 *@Date 2019/9/10 15:54    
 *@Version 1.0
 */
public class DemoEntity implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.mycompany.app.Search;

import org.apache.commons.lang3.StringUtils;

public class Topic {

    String num;

    String title;

    String desc;

    String narrative;

    public Topic(String num, String title, String desc, String narrative) {
        if(StringUtils.isAnyBlank(num, title, desc, narrative)) {
            throw new IllegalArgumentException("One of the parameters in this topic is empty.");
        }
        this.num = num;
        this.title = title;
        this.desc = desc;
        this.narrative = narrative;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
}
package com.hongpro.demo.es.test;

import lombok.Builder;

/**
 * @Description:
 * @Author: zhangzihong
 * @CreateTime: 2021/9/17
 * @Version:
 */
@Builder
public class User {
    private String name;

    private String sex;

    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}

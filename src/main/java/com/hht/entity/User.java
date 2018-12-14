package com.hht.entity;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Date creaetdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreaetdate() {
        return creaetdate;
    }

    public void setCreaetdate(Date creaetdate) {
        this.creaetdate = creaetdate;
    }
}
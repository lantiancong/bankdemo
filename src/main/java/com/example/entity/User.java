package com.example.entity;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String idCard;
    private String phone;
    private String name;

    // 构造方法
    public User() {}

    public User(Integer id, String username, String idCard, String phone, String name) {
        this.id = id;
        this.username = username;
        this.idCard = idCard;
        this.phone = phone;
        this.name = name;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
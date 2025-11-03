package com.example.entity;

public class Student {
    private Integer id;
    private String studentNumber;
    private String name;
    private String className;
    private String gender;

    // 构造方法
    public Student() {}

    public Student(Integer id, String studentNumber, String name, String className, String gender) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.name = name;
        this.className = className;
        this.gender = gender;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
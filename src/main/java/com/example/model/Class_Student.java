package com.example.model;


import java.sql.Date;

public class Class_Student {
    private String id;
    private Student student;
    private Class aClass;
    private Date createAt;
    private Date lassmodified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLassmodified() {
        return lassmodified;
    }

    public void setLassmodified(Date lassmodified) {
        this.lassmodified = lassmodified;
    }

    public Class_Student(String id, Student student, Class aClass, Date createAt, Date lassmodified) {
        this.id = id;
        this.student = student;
        this.aClass = aClass;
        this.createAt = createAt;
        this.lassmodified = lassmodified;
    }

    public Class_Student() {
    }

    public Class_Student(Student student, Class aClass) {
        this.student = student;
        this.aClass = aClass;
    }
}
